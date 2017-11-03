package com.example.jorge.clientapp.utils;

import android.util.Log;

import com.example.jorge.clientapp.entities.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.path;

/**
 * Created by Jorge on 18/10/2017.
 */

public class Utils {

    public static int SHOP_LIST = 0;
    public static int PAST = 1;

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

    public static final String buildShopListJSON(String email, ArrayList<Product> list,float price)
    {
        if(list.size()==0){
            return null;
        }
        else {
            int size = list.size();
            String json = "{\"email\":\""+email+"\",\"" ;
            json += "totalPrice\":\""+price+"\",\"";
            if(list.size()==1){
                json+="products\":"+"["+"{\"maker\":\""+list.get(0).getMaker()+"\",\""+
                        "model\":\""+list.get(0).getModel()+"\",\"" +
                        "price\":\""+list.get(0).getPrice()+"\""+"}"+"]"+"}";
            }
            else{
                json+="products\":"+"[";
                for(int i = 0; i<list.size();i++){
                    if(i == size-1){
                        json+="{\"maker\":\""+list.get(i).getMaker()+"\",\""+
                                "model\":\""+list.get(i).getModel()+"\",\"" +
                                "price\":\""+list.get(i).getPrice()+"\""+"}"+"]"+"}";
                    }
                    else {
                        json+="{\"maker\":\""+list.get(i).getMaker()+"\",\""+
                                "model\":\""+list.get(i).getModel()+"\",\"" +
                                "price\":\""+list.get(i).getPrice()+"\""+"}"+",";
                    }
                }
            }
            return json;
        }
    }

    public static void savePrivateKey(PrivateKey key, String path){
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key.getEncoded());
        FileOutputStream  fos = null;
        try {
            fos = new FileOutputStream(path + "/private.key");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(pkcs8EncodedKeySpec.getEncoded());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PrivateKey loadPrivateKey(String path, String algorithm) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File filePrivateKey = new File(path + "/private.key");
        FileInputStream fis = new FileInputStream(path + "/private.key");
        byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
        fis.read(encodedPrivateKey);
        fis.close();
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                encodedPrivateKey);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        return  privateKey;
    }

    public static String buildProductsJSON(ArrayList<Product> list){
        if(list.size()==0){
            return null;
        }
        else {
            int size = list.size();
            String json = "";
            if(list.size()==1){
                json+="["+"{\"maker\":\""+list.get(0).getMaker()+"\",\""+
                        "model\":\""+list.get(0).getModel()+"\",\"" +
                        "price\":\""+list.get(0).getPrice()+"\""+"}"+"]";
            }
            else{
                json+="[";
                for(int i = 0; i<list.size();i++){
                    if(i == size-1){
                        json+="{\"maker\":\""+list.get(0).getMaker()+"\",\""+
                                "model\":\""+list.get(0).getModel()+"\",\"" +
                                "price\":\""+list.get(0).getPrice()+"\""+"}"+"]";
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

    public static byte[] signShopList(PrivateKey pri, byte[] input) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        byte[] output;
        Signature sg = Signature.getInstance("SHA1WithRSA");
        sg.initSign(pri);
        sg.update(input);
        output = sg.sign();
        return output;
    }
}
