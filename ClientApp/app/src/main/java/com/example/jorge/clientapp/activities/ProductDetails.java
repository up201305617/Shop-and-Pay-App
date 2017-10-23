package com.example.jorge.clientapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jorge.clientapp.R;
import com.example.jorge.clientapp.entities.Client;

public class ProductDetails extends AppCompatActivity {
    TextView tvBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Bundle bundle = getIntent().getExtras();
        String code = (String) bundle.get("barcode");

        tvBarcode = (TextView) findViewById(R.id.tvBarcode);
        tvBarcode.setText(code);
    }
}
