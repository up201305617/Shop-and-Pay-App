package com.example.jorge.clientapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jorge.clientapp.R;
import com.example.jorge.clientapp.entities.Client;
import com.example.jorge.clientapp.entities.Product;

public class ProductDetails extends AppCompatActivity {
    TextView tvName, tvMaker, tvModel, tvPrice, tvCategory;
    Button addShopList;
    Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Bundle bundle = getIntent().getExtras();
        p = (Product) bundle.get("Product") ;

        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(p.getName());
        tvMaker = (TextView) findViewById(R.id.tvMaker);
        tvMaker.setText(p.getMaker());
        tvModel = (TextView) findViewById(R.id.tvModel);
        tvModel.setText(p.getModel());
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvPrice.setText(p.getPrice());
        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvCategory.setText(p.getCategory());
        addShopList = (Button) findViewById(R.id.bShopList);

        addShopList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MainScreen.c.addToList(p);
                Log.i("TAMANHO",MainScreen.c.getShopList().size()+"");
            }
        });
    }
}
