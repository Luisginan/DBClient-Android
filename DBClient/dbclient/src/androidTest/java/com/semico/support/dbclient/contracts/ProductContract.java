package com.semico.support.dbclient.contracts;


import com.semico.support.dbclient.contracts.Contract;

/** * Created by bintang on 4/13/2016.
 */
public class ProductContract extends Contract {

    public ProductContract() throws Exception {
        super();
    }

    @Override
    public String getTableName() {
        return "Product";
    }

    @Override
    public String[] getListColumn() {
        return new String[]{
                Column.ID,
                Column.PRODUCT_IMAGE,
                Column.PRODUCT_NAME,
                Column.PRICE_BEFORE_DISCOUNT,
                Column.PRICE_DISCOUNT,
                Column.QTY,
                Column.TOTAL
        };
    }

    @Override
    public String[] getListType() {
        return new String[]{
                Type.ID,
                Type.PRODUCT_IMAGE,
                Type.PRODUCT_NAME,
                Type.PRICE_BEFORE_DISCOUNT,
                Type.PRICE_DISCOUNT,
                Type.QTY,
                Type.TOTAL
        };
    }

    @Override
    public String[] getPrimaryKey() {
        return new String[]{Column.ID};
    }

    public class Column
    {
        public final static String ID = "id";
        public final static String PRODUCT_IMAGE = "product_image";
        public final static String PRODUCT_NAME = "product_name";
        public final static String PRICE_BEFORE_DISCOUNT = "price_before_discount";
        public final static String PRICE_DISCOUNT = "price_discount";
        public final static String QTY = "qty";
        public final static String TOTAL = "total";
    }

    public class Type
    {
        public final static String ID = "TEXT";
        public final static String PRODUCT_IMAGE = "TEXT";
        public final static String PRODUCT_NAME = "TEXT";
        public final static String PRICE_BEFORE_DISCOUNT = "DOUBLE";
        public final static String PRICE_DISCOUNT = "DOUBLE";
        public final static String QTY = "INTEGER";
        public final static String TOTAL = "DOUBLE";
    }
}
