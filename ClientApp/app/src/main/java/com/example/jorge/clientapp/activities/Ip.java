package com.example.jorge.clientapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.clientapp.R;
import com.example.jorge.clientapp.utils.Routes;
import com.example.jorge.clientapp.utils.Utils;

public class Ip extends AppCompatActivity {

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
                    Intent intent = new Intent(Ip.this, LogIn.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
