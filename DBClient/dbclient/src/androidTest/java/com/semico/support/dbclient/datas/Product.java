package com.semico.support.dbclient.datas;


import android.graphics.Bitmap;

import com.semico.support.dbclient.contracts.Contract;
import com.semico.support.dbclient.contracts.ProductContract;
import com.semico.support.dbclient.datas.Data;

import java.io.Serializable;

/**
 * Created by bintang on 4/13/2016.
 */
public class Product extends Data implements Serializable {
    private Bitmap product_image;
    private String product_name;
    private double price_before_discount;
    public double price_discount;
    public int qty;
    public double total;


    public Product (Bitmap product_image, String product_name, double price_before_discount, double price_discount, int qty, double total ){
        this.product_image = product_image;
        this.product_name = product_name;
        this.price_before_discount = price_before_discount;
        this.price_discount = price_discount;
        this.qty = qty;
        this.total = total;
    }

    public Product(){

    }


    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Bitmap getProduct_image() {
        return product_image;
    }

    public void setProduct_image(Bitmap product_image) {
        this.product_image = product_image;
    }

    public double getPrice_before_discount() {
        return price_before_discount;
    }

    public void setPrice_before_discount(double price_before_discount) {
        this.price_before_discount = price_before_discount;
    }

    public double getPrice_discount() {
        return price_discount;
    }

    public void setPrice_discount(double price_discount) {
        this.price_discount = price_discount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public Contract getContract() throws Exception {
        return new ProductContract();
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_image='" + product_image + '\'' +
                ", product_name='" + product_name + '\'' +
                ", price_before_discount=" + price_before_discount +
                ", price_discount=" + price_discount +
                ", qty=" + qty +
                ", total=" + total +
                '}';
    }
}
