package com.example.jorge.clientapp.entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jorge on 23/10/2017.
 */

public class Client implements Serializable {
    private String name;
    private String email;
    private String password;
    private ArrayList<Product> shopList;
    private String address;
    private String ccType;
    private String ccNumber;
    private String ccDate;

    public Client(){
        this.shopList = new ArrayList<Product>();
    }

    public Client(String n, String p, String e){
        this.email = e;
        this.name = n;
        this.password = p;
        this.shopList = new ArrayList<Product>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Product> getShopList() {
        return shopList;
    }

    public void setShopList(ArrayList<Product> shopList) {
        this.shopList = shopList;
    }

    public void addToList(Product p){
        this.shopList.add(p);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcDate() {
        return ccDate;
    }

    public void setCcDate(String ccDate) {
        this.ccDate = ccDate;
    }
}
