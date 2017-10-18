package com.example.jorge.clientapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    public static String buildUserJSON(String name, String address, String NIF, String email, String password){
        String json = "{\"name\":\""+name+"\",\"" +
                "address\":\""+address+"\",\"" +
                "nif\":\""+NIF+"\",\"" +
                "email\":\""+email+"\",\"" +
                "password\":\""+password+"\"" + "}";
        return json;
    }
}
