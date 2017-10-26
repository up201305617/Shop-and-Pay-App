package com.example.jorge.clientapp.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jorge.clientapp.R;
import com.example.jorge.clientapp.utils.Utils;

public class ShopList extends AppCompatActivity {

    LinearLayout list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        list = (LinearLayout) findViewById(R.id.llShop);
        float price=0;
        for(int i=0; i<MainScreen.c.getShopList().size();i++){
            final int temp = i;
            final TextView tv = new TextView(this);
            final Button b = new Button(this);
            b.setText("X");
            b.setBackgroundColor(Color.RED);
            b.setTextColor(Color.WHITE);
            b.setScaleX(0.5f);
            b.setScaleY(0.5f);
            final LinearLayout l = new LinearLayout(this);
            l.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            l.setOrientation(LinearLayout.HORIZONTAL);
            tv.setText(MainScreen.c.getShopList().get(i).getName()+" "+MainScreen.c.getShopList().get(i).getPrice());
            price+=Float.parseFloat(MainScreen.c.getShopList().get(i).getPrice());
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(18);
            //tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            //tv.getLayoutParams().width = (int)(ViewGroup.LayoutParams.MATCH_PARENT * 0.7f);
            l.addView(tv);
            l.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainScreen.c.getShopList().remove(temp);
                    l.removeView(tv);
                    l.removeView(b);
                    finish();
                    startActivity(getIntent());
                }
            });
            list.addView(l);
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
                String json = Utils.buildShopListJSON(MainScreen.c.getEmail(),MainScreen.c.getShopList());
                Log.i("SHOPLISTjson",json);
            }
        });
        list.addView(buy);
    }
}
