package com.example.jorge.printerapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.printerapp.R;
import com.example.jorge.printerapp.Utils.Routes;
import com.example.jorge.printerapp.Utils.Utils;

public class IP extends AppCompatActivity {
    Button connect;
    TextView ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        connect = (Button) findViewById(R.id.connectId);
        ip = (TextView) findViewById(R.id.ipId);

        connect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!ip.getText().toString().matches(Utils.IP_REGEX)){
                    Toast.makeText(getBaseContext(), "Type a valid IP address.",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Routes.IP_ADDRESS = ip.getText().toString();
                    Intent intent = new Intent(IP.this, MainScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
