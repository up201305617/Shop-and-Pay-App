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
import android.widget.Toast;

import com.example.jorge.clientapp.R;
import com.example.jorge.clientapp.entities.Client;
import com.example.jorge.clientapp.entities.Product;
import com.example.jorge.clientapp.utils.Routes;
import com.example.jorge.clientapp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.jorge.clientapp.utils.Utils.PAST;

public class MainScreen extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    TextView tvWelcome, items;
    Button bScan, bShopping, bPast;
    static Client c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Bundle bundle = getIntent().getExtras();
        c = (Client) bundle.get("Logged");

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome "+c.getName()+".");
        bScan = (Button) findViewById(R.id.scan);
        bShopping = (Button) findViewById(R.id.bShopping);
        items = (TextView) findViewById(R.id.tvItems);
        bPast = (Button) findViewById(R.id.pastBId);
        bScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                scan(false);
            }
        });

        bShopping.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreen.this, ShopList.class);
                startActivity(intent);
            }
        });

        bPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpAsyncTask get = new HttpAsyncTask(Utils.PAST);
                get.execute(Routes.buildGetPastTransactionsRoute(Routes.IP_ADDRESS)+c.getEmail());
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        if(c.getShopList().size() == 1){
            items.setText("You have "+c.getShopList().size()+" product on your shopping list.");
        }
        else if(c.getShopList().size() == 0){
            items.setText("Your shopping list is empty.");
        }
        else{
            items.setText("You have "+c.getShopList().size()+" products on your shopping list.");
        }
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
                HttpAsyncTask get = new HttpAsyncTask(Utils.SHOP_LIST);
                get.execute(Routes.buildGetProductByBarCodeRoute(Routes.IP_ADDRESS)+contents);
            }
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, String, String> {
        HttpURLConnection urlConnection;
        int state;
        public HttpAsyncTask(int s){
            state = s;
        }
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
            switch (state){
                case 0:
                    if(result==null) {
                        Toast.makeText(getBaseContext(), "Not Connected to Server.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        Product p = new Product();
                        boolean contains = result.matches(".*\\bfalse\\b.*");
                        if(!contains){
                            Log.i("ENTREI",1+"");
                            Log.i("SPLIT",result);
                            intent = new Intent(MainScreen.this, ProductDetails.class);
                            String r = result.replace("[","").replace("]","").replace("{","").replace("}","").replace(":"," ").replace(","," ").replace("\"","");
                            Log.i("SUBSTITUICAO",r);
                            JSONObject prod=null;
                            try {
                                prod = new JSONObject(new JSONObject(result).getString("product"));
                                p.setCategory(prod.getString("category"));
                                p.setName(prod.getString("name"));
                                p.setPrice(prod.getString("price"));
                                p.setModel(prod.getString("model"));
                                p.setMaker(prod.getString("maker"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            /*String[] split = r.split(" ");
                            p.setCategory(split[2]);
                            p.setModel(split[4]);
                            p.setPrice(split[6]);
                            p.setMaker(split[10]);
                            p.setName(split[12]);*/

                            intent.putExtra("Product",p);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getBaseContext(),"Product Not Found",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    break;
                case 1:
                    if(result==null) {
                        Toast.makeText(getBaseContext(), "Not Connected to Server.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        boolean contains_1 = result.matches(".*\\bfalse\\b.*");
                        if(!contains_1) {
                            intent = new Intent(MainScreen.this, PastTransactions.class);
                            intent.putExtra("Past",result);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getBaseContext(),"Shop Lists Nof Found.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    break;
            }

        }
    }
}
