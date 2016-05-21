package com.semico.support.dbclient;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.semico.support.dbclient.contracts.Contract;
import com.semico.support.dbclient.implementor.IContractManager;

import java.util.List;

/**
 * Created by luis on 2/23/2016.
 * Purpose : direct access to Sqlite db, don't add specific query here..
 */
class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Ngemart";
    private static final int DATABASE_VERSION = 1;

    private List<Contract> listContract;

    public  DbHelper(Context context,IContractManager contractManager) throws Exception {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        listContract = contractManager.getListContract();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Contract contract: listContract) {
            db.execSQL(getQueryCreateTable(contract));
        }
    }

    //todo: change Object.equals() : http://stackoverflow.com/questions/21548989/using-objects-equals-in-android
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }


    private String getQueryCreateTable(Contract contract)
    {
        String[] listColumn = contract.getListColumn();
        String[] listType = contract.getListType();
        String[] listPrimaryKey = contract.getPrimaryKey();

        String textCreateColumn = "";
        String textPrimaryKey   = "";

        for(int i=0; i < listColumn.length; i++){
            textCreateColumn += "\"" + listColumn[i] +"\"" + " " + listType[i] + " NOT NULL";
            if(i != listColumn.length -1){
            textCreateColumn += ", ";
            }
        }
        for(int pK=0; pK < listPrimaryKey.length; pK++){
            textPrimaryKey += "\""+listPrimaryKey[pK]+"\"";
            if(pK != listPrimaryKey.length -1){
                textPrimaryKey += ", ";
            }
        }

        return " CREATE TABLE " + "\"" + contract.getTableName() + "\"" +
                " (" + textCreateColumn + ", PRIMARY KEY(" + textPrimaryKey +"));";
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Contract contract : listContract) {
            db.execSQL("DROP TABLE IF EXISTS " +  contract.getTableName());
        }
        onCreate(db);
    }

}
