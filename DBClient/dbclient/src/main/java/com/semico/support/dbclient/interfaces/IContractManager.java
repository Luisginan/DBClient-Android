package com.semico.support.dbclient.interfaces;

import com.semico.support.dbclient.contracts.Contract;

import java.util.List;

/**
 * Created by Luis Ginanjar on 21/05/2016.
 * Purpose :
 */
public interface IContractManager {
    List<Contract> getListContract() throws Exception;
}
