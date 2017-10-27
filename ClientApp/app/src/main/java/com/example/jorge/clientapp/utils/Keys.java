package com.example.jorge.clientapp.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by Jorge on 27/10/2017.
 */

public class Keys {
    private PrivateKey pri;
    private PublicKey pub;

    public Keys(){

    }

    public void generateKeys(){
        KeyPairGenerator kgen = null;
        try {
            kgen = KeyPairGenerator.getInstance("RSA");
            kgen.initialize(368);
            KeyPair kp = kgen.generateKeyPair();
            pri = kp.getPrivate();
            pub = kp.getPublic();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public PrivateKey getPri() {
        return pri;
    }

    public PublicKey getPub() {
        return pub;
    }
}
