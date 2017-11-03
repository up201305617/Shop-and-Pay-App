package com.example.jorge.printerapp.Activities;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.jorge.printerapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayList extends AppCompatActivity {
    RelativeLayout rl;
    TableLayout tableLayout;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        Bundle bundle = getIntent().getExtras();
        String list = (String) bundle.get("List");

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tableLayout = (TableLayout)findViewById(R.id.tableId);
        TableRow titleRow = new TableRow(this);
        titleRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        TextView makerTv = new TextView(this);
        TextView modelTv = new TextView(this);
        TextView priceTv = new TextView(this);
        makerTv.setText("Maker");
        modelTv.setText("Model");
        priceTv.setText("Price");
        makerTv.setTextColor(Color.BLACK);
        makerTv.setTextSize(24);
        modelTv.setTextColor(Color.BLACK);
        modelTv.setTextSize(24);
        priceTv.setTextColor(Color.BLACK);
        priceTv.setTextSize(24);
        titleRow.addView(makerTv);
        titleRow.addView(modelTv);
        titleRow.addView(priceTv);
        tableLayout.addView(titleRow);

        try {
            for (int i = 0; i < jsonObj.getJSONArray("list").length(); i++)
            {
                TableRow tableRow = new TableRow(this);
                TextView auxMaker = new TextView(this);
                TextView auxModel = new TextView(this);
                TextView auxPrice = new TextView(this);
                auxMaker.setTextColor(Color.BLUE);
                auxMaker.setTextSize(20);
                auxModel.setTextColor(Color.BLUE);
                auxModel.setTextSize(20);
                auxPrice.setTextColor(Color.BLUE);
                auxPrice.setTextSize(20);
                JSONObject aux=null;
                try {
                    aux = new JSONObject(jsonObj.getJSONArray("list").get(i).toString());
                    auxMaker.setText(aux.getString("maker"));
                    auxModel.setText(aux.getString("model"));
                    auxPrice.setText(aux.getString("price")+"€");
                    tableRow.addView(auxMaker);
                    tableRow.addView(auxModel);
                    tableRow.addView(auxPrice);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tableLayout.addView(tableRow);
                if(jsonObj.getJSONArray("list").length() == i+1){
                    TableRow totalRow = new TableRow(this);
                    TextView totalTv = new TextView(this);
                    totalTv.setTextColor(Color.BLACK);
                    totalTv.setTextSize(24);
                    totalTv.setText("Total = "+jsonObj.getString("price")+"€");
                    totalRow.addView(totalTv);
                    tableLayout.addView(totalRow);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
