package com.example.jorge.clientapp.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jorge.clientapp.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PastTransactions extends AppCompatActivity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_transactions);

        Bundle bundle = getIntent().getExtras();
        String list = (String) bundle.get("Past");

        tableLayout = (TableLayout) findViewById(R.id.pastTable);

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            for(int i=0; i<jsonObj.getJSONArray("lists").length(); i++){
                TableRow tableRow = new TableRow(this);
                TextView dateV = new TextView(this);
                TextView timeV = new TextView(this);
                dateV.setTextColor(Color.BLACK);
                dateV.setTextSize(18);
                timeV.setTextColor(Color.BLACK);
                timeV.setTextSize(18);
                JSONObject aux = new JSONObject(jsonObj.getJSONArray("lists").get(i).toString());
                dateV.setText(aux.getString("date"));
                timeV.setText(aux.getString("time"));
                tableRow.addView(dateV);
                tableRow.addView(timeV);
                tableLayout.addView(tableRow);
                for(int j = 0; j<aux.getJSONArray("products").length();j++){
                    TableRow tableRow_1 = new TableRow(this);
                    JSONObject aux_1 = new JSONObject(aux.getJSONArray("products").get(j).toString());
                    TextView auxMaker = new TextView(this);
                    TextView auxModel = new TextView(this);
                    TextView auxPrice = new TextView(this);
                    auxMaker.setTextColor(Color.BLUE);
                    auxMaker.setTextSize(16);
                    auxModel.setTextColor(Color.BLUE);
                    auxModel.setTextSize(16);
                    auxPrice.setTextColor(Color.BLUE);
                    auxPrice.setTextSize(16);
                    auxMaker.setText(aux_1.getString("maker"));
                    auxModel.setText(aux_1.getString("model"));
                    auxPrice.setText(aux_1.getString("price")+"€");
                    tableRow_1.addView(auxMaker);
                    tableRow_1.addView(auxModel);
                    tableRow_1.addView(auxPrice);
                    tableLayout.addView(tableRow_1);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tableLayout = (TableLayout)findViewById(R.id.pastTable);
    }
}
