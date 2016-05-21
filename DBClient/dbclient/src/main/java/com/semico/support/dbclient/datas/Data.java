
package com.semico.support.dbclient.datas;

import com.semico.support.dbclient.contracts.Contract;

/**
 * Created by luis on 2/12/2016.
 * Purpose :
 */
public abstract class Data {

    public String id;
    public abstract Contract getContract() throws Exception;

}
