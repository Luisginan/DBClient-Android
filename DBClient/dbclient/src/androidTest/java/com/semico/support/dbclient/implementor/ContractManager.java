package com.semico.support.dbclient.implementor;


import com.semico.support.dbclient.contracts.UserContract;
import com.semico.support.dbclient.contracts.Contract;
import com.semico.support.dbclient.contracts.ProductContract;
import com.semico.support.dbclient.contracts.ProductDetailContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 3/18/2016.
 * Purpose : Register contract for generated DB, add your contract here..
 */
public class ContractManager implements IContractManager {
    public  List<Contract>  getListContract() throws Exception {
        List<Contract> contractList = new ArrayList<>();
        contractList.add(new ProductDetailContract());
        contractList.add(new UserContract());
        contractList.add(new ProductContract());
        return contractList;
    }
}
