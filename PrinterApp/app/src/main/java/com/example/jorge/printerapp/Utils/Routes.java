package com.example.jorge.printerapp.Utils;

/**
 * Created by Jorge on 31/10/2017.
 */

public class Routes {
    public static String IP_ADDRESS;

    public static String buildGetShopListByUUID(String ip){
        String address =  "http://"+ip+":3000/api/shoplist/";
        return address.replace(" ","");
    }
}
