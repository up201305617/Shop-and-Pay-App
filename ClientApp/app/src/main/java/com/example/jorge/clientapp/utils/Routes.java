package com.example.jorge.clientapp.utils;

import java.security.PublicKey;

/**
 * Created by Jorge on 19/10/2017.
 */

public class Routes {
    public static String SignUpRouteEmulator = "http://10.0.2.2:3000/api/user";
    public static String LogInRouteEmulator = "http://10.0.2.2:3000/api/login";
    public static String LogInRoute = "http://192.168.1.67:3000/api/login";
    public static String GetProductByBarCode = "http://192.168.1.67:3000/api/product/";
    public static String SignUpRoute = "http://192.168.1.67:3000/api/user";
    public static String SignUpRouteFEUP = "http://172.30.7.223:3000/api/user";
    public static String LogInRouteFEUP = "http://172.30.7.223:3000/api/login";
    public static String GetProductByBarCodeFEUP = "http://172.30.7.223:3000/api/product/";
    public static String PostShopListFEUP = "http://172.30.7.223:3000/api/shoplist/";
    public static String PostShopList = "http://192.168.1.67:3000/api/shoplist/";
}
