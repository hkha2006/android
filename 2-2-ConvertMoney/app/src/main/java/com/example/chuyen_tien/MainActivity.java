package com.example.chuyen_tien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Tien> arrayListTien;
    static ArrayList<String> arrayList;
    static String url = "";
    Spinner dropdown1, dropdown2;
    ListView listView;
    ArrayAdapter<String> adapter;
    TextView textView;
    EditText InputMoney;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InputMoney = findViewById(R.id.money);
        arrayList = new ArrayList<>();
        arrayListTien = new ArrayList<>();
        dropdown1 = (Spinner) findViewById(R.id.spinner1);
        dropdown2 = (Spinner) findViewById(R.id.spinner2);
        textView = findViewById(R.id.textViewtigia);


        new ReadHTML().execute("https://www.fxexchangerate.com/currency-converter-rss-feed.html");
        // Link trang web cần lấy dữ liệu


        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vt1 = dropdown1.getSelectedItemPosition();
                int vt2 = dropdown2.getSelectedItemPosition();

                creatDrop(vt2, vt1);


            }
        });
        findViewById(R.id.button_convert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = arrayListTien.get(dropdown1.getSelectedItemPosition()).tientetat.toLowerCase();
                String s2 = arrayListTien.get(dropdown2.getSelectedItemPosition()).tientetat.toLowerCase();

                url = "https://" + s1 + ".fxexchangerate.com/" + s2 + ".xml";
                new ReadRSS().execute(url);


            }
        });


    }

    // Tạo danh sách tiền
    public void creatDrop(int vt1, int vt2) {


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        dropdown1.setAdapter(adapter);
        dropdown2.setAdapter(adapter);

        dropdown1.setSelection(vt1);
        dropdown2.setSelection(vt2);


    }

    // Hàm chuyển đổi bình thường
    public float chuyendoi(int tien, float tigia) {

        return tien * tigia;

    }

    // hàm đọc rss để lấy dữ liệu rss
    private class ReadRSS extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Đang chuyển đổi...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();

            XMLDOMParser parser = new XMLDOMParser();
            Document document = null;
            try {
                document = parser.getDocument(s);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            NodeList nodeList = document.getElementsByTagName("description");
            String noidung = "";

            Element element = (Element) nodeList.item(1);

            noidung += parser.getCharacterDataFromElement(element);

            String[] output = noidung.split("<br/>");
            String s1 = output[0];

            output = s1.trim().split(" ");
            String giatrichuyendoi = output[3];


            textView.setText(s1.trim());
            String chuoiA = InputMoney.getText().toString();

            if (chuoiA.isEmpty() || chuoiA.length() == 0 || chuoiA.equals("") || chuoiA == null) {
                Toast.makeText(MainActivity.this, "Bạn chưa nhập số", Toast.LENGTH_SHORT).show();


            } else {
                int i1 = Integer.parseInt(String.valueOf(InputMoney.getText()));
                float i2 = Float.valueOf(giatrichuyendoi);
                float tienchuyendoi = chuyendoi(i1, i2);
                String dau = arrayListTien.get(dropdown1.getSelectedItemPosition()).tientetat;
                String duoi = arrayListTien.get(dropdown2.getSelectedItemPosition()).tientetat;
                textView.append("\n" + String.valueOf(InputMoney.getText()) + " " + dau + " = " + String.valueOf(tienchuyendoi) + " " + duoi);

                Toast.makeText(MainActivity.this, String.valueOf(tienchuyendoi), Toast.LENGTH_SHORT).show();

            }


        }


    }

    // hàm đọc html
    private class ReadHTML extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Đang lấy dữ liệu...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();

            org.jsoup.nodes.Document document = Jsoup.parse(s);
            org.jsoup.nodes.Element fxSidebarFrom = document.getElementById("fxSidebarFrom");
            // Lấy tag có id=fxSidebarFrom
            Elements inputElements = fxSidebarFrom.getElementsByTag("option");
            for (org.jsoup.nodes.Element inputElement : inputElements) {
                String tientetat = inputElement.attr("value");
                // Lấy tất cả value của select option
                String tien = inputElement.text();
                Tien tien_real = new Tien(tien, tientetat);
                arrayListTien.add(tien_real);
                arrayList.add(tien);

            }
            creatDrop(0, 1);


        }
    }
}