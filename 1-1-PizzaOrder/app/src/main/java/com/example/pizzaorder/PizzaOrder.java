package com.example.pizzaorder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class PizzaOrder extends AppCompatActivity {

    public String getOrder() {

        RadioGroup crust = (RadioGroup) findViewById(R.id.crust);
        RadioGroup size = (RadioGroup) findViewById(R.id.size);
        String yourOrderText = getString(R.string.yourOrder);
        String toppingText = getString(R.string.topping);
        String sizeText = getString(R.string.size);
        String crustText = getString(R.string.crust);
        String drinkText = getString(R.string.drink);
        String noneText = getString(R.string.none);
        String toppingWarningText = getString(R.string.toppingWarning);
        String msg = "";
        ArrayList<String> toppingList = new ArrayList<String>();
        ArrayList<String> drinkList = new ArrayList<String>();
        int selectedCrustID = crust.getCheckedRadioButtonId();
        RadioButton selectedCrust = (RadioButton) findViewById(selectedCrustID);
        int selectedSizeID = size.getCheckedRadioButtonId();
        RadioButton selectedSize = (RadioButton) findViewById(selectedSizeID);
        LinearLayout toppingLayout = (LinearLayout) findViewById(R.id.toppingLayout);

        int count = toppingLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            LinearLayout view = (LinearLayout) toppingLayout.getChildAt(i);
            int count2 = view.getChildCount();
            for (int j = 0; j < count2; j++) {
                View view2 = view.getChildAt(j);
                if (view2 instanceof CheckBox) {
                    if (((CheckBox) view2).isChecked()) {
                        toppingList.add(((CheckBox) view2).getText().toString());
                    }
                }
            }
        }

        String toppingString = "";
        if (toppingList.isEmpty()) {
            Toast.makeText(PizzaOrder.this, toppingWarningText , Toast.LENGTH_LONG).show(); // show message
        } else {
            toppingString = String.join(", ", toppingList); // show topping

            LinearLayout drinkLayout = (LinearLayout) findViewById(R.id.drinkLayout);
            count = toppingLayout.getChildCount();
            for (int i = 0; i < count; i++) {
                LinearLayout view = (LinearLayout) drinkLayout.getChildAt(i);
                int count2 = view.getChildCount();
                for (int j = 0; j < count2; j++) {
                    View view2 = view.getChildAt(j);
                    if (view2 instanceof CheckBox) {
                        if (((CheckBox) view2).isChecked()) {
                            drinkList.add(((CheckBox) view2).getText().toString());
                        }
                    }
                }
            }

            String drinkString = "";
            if (!drinkList.isEmpty()) {
                drinkString = String.join(", ", drinkList);
            } else {
                drinkString = noneText;
            }



            msg = yourOrderText +":\n - "+ toppingText +": " + toppingString + "\n - "+sizeText+": " + selectedSize.getText().toString() + "\n - "+ crustText +": " + selectedCrust.getText().toString() +  "\n - "+ drinkText+": " + drinkString + " ";

        }
        return msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        addListener();
    }


    public void addListener() {
            Button orderBtn = (Button) findViewById(R.id.orderButton);
            Button detailBtn = (Button) findViewById(R.id.detailButton);
            Button locationBtn = (Button) findViewById(R.id.locationButton);
            WebView detailWebView = (WebView) findViewById(R.id.detailWebView);


            orderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = getOrder();
                    if (!msg.equals("")){
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:12345"));
                        intent.putExtra("sms_body", msg);
                        startActivity(intent);
                    }

                }
            });



            detailBtn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onClick(View v) {
                    detailWebView.getSettings().setJavaScriptEnabled(true);
                    detailWebView.loadUrl("file:///android_asset/detail.html");
                    detailWebView.getSettings().setAllowContentAccess(true);
                    detailWebView.getSettings().setAllowFileAccess(true);
                    detailWebView.setWebViewClient(new WebViewClient(){
                        public void onPageFinished(WebView view, String url){
                            detailWebView.loadUrl("javascript:getDetail('" + getOrder() + "')");
                        }
                    });
                }
            });


            locationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/maps/Mf34SexmEDAMNBnJ7"));
                    startActivity(intent);
                }
            });
    }
}
