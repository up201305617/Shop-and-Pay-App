package com.example.jorge.clientapp.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jorge.clientapp.R;
import com.example.jorge.clientapp.entities.Client;
import com.example.jorge.clientapp.utils.Keys;
import com.example.jorge.clientapp.utils.Routes;
import com.example.jorge.clientapp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class SignUp extends AppCompatActivity {

    String name, address, email, password, nif, ccType, ccNumber, ccMonth, ccYear, ccValidity;
    EditText etName, etAddress, etEmail, etPassword, etNif, etCCNumber, etCCMonth, etCCYear;
    Button register;
    RadioGroup type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etName = (EditText) findViewById(R.id.name);
        etAddress = (EditText) findViewById(R.id.address);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etNif = (EditText) findViewById(R.id.nif);
        register = (Button) findViewById(R.id.register);
        type = (RadioGroup) findViewById(R.id.ccType);
        etCCNumber = (EditText) findViewById(R.id.ccNumber);
        etCCMonth = (EditText) findViewById(R.id.ccMonth);
        etCCYear = (EditText) findViewById(R.id.ccYear);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int checkedRadioButtonId = type.getCheckedRadioButtonId();

                if (etName.length() == 0 || etPassword.length() == 0 || etAddress.length() == 0 || etEmail.length() == 0 || etNif.length() == 0 || checkedRadioButtonId == -1) {
                    Toast.makeText(getBaseContext(), "All fields must be filled.", Toast.LENGTH_SHORT).show();
                    return;
                }

                name = etName.getText().toString();
                address = etAddress.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                nif = etNif.getText().toString();
                ccNumber = etCCNumber.getText().toString();
                ccMonth = etCCMonth.getText().toString();
                ccYear = etCCYear.getText().toString();

                if(!Utils.checkDate(ccMonth,ccYear)){
                    Toast.makeText(getBaseContext(), "Wrong date.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!etEmail.getText().toString().matches(Utils.EMAIL)){
                    Toast.makeText(getBaseContext(), "Type a valid e-mail address.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(nif.length()<9){
                    Toast.makeText(getBaseContext(), "NIF is a 9 digit number.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(ccNumber.length()<16){
                    Toast.makeText(getBaseContext(), "Credit Card number has 16 digits.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (checkedRadioButtonId == R.id.visa) {
                    ccType = "VISA";
                }
                else if(checkedRadioButtonId == R.id.mastercard){
                    ccType = "MasterCard";
                }

                ccValidity = Utils.buildDate(ccMonth,ccYear);

                //Keys k = new Keys();
                //k.generateKeys();
                //byte [] data = k.getPub().getEncoded();
                //Log.i("ENCODED",data+"");
                //byte [] encode = Base64.encode(k.getPub().toString().getBytes(), Base64.DEFAULT);
                //Log.i("ENCODED",encode+"");
                //byte[] pKbytes = Base64.encode(k.getPub().getEncoded(), 0);
                //String pK = new String(pKbytes);

                //String pubKey = "-----BEGIN PUBLIC KEY-----\n" + pK + "-----END PUBLIC KEY-----\n";
                //String base64 = Base64.encodeToString(data, Base64.DEFAULT);
                //Log.i("ENCODED",pKbytes+"");
                //Utils.savePrivateKey(k.getPri(),getFilesDir()+"");

                HttpAsyncTask post = new HttpAsyncTask();
                post.execute(Routes.buildSignUpRoute(Routes.IP_ADDRESS), name,address,nif,email,password,ccType,ccNumber, ccValidity);
            }
        });
    }

    private class HttpAsyncTask extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... params) {
            JSONObject response = null;
            if (params.length > 0) {
                try {
                    URL url = new URL(params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.connect();
                    String stringJson = Utils.buildUserJSON(params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8]);
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(stringJson);
                    writer.flush();
                    writer.close();
                    InputStream input = urlConnection.getInputStream();
                    response = new JSONObject(Utils.convertInputStreamToString(input).toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
                if(response!=null)
                    return response.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent;
            Client c = new Client();

            if(result==null) {
                Toast.makeText(getBaseContext(), "Not Connected to Server.", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                boolean contains = result.matches(".*\\btrue\\b.*");
                if(contains){
                    c.setEmail(email);
                    c.setName(name);
                    c.setAddress(address);
                    intent = new Intent(SignUp.this, MainScreen.class);
                    intent.putExtra("Logged",c);
                    startActivity(intent);
                    finish();
                }
                else {
                    return;
                }
            }
        }
    }
}