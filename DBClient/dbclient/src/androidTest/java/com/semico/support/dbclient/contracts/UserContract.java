package com.semico.support.dbclient.contracts;

import com.semico.support.dbclient.contracts.Contract;

/**
 * Created by luis on 2/23/2016.
 * Purpose : Contract for Class data User
 */
public class UserContract extends Contract {

    public UserContract() throws Exception {
        super();
    }

    @Override
    public String getTableName() {
        return "User";
    }

    @Override
    public String[] getListColumn() {
        return new String[]{
                Column.ID,
                Column.FULL_NAME,
                Column.ROWKEY,
                Column.ACTIVE,
                Column.ROLE,
                Column.CREATED
        };
    }

    @Override
    public String[] getListType() {
        return new String[]{
                Type.ID,
                Type.FULL_NAME,
                Type.ROWKEY,
                Type.ACTIVE,
                Type.ROLE,
                Type.CREATED
        };
    }

    @Override
    public String[] getPrimaryKey() {
        return new String[]{Column.ID};
    }

    public class Column
    {
        public final static String ID = "id";
        public final static String FULL_NAME = "fullName";
        public final static String ROWKEY = "RowKey";
        public final static String ACTIVE = "active";
        public final static String ROLE = "role";
        public final static String CREATED = "created";
    }

    public class Type
    {
        public final static String ID = "TEXT";
        public final static String FULL_NAME = "TEXT";
        public final static String ROWKEY = "TEXT";
        public final static String ACTIVE = "INTEGER";
        public final static String ROLE = "REAL";
        public final static String CREATED = "TEXT";
    }
}
