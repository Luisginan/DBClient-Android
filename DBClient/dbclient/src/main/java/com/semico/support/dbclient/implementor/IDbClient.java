package com.semico.support.dbclient.implementor;

import android.database.Cursor;

import com.semico.support.dbclient.datas.Data;

import java.util.List;

/**
 * Created by luis on 2/11/2016.
 * Purpose : Interfacing for DBClient
 */
public interface IDbClient {

    void beginTransaction() throws Exception;

    void endTransaction() throws Exception;

    <T extends Data> T find(Class<T> theClass, String rowKey) throws Exception;

    <T extends Data> T find(Class<T> theClass, String[] Key) throws Exception;

    void update(Data data) throws Exception;

    void delete(Data model) throws Exception;

    <T extends Data> void deleteAll(Class<T> theClass) throws Exception;

    <T extends Data> void insertAll(Class<T> theClass, List<T> listData) throws Exception;

    <T extends Data> void insert(T data) throws Exception;

    <T extends Data> void save(T data) throws Exception;

    Cursor query(String query) throws Exception;

    void execute(String query) throws Exception;

    <T extends Data> List<T> query(Class<T> theClass, String query) throws Exception;
}
