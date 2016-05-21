package com.semico.support.dbclient.datas;

import com.semico.support.dbclient.contracts.Contract;
import com.semico.support.dbclient.contracts.ProductDetailContract;
import com.semico.support.dbclient.datas.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bintang on 4/29/2016.
 */
public class ProductDetail extends Data implements Serializable{

    public String product_type;
    public String product_warehouse_code;
    public int product_stock;
    public Date product_expired_date;

    @Override
    public Contract getContract() throws Exception {
        return new ProductDetailContract();
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "product_type='" + product_type + '\'' +
                ", product_warehouse_code='" + product_warehouse_code + '\'' +
                ", product_stock=" + product_stock +
                ", product_expired_date=" + product_expired_date +
                '}';
    }
}
