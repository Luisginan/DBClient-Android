package com.semico.support.dbclient;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.semico.support.dbclient.contracts.Contract;
import com.semico.support.dbclient.datas.Data;
import com.semico.support.dbclient.implementor.IContractManager;
import com.semico.support.dbclient.implementor.IDbClient;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by luis on 2/12/2016.
 * Purpose : access database with auto mapping to class data, don't add business flow specific here ..
 */
public class DbClient implements IDbClient {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SQLiteDatabase dbWritable;
    private static int beginCounter = 0;
    private static int endCounter = 0;
    private final Context context;
    private final IContractManager contractManager;
    private DbHelper dbHelper;
    public DbClient(Context context,IContractManager contractManager) throws Exception {
        this.context = context;
        this.contractManager = contractManager;

        if(dbWritable == null)
            dbWritable = getDbWritable();

    }

    public static <T extends Data> void ValidateData(T data) throws IllegalAccessException {
        if (data == null) throw new NullPointerException("data");
        Field[] listField = data.getClass().getFields();
        for (Field field : listField) {
            if (field.getName().equals("$change"))
                continue;
            if (field.get(data) == null)
                throw new NullPointerException(field.getName());
        }
    }

    @Override
    public synchronized void beginTransaction() throws Exception {

        if(beginCounter < endCounter)
           throw new Exception("Transaction not match begin with end");
        if(dbWritable == null)
        {
                dbWritable = getDbWritable();
            endCounter = 0;
            beginCounter = 0;
            dbWritable.beginTransactionNonExclusive();
        }
        else
        {
            if(!dbWritable.isOpen())
            {
                dbWritable = getDbWritable();
                endCounter = 0;
                beginCounter = 0;
                dbWritable.beginTransactionNonExclusive();
            }
        }


        beginCounter ++;
    }

    @Override
    public synchronized void endTransaction() throws Exception {

        if(dbWritable != null)
        {
            if(dbWritable.inTransaction()) {
                endCounter ++;
                if (endCounter == beginCounter) {
                    dbWritable.setTransactionSuccessful();
                    dbWritable.endTransaction();
                    dbWritable.close();
                    endCounter = 0;
                    beginCounter = 0;
                }
            }
        }
    }

    @Override
    public <T extends Data> T find(Class<T> theClass, String valueKey) throws Exception {
        return find(theClass,new String[]{valueKey});
    }


    @Override
    public <T extends Data> T find(Class<T> theClass, String[] keyValue) throws Exception {

        SQLiteDatabase dbReadable = getDbReadable();

        T data = theClass.newInstance();
        String[] listColumn = data.getContract().getListColumn();
        String[] listColumnPrimaryKey = data.getContract().getPrimaryKey();
        String selection = generateKeySelection(listColumnPrimaryKey);

        Cursor cursor = dbReadable.query(data.getContract().getTableName(),
                listColumn, selection, keyValue, "", "", "");
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToNext();
            MappingCursorToData(data, listColumn, cursor);
        cursor.close();

        return data;
    }

    @Override
    public void update(Data data) throws Exception {
        if (data == null) throw new NullPointerException("data");

        String[] listColumn = data.getContract().getListColumn();
        String[] listColumnPrimaryKey = data.getContract().getPrimaryKey();
        String selection = generateKeySelection(listColumnPrimaryKey);
        String[] listValueOfPrimaryKey = generateKeySelectionValue(data, listColumnPrimaryKey);

        ContentValues contentValues = mappingDataToContentValues(data, listColumn);
        dbWritable.update(data.getContract().getTableName(), contentValues, selection, listValueOfPrimaryKey);
    }

    @Override
    public void delete(Data model) throws Exception {
        Contract contract  = model.getContract();

        String[] listColumnPrimaryKey = contract.getPrimaryKey();
        String[] listValueOfPrimaryKey = generateKeySelectionValue(model, listColumnPrimaryKey);
        String selection = generateKeySelection(listColumnPrimaryKey);

        dbWritable.delete(contract.getTableName(), selection, listValueOfPrimaryKey);
    }

    @Override
    public <T extends Data> void deleteAll(Class<T> theClass) throws Exception {
        T data = theClass.newInstance();
        dbWritable.execSQL("Delete from " + data.getContract().getTableName());
    }

    @Override
    public <T extends Data> void insertAll(Class<T> theClass, List<T> listData) throws Exception {

        if (listData == null)
            throw new NullPointerException("listData");
        if (listData.size() == 0)
            return;

            for (Data data : listData) {
                ValidateData(data);
                String[] listColumn = data.getContract().getListColumn();
                ContentValues contentValues = mappingDataToContentValues(data, listColumn);
                long result = dbWritable.insert(data.getContract().getTableName(), "", contentValues);
                if (result < 1)
                    throw new Exception("Failed affected database");
            }
    }

    @Override
    public <T extends Data> void insert(T data) throws Exception {
        ValidateData(data);

        String[] listColumn = data.getContract().getListColumn();

        ContentValues contentValues = mappingDataToContentValues(data, listColumn);
        long result = dbWritable.insert(data.getContract().getTableName(), "", contentValues);
        if (result < 1)
            throw new Exception("Insert failed affected");
    }

