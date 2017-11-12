package com.example.jorge.clientapp.utils;

/**
 * Created by Jorge on 19/10/2017.
 */

public class Routes {

    public static String IP_ADDRESS;

    public static String buildLogInRoute(String ip){
        String address =  "http://"+ip+":3000/api/login";
        return address.replace(" ","");
    }

    public static String buildSignUpRoute(String ip){
        String address =  "http://"+ip+":3000/api/user";
        return address.replace(" ","");
    }

    public static String buildGetProductByBarCodeRoute(String ip){
        String address =  "http://"+ip+":3000/api/product/";
        return address.replace(" ","");
    }

    public static String buildPostShopListRoute(String ip){
        String address =  "http://"+ip+":3000/api/shoplist";
        return address.replace(" ","");
    }

    public static String buildGetPastTransactionsRoute(String ip){
        String address =  "http://"+ip+":3000/api/shoplists/";
        return address.replace(" ","");
    }
}
