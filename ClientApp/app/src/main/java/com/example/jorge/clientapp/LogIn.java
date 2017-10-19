package com.example.jorge.clientapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LogIn extends AppCompatActivity {
    String email, password;
    Button login, signup;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        login = (Button) findViewById(R.id.log_in);
        signup = (Button) findViewById(R.id.sign_up);
        etEmail = (EditText) findViewById(R.id.lEmail);
        etPassword = (EditText) findViewById(R.id.lPassword);

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if(email.length() == 0 || password.length() == 0){
                    Toast.makeText(getBaseContext(), "Todos os campos devem ser preenchidos.", Toast.LENGTH_SHORT).show();
                }
                else{
                    HttpAsyncTask post = new HttpAsyncTask();
                    post.execute(Routes.LogInRouteEmulator, email,password);
                    //post.execute("http://10.0.2.2:3000/api/login", email,password);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
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
                    String stringJson = Utils.buildLogInJSON(params[1],params[2]);
                    Log.i("String",stringJson);
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(stringJson);
                    writer.flush();
                    writer.close();
                    InputStream input = urlConnection.getInputStream();
                    response = new JSONObject(Utils.convertInputStreamToString(input).toString());
                    Log.i("Response",response.toString());
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

        }
    }
}
