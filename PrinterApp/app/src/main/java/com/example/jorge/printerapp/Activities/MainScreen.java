package com.example.jorge.printerapp.Activities;

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
import android.widget.Toast;

import com.example.jorge.printerapp.R;
import com.example.jorge.printerapp.Utils.Routes;
import com.example.jorge.printerapp.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainScreen extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    Button scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        scan = (Button) findViewById(R.id.scanQrcode);

        scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                scan(true);
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
                    contents = contents.substring(0, contents.length());
                }
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                Log.i("Formal",contents);
                HttpAsyncTask get = new HttpAsyncTask();
                get.execute(Routes.buildGetShopListByUUID(Routes.IP_ADDRESS)+contents);
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
            if(result == null){
                Toast.makeText(getBaseContext(), "Not Connected to Server.", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                Intent intent;
                boolean contains = result.matches(".*\\bfalse\\b.*");
                if(contains){
                    Toast.makeText(getBaseContext(), "Not Found.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    intent = new Intent(MainScreen.this, DisplayList.class);
                    intent.putExtra("List",result);
                    startActivity(intent);
                }
            }
        }
    }
}
