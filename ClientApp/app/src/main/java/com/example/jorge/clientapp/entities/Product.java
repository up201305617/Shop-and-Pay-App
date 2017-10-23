package com.example.jorge.clientapp.entities;

import java.io.Serializable;

/**
 * Created by Jorge on 21/10/2017.
 */

public class Product implements Serializable {
    private String model;
    private String maker;
    private String price;
    private String category;
    private String barcode;
    private String name;

    public Product(String model, String maker, String price, String category, String id){
        this.model = model;
        this.category = category;
        this.barcode = id;
        this.price = price;
        this.maker = maker;
    }

    public Product(){

    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String id) {
        this.barcode = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
