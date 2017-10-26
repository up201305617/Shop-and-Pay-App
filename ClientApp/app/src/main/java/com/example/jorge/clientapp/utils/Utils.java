package com.example.jorge.clientapp.utils;

import android.util.Log;

import com.example.jorge.clientapp.entities.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jorge on 18/10/2017.
 */

public class Utils {
    public static String convertInputStreamToString(InputStream input) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(input));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result+=line;
        }
        input.close();
        bufferedReader.close();
        return result;
    }

    public static String buildUserJSON(String name, String address, String NIF, String email, String password, String cctype, String ccnumber, String ccvalidity){
        String json = "{\"name\":\""+name+"\",\"" +
                "address\":\""+address+"\",\"" +
                "nif\":\""+NIF+"\",\"" +
                "email\":\""+email+"\",\"" +
                "password\":\""+password+"\",\"" +
                "cctype\":\""+cctype+"\",\""+
                "ccnumber\":\""+ccnumber+"\",\""+
                "ccvalidity\":\""+ccvalidity+"\""+"}";
        return json;
    }

    public static String buildLogInJSON(String email, String password){
        String json = "{\"email\":\""+email+"\",\"password\":\""+password+"\"}";
        return  json;
    }

    public static String buildGetProductJSON(String barcode){
        String json = "{\"barcode\":\""+barcode+"\"}";
        return json;
    }

    public static boolean checkDate(String month, String year){
        int m = Integer.parseInt(month);
        int y = Integer.parseInt(year);

        if(y<Calendar.getInstance().get(Calendar.YEAR)){
            return false;
        }
        else if(m<Calendar.getInstance().get(Calendar.MONTH)){
            return false;
        }
        else{
            return true;
        }
    }

    public static String buildDate(String month, String year){
        return month+"/"+year;
    }

    public static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String buildShopListJSON(String email, ArrayList<Product> list)
    {
        if(list.size()==0){
            return null;
        }
        else {
            int size = list.size();
            String json = "{\"email\":\""+email+"\",\"" ;
            if(list.size()==1){
                json+="products\":"+"["+"{\"maker\":\""+list.get(0).getMaker()+"\",\""+
                        "model\":\""+list.get(0).getModel()+"\",\"" +
                        "price\":\""+list.get(0).getPrice()+"\""+"}"+"]"+"}";
            }
            else{
                json+="products\":"+"[";
                for(int i = 0; i<list.size();i++){
                    if(i == size-1){
                        json+="{\"maker\":\""+list.get(0).getMaker()+"\",\""+
                                "model\":\""+list.get(0).getModel()+"\",\"" +
                                "price\":\""+list.get(0).getPrice()+"\""+"}"+"]"+"}";
                    }
                    else {
                        json+="{\"maker\":\""+list.get(0).getMaker()+"\",\""+
                                "model\":\""+list.get(0).getModel()+"\",\"" +
                                "price\":\""+list.get(0).getPrice()+"\""+"}"+",";
                    }
                }
            }
            return json;
        }
    }
}
