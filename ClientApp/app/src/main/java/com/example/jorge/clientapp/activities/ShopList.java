package com.example.jorge.clientapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.clientapp.R;
import com.example.jorge.clientapp.entities.Client;
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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

public class ShopList extends AppCompatActivity {

    LinearLayout list;
    TableLayout tableLayout;

    float price=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        list = (LinearLayout) findViewById(R.id.llShop);
        tableLayout = (TableLayout) findViewById(R.id.slTable);

        for(int i=0; i<MainScreen.c.getShopList().size();i++){
            final int temp = i;
            final TextView tv = new TextView(this);
            final TextView makerTv = new TextView(this);
            final TextView modelTv = new TextView(this);
            final TextView priceTv = new TextView(this);
            final Button b = new Button(this);
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            b.setText("X");
            b.setBackgroundColor(Color.RED);
            b.setTextColor(Color.WHITE);
            //b.setScaleX(0.5f);
            //b.setScaleY(0.5f);
            //final LinearLayout l = new LinearLayout(this);
            //l.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //l.setOrientation(LinearLayout.HORIZONTAL);
            makerTv.setText(MainScreen.c.getShopList().get(i).getMaker());
            makerTv.setTextColor(Color.BLACK);
            makerTv.setTextSize(18);
            modelTv.setText(MainScreen.c.getShopList().get(i).getModel());
            modelTv.setTextColor(Color.BLACK);
            modelTv.setTextSize(18);
            priceTv.setText(MainScreen.c.getShopList().get(i).getPrice()+"€");
            priceTv.setTextColor(Color.BLACK);
            priceTv.setTextSize(18);
            //tv.setText(MainScreen.c.getShopList().get(i).getName()+" "+MainScreen.c.getShopList().get(i).getPrice()+"€"+" ");
            price+=Float.parseFloat(MainScreen.c.getShopList().get(i).getPrice());
            //tv.setTextColor(Color.BLACK);
            //tv.setTextSize(18);
            //tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            //tv.getLayoutParams().width = (int)(ViewGroup.LayoutParams.MATCH_PARENT * 0.7f);
            //l.addView(tv);
            //l.addView(b);
            tableRow.addView(makerTv);
            tableRow.addView(modelTv);
            tableRow.addView(priceTv);
            tableRow.addView(b);
            tableLayout.addView(tableRow);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainScreen.c.getShopList().remove(temp);
                    //tableRow.removeAllViews();
                    //tableRow.removeView(makerTv);
                    //tableRow.removeView(modelTv);
                    //tableRow.removeView(priceTv);
                    //tableRow.removeView(b);
                    tableLayout.removeView(tableRow);
                    //l.removeView(tv);
                    //l.removeView(b);
                    finish();
                    startActivity(getIntent());
                }
            });

        }
        TextView totalPrice = new TextView(this);
        totalPrice.setText("Total = "+price+"€");
        totalPrice.setTextColor(Color.BLACK);
        totalPrice.setTextSize(18);
        list.addView(totalPrice);
        Button buy = new Button(this);
        buy.setText("BUY");
        buy.setTextColor(Color.BLACK);
        buy.setBackgroundColor(Color.GREEN);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpAsyncTask post = new HttpAsyncTask();
                post.execute(Routes.PostShopList);
            }
        });
        list.addView(buy);
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
                    String prodArray = Utils.buildProductsJSON(MainScreen.c.getShopList());
                    //PrivateKey priKey = Utils.loadPrivateKey(getFilesDir()+"","RSA");
                    //byte [] sig = Utils.signShopList(priKey,prodArray.getBytes("UTF-8"));
                    String stringJson = Utils.buildShopListJSON(MainScreen.c.getEmail(),MainScreen.c.getShopList(),price);
                    Log.i("PEDIDO",stringJson);
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(stringJson);
                    writer.flush();
                    writer.close();
                    InputStream input = urlConnection.getInputStream();
                    response = new JSONObject(Utils.convertInputStreamToString(input).toString());
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
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals(null)) {
                return;
            }
            else {
                MainScreen.c.getShopList().clear();
                Intent intent;
                boolean contains = result.matches(".*\\bfalse\\b.*");
                if(contains){
                    return;
                }
                else {
                    intent = new Intent(ShopList.this, QRCodeScreen.class);
                    intent.putExtra("UUID",result.replace("{","").replace("}","").replace(":","").replace("UUID","").replace("\"",""));
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}