    @Override
    public <T extends Data> void save(T data) throws Exception {
        ValidateData(data);
        String[] listColumnPrimaryKey = data.getContract().getPrimaryKey();
        String[] listValueOfPrimaryKey = generateKeySelectionValue(data, listColumnPrimaryKey);
        if (find(data.getClass(), listValueOfPrimaryKey) != null)
            update(data);
        else
            insert(data);
    }

    @Override
    public Cursor query (String query) throws Exception {
        SQLiteDatabase dbReadable = getDbReadable();
        return dbReadable.rawQuery(query, null);
    }

    @Override
    public void execute(String query) throws Exception {
      dbWritable.execSQL(query);
    }


    @Override
    public <T extends Data> List<T> query(Class<T> theClass, String query) throws Exception {
        SQLiteDatabase dbReadable = getDbReadable();
        List<T> listData = new ArrayList<>();
        T data = theClass.newInstance();
        String[] listColumn = data.getContract().getListColumn();

         Cursor cursor = dbReadable.rawQuery(query, null);

            if (cursor.getCount() == 0)
                return new ArrayList<>();
            while (cursor.moveToNext()) {
                data = theClass.newInstance();
                MappingCursorToData(data, listColumn, cursor);
                listData.add(data);
            }
        cursor.close();

        return listData;
    }

    private String generateKeySelection(String[] listColumnPrimaryKey) {
        String selection = "";
        for (int i = 0; i < listColumnPrimaryKey.length; i++) {
            selection += listColumnPrimaryKey[i] + " = ? ";
            if (i != (listColumnPrimaryKey.length - 1)) {
                selection += " AND ";
            }
        }
        return selection;
    }

    private <T extends Data> ContentValues mappingDataToContentValues(T data, String[] listColumn) throws NoSuchFieldException, IllegalAccessException {
        ContentValues contentValues = new ContentValues();
        for (String column : listColumn) {
            Field field = data.getClass().getField(column);
            if (field.getType() == String.class)
                contentValues.put(column, (String) field.get(data));
            else if (field.getType() == Integer.class)
                contentValues.put(column, (Integer) field.get(data));
            else if (field.getType() == int.class)
                contentValues.put(column, (Integer) field.get(data));
            else if (field.getType() == Long.class)
                contentValues.put(column, (Long) field.get(data));
            else if (field.getType() == long.class)
                contentValues.put(column, (Long) field.get(data));
            else if (field.getType() == boolean.class)
                contentValues.put(column, field.getBoolean(data) ? 1 : 0);
            else if (field.getType() == Boolean.class)
                contentValues.put(column, field.getBoolean(data) ? 1 : 0);
            else if (field.getType() == Date.class)
                contentValues.put(column, getDateTime((Date) field.get(data)));
        }
        return contentValues;
    }

    public String getDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                DATE_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }
    private SQLiteDatabase getDbReadable() throws Exception {
        dbHelper = new DbHelper(context,contractManager);
        return dbHelper.getReadableDatabase();
    }

    private <T extends Data> void MappingCursorToData(T data, String[] listColumn, Cursor cursor) throws Exception {
            int rowCount = data.getClass().getFields().length;
            for (int i = 0; i < rowCount; i++) {
                Field field = data.getClass().getField(listColumn[i]);
                if (field.getType() == String.class)
                    field.set(data, cursor.getString(cursor.getColumnIndex(field.getName())));
                else if (field.getType() == Integer.class)
                    field.set(data, cursor.getInt(cursor.getColumnIndex(field.getName())));
                else if (field.getType() == int.class)
                    field.set(data, cursor.getInt(cursor.getColumnIndex(field.getName())));
                else if (field.getType() == Long.class)
                    field.set(data, cursor.getLong(cursor.getColumnIndex(field.getName())));
                else if (field.getType() == long.class)
                    field.set(data, cursor.getLong(cursor.getColumnIndex(field.getName())));
                else if (field.getType() == Boolean.class)
                    field.set(data, cursor.getInt(cursor.getColumnIndex(field.getName())) == 1);
                else if (field.getType() == boolean.class)
                    field.set(data, cursor.getInt(cursor.getColumnIndex(field.getName())) == 1);
                else if (field.getType() == Date.class) {
                    String value = cursor.getString(cursor.getColumnIndex(field.getName()));
                    field.set(data, getDateFromString(value));
                }
            }
    }

    public Date getDateFromString(String stringDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return dateFormat.parse(stringDate);
    }

    private  SQLiteDatabase getDbWritable() throws Exception {
        dbHelper = new DbHelper(context,contractManager);
        return dbHelper.getWritableDatabase();
    }


    private String[] generateKeySelectionValue(Data model, String[] keyColumn) throws NoSuchFieldException, IllegalAccessException {
        String[] keyValue = new String[keyColumn.length];
        for (int i = 0; i < keyValue.length; i++) {
            Field field = model.getClass().getField(keyColumn[i]);
            keyValue[i] = (String) field.get(model);
        }
        return keyValue;
    }
}
