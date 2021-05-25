package com.example.nationinfo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;


public class NationInfo extends AppCompatActivity {

    private String TAG = NationInfo.class.getSimpleName();

    private ListView lv;

    private static String url = "http://api.geonames.org/countryInfoJSON?formatted=true&lang=it&username=hien2511&style=full";

    ArrayList<HashMap<String, String>> nationList;
    ArrayList<HashMap<String, String>> nationListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nationListDetail = new ArrayList<>();
        nationList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray nations = jsonObj.getJSONArray("geonames");

                    for (int i = 0; i < nations.length(); i++) {
                        JSONObject c = nations.getJSONObject(i);

                        String code = c.getString("countryCode");
                        if (code.length() == 0)
                            code = "Unknown";
                        String name = c.getString("countryName").replace(" ", "-");
                        if (name.length() == 0)
                            name = "Unknown";
                        String population = c.getString("population");
                        if (population.length() == 0)
                            population = "Unknown";
                        String area = c.getString("areaInSqKm");
                        if (area.length() == 0)
                            area = "Unknown";
                        String continent = c.getString("continentName").replace(" ", "-");
                        if (continent.length() == 0)
                            continent = "Unknown";
                        String currency = c.getString ("currencyCode");
                        if (currency.length() == 0)
                            currency = "Unknown";
                        String capital = c.getString("capital").replace(" ", "-");
                        if (capital.length() == 0)
                            capital = "Unknown";

                        HashMap<String, String> nationDetail = new HashMap<>();
                        HashMap<String, String> nation = new HashMap<>();

                        nationDetail.put("name", name);
                        nationDetail.put("code", code);
                        nationDetail.put("population", population);
                        nationDetail.put("continent", continent);
                        nationDetail.put("currency", currency);
                        nationDetail.put("capital", capital);
                        nationDetail.put("area", area);

                        nationListDetail.add(nationDetail);

                        nation.put("name", name.replace("-", " "));
                        nation.put("population", population);

                        nationList.add(nation);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(
                    NationInfo.this, nationList,
                    R.layout.list_item, new String[]{"name","population"}, new int[]{R.id.name,R.id.population});

            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent intent = new Intent(NationInfo.this, NationDetail.class);
                    intent.putExtra("detail",nationListDetail.get(position).toString());
                    startActivity(intent);
                }
            });
        }

    }
}

