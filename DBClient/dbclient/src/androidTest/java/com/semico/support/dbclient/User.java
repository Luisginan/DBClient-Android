package com.semico.support.dbclient;

import com.semico.support.dbclient.contracts.Contract;
import com.semico.support.dbclient.datas.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by luis on 2/12/2016.
 * Purpose :
 */
public class User extends Data implements Serializable {
    public String fullName;
    public String RowKey;
    public boolean active;
    public long role;
    public Date created;

    @Override
    public Contract getContract() throws Exception {
        return new UserContract();
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", RowKey='" + RowKey + '\'' +
                ", active=" + active +
                ", role=" + role +
                '}';
    }
}
