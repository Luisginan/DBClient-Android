package com.semico.support.dbclient.contracts;


import com.semico.support.dbclient.contracts.Contract;

/**
 * Created by bintang on 4/29/2016.
 */
public class ProductDetailContract extends Contract {
    public ProductDetailContract() throws Exception {
        super();
    }

    @Override
    public String getTableName() {
        return "ProductDetail";
    }

    @Override
    public String[] getListColumn() {
        return new String[]{
                Column.ID,
                Column.PRODUCT_TYPE,
                Column.PRODUCT_WAREHOUSE_CODE,
                Column.PRODUCT_STOCK,
                Column.PRODUCT_EXPIRED_DATE
        };
    }

    @Override
    public String[] getListType() {
        return new String[]{
                Type.ID,
                Type.PRODUCT_TYPE,
                Type.PRODUCT_WAREHOUSE_CODE,
                Type.PRODUCT_STOCK,
                Type.PRODUCT_EXPIRED_DATE
        };
    }

    @Override
    public String[] getPrimaryKey() {
        return new String[]{
                Column.ID,
                Column.PRODUCT_TYPE,
                Column.PRODUCT_WAREHOUSE_CODE
        };
    }

    public class Column{
        public final static String ID = "id";
        public final static String PRODUCT_TYPE = "product_type";
        public final static String PRODUCT_WAREHOUSE_CODE = "product_warehouse_code";
        public final static String PRODUCT_STOCK = "product_stock";
        public final static String PRODUCT_EXPIRED_DATE = "product_expired_date";
    }

    public class Type{
        public final static String ID = "TEXT";
        public final static String PRODUCT_TYPE = "TEXT";
        public final static String PRODUCT_WAREHOUSE_CODE = "TEXT";
        public final static String PRODUCT_STOCK = "INTEGER";
        public final static String PRODUCT_EXPIRED_DATE = "DATE";
    }
}
