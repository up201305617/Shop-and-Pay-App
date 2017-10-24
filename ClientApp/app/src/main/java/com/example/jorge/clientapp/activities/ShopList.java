package com.example.jorge.clientapp.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jorge.clientapp.R;

public class ShopList extends AppCompatActivity {

    LinearLayout list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        list = (LinearLayout) findViewById(R.id.llShop);
        for(int i=0; i<MainScreen.c.getShopList().size();i++){
            final int temp = i;
            final TextView tv = new TextView(this);
            final Button b = new Button(this);
            b.setText("X");
            b.setBackgroundColor(Color.RED);
            b.setTextColor(Color.WHITE);
            final LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.HORIZONTAL);
            tv.setText(MainScreen.c.getShopList().get(i).getName()+" "+MainScreen.c.getShopList().get(i).getPrice());
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(12);
            l.addView(tv);
            l.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainScreen.c.getShopList().remove(temp);
                    l.removeView(tv);
                    l.removeView(b);
                }
            });
            list.addView(l);
        }
    }
}
