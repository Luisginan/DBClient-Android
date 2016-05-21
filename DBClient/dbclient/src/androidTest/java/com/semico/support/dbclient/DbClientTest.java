package com.semico.support.dbclient;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.semico.support.dbclient.interfaces.IContractManager;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class DbClientTest extends ApplicationTestCase<Application> {

    IContractManager contractManager = new ContractManager();
    public DbClientTest() {
        super(Application.class);
    }

    public void test_Find() throws Exception {

        DbClient dbClient = new DbClient(getContext(),contractManager);
        dbClient.beginTransaction();
        dbClient.deleteAll(User.class);
        dbClient.endTransaction();

        User user = new User();
        user.id = "001";
        user.fullName = "Luis";
        user.RowKey = "00183433";
        user.role = 9L;
        user.active = true;
        user.created = new Date();

        dbClient.beginTransaction();
        dbClient.insert(user);
        dbClient.endTransaction();


        user = dbClient.find(User.class, "001");
        Assert.assertEquals(user.fullName, "Luis");
    }

    public void testDelete() throws Exception {
        DbClient dbClient = new DbClient(getContext(),contractManager);
        dbClient.beginTransaction();
        dbClient.deleteAll(User.class);
        dbClient.endTransaction();

        dbClient.beginTransaction();
        User user = new User();
        user.id = "001";
        user.fullName = "Luis";
        user.RowKey = "00183433";
        user.role = 9L;
        user.active = true;
        user.created = new Date();

        dbClient.insert(user);
        dbClient.endTransaction();

        dbClient.beginTransaction();
        dbClient.delete(user);
        dbClient.endTransaction();

        List<User> userList = dbClient.query(User.class, "Select * from " + new User().getContract().getTableName());
        assertEquals(0,userList.size());
    }


    public void test_Query() throws Exception {
        DbClient dbClient = new DbClient(getContext(),contractManager);
        dbClient.beginTransaction();
        dbClient.deleteAll(User.class);
        dbClient.endTransaction();

        User user = new User();
        user.id = "001";
        user.fullName = "Luis";
        user.RowKey = "00183433";
        user.role = 9L;
        user.active = true;
        user.created = new Date();

        dbClient.beginTransaction();
        dbClient.insert(user);
        dbClient.endTransaction();

        List<User> listUser = dbClient.query(User.class, "Select * from user");
        Assert.assertTrue(listUser.size() == 1);
    }

    public void test_Query_emptyTable() throws Exception {
        DbClient dbClient = new DbClient(getContext(),contractManager);
        dbClient.beginTransaction();
        dbClient.deleteAll(User.class);
        dbClient.endTransaction();

        List<User> listUser = dbClient.query(User.class, "Select * from user");
        Assert.assertEquals(0, listUser.size());
    }

    public void test_Find_DataNotFound() throws Exception {
        DbClient dbClient = new DbClient(getContext(),contractManager);
        User user = dbClient.find(User.class, "XXX");
        Assert.assertEquals(user, null);
    }

    public void test_Insert() throws Exception {
        DbClient dbClient = new DbClient(getContext(),contractManager);
        dbClient.beginTransaction();
        dbClient.deleteAll(User.class);
        dbClient.endTransaction();

        dbClient.beginTransaction();
        User user = new User();
        user.id = "001";
        user.fullName = "Luis";
        user.RowKey = "00183433";
        user.role = 9L;
        user.active = true;
        user.created = new Date();

        dbClient.insert(user);
        dbClient.endTransaction();

        List<User> userList = dbClient.query(User.class, "Select * from user");
        assertEquals(userList.size(), 1);
    }

    public void test_Insert_memberNull() throws Exception {
        try {
            DbClient dbClient = new DbClient(getContext(),contractManager);
            dbClient.beginTransaction();
            User user = new User();
            user.id = "001";
            user.fullName = "Luis";
            user.RowKey = null;
            user.created = new Date();

            dbClient.insert(user);
            dbClient.endTransaction();

            assertFalse(true);
        } catch (Exception e) {
            Assert.assertEquals("java.lang.NullPointerException: RowKey", e.toString());
        }
    }

    public void test_InsertAll_NullList() throws Exception {
        try {
            DbClient dbClient = new DbClient(getContext(),contractManager);
            dbClient.beginTransaction();
            dbClient.insertAll(User.class, null);
            dbClient.endTransaction();
        } catch (Exception e) {
            assertEquals("java.lang.NullPointerException: listData", e.toString());
        }
    }

    public void test_Insert_nested_transaction() throws Exception {
        try {
            DbClient dbClient = new DbClient(getContext(),contractManager);
            dbClient.beginTransaction();
            User user = new User();
            user.id = "001";
            user.fullName = "Luis";
            user.RowKey = null;
            user.created = new Date();

            dbClient.insert(user);

            new NestedClass().Save();
            dbClient.endTransaction();

            assertFalse(true);
        } catch (Exception e) {
            Assert.assertEquals("java.lang.NullPointerException: RowKey", e.toString());
        }
    }

    public void test_InsertAll_NullMember() throws Exception {
        try {
            DbClient dbClient = new DbClient(getContext(),contractManager);
            dbClient.beginTransaction();
            dbClient.deleteAll(User.class);
            dbClient.endTransaction();

            List<User> listUser = new ArrayList<>();
            User user = new User();
            user.id = "001";
            user.fullName = "Luis";
            user.RowKey = "00183433";
            user.role = 9L;
            user.active = true;
            user.created = new Date();

            listUser.add(user);

            user = new User();
            user.id = "002";
            user.fullName = null;
            user.RowKey = "00183433";
            user.role = 9L;
            user.active = true;
            user.created = new Date();

            listUser.add(user);

            dbClient.beginTransaction();
            dbClient.insertAll(User.class, listUser);
            dbClient.endTransaction();
        } catch (Exception e) {
            assertEquals("java.lang.NullPointerException: fullName", e.toString());
        }
    }

    public void test_InsertAll() throws Exception {
        DbClient dbClient = new DbClient(getContext(),contractManager);
        dbClient.beginTransaction();
        dbClient.deleteAll(User.class);
        dbClient.endTransaction();

        List<User> listUser = new ArrayList<>();
        User user = new User();
        user.id = "001";
        user.fullName = "Luis";
        user.RowKey = "00183433";
        user.role = 9L;
        user.active = true;
        user.created = new Date();

        listUser.add(user);

        user = new User();
        user.id = "002";
        user.fullName = "Bintang";
        user.RowKey = "00183433";
        user.role = 9L;
        user.active = true;
        user.created = new Date();

        listUser.add(user);

        user = new User();
        user.id = "003";
        user.fullName = "Jati";
        user.RowKey = "00183433";
        user.role = 9L;
        user.active = true;
        user.created = new Date();

        listUser.add(user);

        dbClient.beginTransaction();
        dbClient.insertAll(User.class, listUser);
        dbClient.endTransaction();
    }

    public void test_Insert_null() throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        try {
            DbClient dbClient = new DbClient(getContext(),contractManager);
            dbClient.beginTransaction();
            dbClient.insert(null);
            dbClient.endTransaction();
            assertFalse(true);
        } catch (Exception e) {
            assertEquals(e.toString(), "java.lang.NullPointerException: data");
        }
    }

    public void test_Save() throws Exception {
        DbClient dbClient = new DbClient(getContext(),contractManager);
        dbClient.beginTransaction();

        dbClient.deleteAll(User.class);
        User user = new User();
        user.id = "0010";
        user.fullName = "Luis";
        user.RowKey = "00183433";
        user.role = 9L;
        user.active = true;
        user.created = new Date();

        dbClient.insert(user);
        dbClient.endTransaction();

        dbClient.beginTransaction();
        user.fullName = "Luis Ginanjar";
        dbClient.save(user);
        dbClient.endTransaction();

        List<User> userList = dbClient.query(User.class, "select * from " + user.getContract().getTableName());
        assertEquals(1, userList.size());
        assertEquals("Luis Ginanjar", userList.get(0).fullName);

    }

    public void test_Delete() throws Exception {
        DbClient dbClient = new DbClient(getContext(),contractManager);
        dbClient.beginTransaction();
        User user = new User();
        user.id = "001";
        user.fullName = "Luis";
        user.created = new Date();
        dbClient.delete(user);
        dbClient.endTransaction();
    }

    public void testGetDateTime() throws Exception {
        DbClient dbClient = new DbClient(getContext(),contractManager);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DATE, 2);
        cal.set(Calendar.MONTH, 2);
        cal.set(Calendar.YEAR, 2016);

        Date date = cal.getTime();
        String timeActual = dbClient.getDateTime(date);
        String timeExpected = "2016-03-02 17:30:00";

        assertEquals(timeExpected, timeActual);
    }

    public void testGetDateFromString() throws Exception {

        DbClient dbClient = new DbClient(getContext(),contractManager);
        String time = "2016-03-02 17:30:00";
        Date actualDate = dbClient.getDateFromString(time);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DATE, 2);
        cal.set(Calendar.MONTH, 2);
        cal.set(Calendar.YEAR, 2016);
        Date dateExpected = cal.getTime();

        assertEquals(dateExpected, actualDate);

    }

    //region CRUD ProductDetail
    /**
     * Creator : Bintang
     * Purpose : Testing of class model which has more than 1 or even 2 primary keys.
     */
    public void test_InsertProductDetail() throws Exception {
        DbClient dbClient = new DbClient(getContext(),contractManager);
        dbClient.beginTransaction();
        dbClient.deleteAll(ProductDetail.class);
        dbClient.endTransaction();

        dbClient.beginTransaction();
        ProductDetail productDetail = new ProductDetail();
        productDetail.id = "001";
        productDetail.product_type = "snack";
        productDetail.product_warehouse_code = "011";
        productDetail.product_stock = 1000;
        productDetail.product_expired_date = new Date();

        dbClient.insert(productDetail);
        dbClient.endTransaction();

        List<ProductDetail> productDetailList = dbClient.query(ProductDetail.class, "Select * from ProductDetail");
        assertEquals(productDetailList.size(), 1);
    }

    public void test_FindProductDetail() throws Exception {

        DbClient dbClient = new DbClient(getContext(),contractManager);
        dbClient.beginTransaction();
        dbClient.deleteAll(ProductDetail.class);
        dbClient.endTransaction();

        ProductDetail productDetail = new ProductDetail();
        productDetail.id = "001";
        productDetail.product_type = "fragile";
        productDetail.product_warehouse_code = "011";
        productDetail.product_stock = 1000;
        productDetail.product_expired_date = new Date();

        dbClient.beginTransaction();
        dbClient.insert(productDetail);
        dbClient.endTransaction();


        List<ProductDetail> productDetailList = dbClient.query(ProductDetail.class,
                "Select * from ProductDetail where product_type= 'fragile'");
        assertEquals(productDetailList.size(), 1);
    }


    public void test_UpdateProductDetail() throws Exception{
        DbClient dbClient = new DbClient(getContext(),contractManager);
        ProductDetail productDetail = new ProductDetail();

        //Insert data first.
        dbClient.beginTransaction();
        productDetail.id = "001";
        productDetail.product_type = "fragile";
        productDetail.product_warehouse_code = "011";
        productDetail.product_stock = 1000;
        productDetail.product_expired_date = new Date();
        dbClient.insert(productDetail);
        dbClient.endTransaction();

        //then update the data
        dbClient.beginTransaction();
        productDetail = new ProductDetail();
        productDetail.id = "001";
        productDetail.product_type = "fragile";
        productDetail.product_warehouse_code = "011";
        productDetail.product_stock = 1230;
        productDetail.product_expired_date = new Date();
        dbClient.update(productDetail);
        dbClient.endTransaction();

        //check whether updated or not
        List<ProductDetail> productDetailList = dbClient.query(ProductDetail.class,
                "Select * from ProductDetail where product_stock= '1230'");
        assertEquals(productDetailList.size(), 1);
    }


    public void testDeleteProductDetail() throws Exception {
        DbClient dbClient = new DbClient(getContext(),contractManager);

        dbClient.beginTransaction();
        dbClient.deleteAll(ProductDetail.class);
        dbClient.endTransaction();

        dbClient.beginTransaction();
        ProductDetail productDetail = new ProductDetail();
        productDetail.id = "007";
        productDetail.product_type = "medicine";
        productDetail.product_warehouse_code = "007";
        productDetail.product_stock = 111;
        productDetail.product_expired_date = new Date();

        dbClient.insert(productDetail);
        dbClient.endTransaction();

        dbClient.beginTransaction();
        dbClient.delete(productDetail);
        dbClient.endTransaction();

        List<ProductDetail> productDetailList = dbClient.query(ProductDetail.class, "Select * from " + new ProductDetail().getContract().getTableName());
        assertEquals(0,productDetailList.size());
    }

    //endregion

    class NestedClass {
        public void Save() throws Exception {
            DbClient dbClient = new DbClient(getContext(),contractManager);
            dbClient.beginTransaction();
            User user = new User();
            user.id = "0010";
            user.fullName = "Luis";
            user.RowKey = "00183433";
            user.role = 9L;
            user.active = true;
            dbClient.insert(user);
            dbClient.endTransaction();
        }
    }
}