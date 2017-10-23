package com.example.jorge.clientapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jorge.clientapp.R;
import com.example.jorge.clientapp.entities.Client;
import com.example.jorge.clientapp.entities.Product;
import com.example.jorge.clientapp.utils.Routes;
import com.example.jorge.clientapp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainScreen extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    TextView tvWelcome;
    Button bScan;
    static Client c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Bundle bundle = getIntent().getExtras();
        c = (Client) bundle.get("Logged");

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome back "+c.getName()+".");
        bScan = (Button) findViewById(R.id.scan);

        bScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                scan(false);
            }
        });
    }

    public void scan(boolean qrcode) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", qrcode ? "QR_CODE_MODE" : "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        }
        catch (ActivityNotFoundException anfe) {
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                act.startActivity(intent);
            }
        });
        downloadDialog.setNegativeButton(buttonNo, null);
        return downloadDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                if(contents!=null&&contents.length()>0){
                    contents = contents.substring(0, contents.length() - 1);
                }
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                HttpAsyncTask get = new HttpAsyncTask();
                get.execute(Routes.GetProductByBarCode+contents);
            }
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;
        @Override
        protected String doInBackground(String... params) {
            JSONObject response = null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();
                InputStream input = urlConnection.getInputStream();
                response = new JSONObject(Utils.convertInputStreamToString(input).toString());
                //String r = response.toString().replace("[","").replace("]","").replace("{","").replace("}","").replace(":"," ").replace(","," ");
                Log.i("RESPOSTA",response.toString());
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
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent;
            Product p = new Product();
            Log.i("ENTREI",1+"");
            Log.i("SPLIT",result);
            intent = new Intent(MainScreen.this, ProductDetails.class);
            String r = result.replace("[","").replace("]","").replace("{","").replace("}","").replace(":"," ").replace(","," ").replace("\"","");
            Log.i("SUBSTITUICAO",r);
            String[] split = r.split(" ");
            p.setCategory(split[2]);
            p.setModel(split[4]);
            p.setPrice(split[6]);
            p.setMaker(split[11]);
            p.setName(split[13]);
            intent.putExtra("Product",p);
            startActivity(intent);
        }
    }
}
