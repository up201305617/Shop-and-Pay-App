package com.example.jorge.printerapp.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jorge on 01/11/2017.
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

    public static final String IP_REGEX = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
}
