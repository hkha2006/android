package com.example.nationinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class NationDetail extends AppCompatActivity {
    TextView name;
    TextView population;
    TextView area;
    TextView continent;
    TextView capital;
    TextView currency;
    ImageView flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nation_detail);
        Intent intent = this.getIntent();
        JSONObject d = new JSONObject();
        try {
             d = new JSONObject(getIntent().getStringExtra("detail"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String codeText = d.optString("code");
        String imageURL = "https://img.geonames.org/flags/x/"+codeText.toLowerCase()+".gif";
        String nameText = d.optString("name");
        String populationText = d.optString ("population");
        String areaText = d.optString("area");
        String capitalText = d.optString ("capital");
        String continentText = d.optString("continent");
        String currencyText = d.optString ("currency");

        name = (TextView) findViewById(R.id.name);
        population = (TextView) findViewById(R.id.populationValue);
        area = (TextView) findViewById(R.id.areaValue);
        capital = (TextView) findViewById(R.id.capitalValue);
        continent = (TextView) findViewById(R.id.continentValue);
        currency = (TextView) findViewById(R.id.currencyValue);
        flag = (ImageView) findViewById(R.id.flag);

        new ImageLoadTask(imageURL, flag).execute();
        name.setText(nameText);
        population.setText(populationText);
        area.setText(areaText);
        capital.setText(capitalText);
        continent.setText(continentText);
        currency.setText(currencyText);

    }
}