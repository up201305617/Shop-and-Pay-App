package com.example.jorge.clientapp.utils;

/**
 * Created by Jorge on 19/10/2017.
 */

public class Routes {
    public static String SignUpRouteEmulator = "http://10.0.2.2:3000/api/user";
    public static String LogInRouteEmulator = "http://10.0.2.2:3000/api/login";
    public static String LogInRoute = "http://192.168.1.66:3000/api/login";
    public static String GetProductByBarCode = "http://192.168.1.66:3000/api/product/";
    public static String SignUpRoute = "http://192.168.1.66:3000/api/user";
    public static String SignUpRouteFEUP = "http://172.30.7.223:3000/api/user";
    public static String LogInRouteFEUP = "http://172.30.7.223:3000/api/login";
    public static String GetProductByBarCodeFEUP = "http://172.30.7.223:3000/api/product/";
    public static String PostShopListFEUP = "http://172.30.7.223:3000/api/shoplist/";
    public static String PostShopList = "http://192.168.1.66:3000/api/shoplist";
    public static String GetPastTransactions = "http://192.168.1.66:3000/api/shoplists/";
    public static String GetPastTransactionsFEUP = "http://172.30.7.223:3000/api/shoplists/";
    public static String IP_ADDRESS;
    public static String IPLogInRoute = "http://"+IP_ADDRESS+":3000/api/login";

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
