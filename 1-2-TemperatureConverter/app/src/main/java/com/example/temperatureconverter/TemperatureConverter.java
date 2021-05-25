package com.example.temperatureconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TemperatureConverter extends AppCompatActivity {

    EditText input;
    TextView output;
    RadioButton CtoF;
    RadioButton FtoC;
    TextView history;

    List<String> historyList = new ArrayList<String>();

    public static double CtoF(double c){
        double result = c*9/5+32;
        return Math.round(result * 100.0) / 100.0;
    }

    public static double FtoC(double f){
        double result = (f-32)*5/9;
        return Math.round(result * 100.0) / 100.0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        addListener();
    }

    public void addListener() {
        EditText input = (EditText) findViewById(R.id.input);
        TextView output = (TextView) findViewById(R.id.output);
        RadioButton CtoF = (RadioButton) findViewById(R.id.CtoF);
        RadioButton FtoC = (RadioButton) findViewById(R.id.FtoC);
        TextView history = (TextView) findViewById(R.id.history);
        Button convertBtn = (Button) findViewById(R.id.convert);
        convertBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String ctofHistory = getString(R.string.ctofHistory);
                 String ftocHistory = getString(R.string.ftocHistory);
                 String blankWarning = getString(R.string.blankWarning);
                 if (input.getText().toString().matches("")){
                     Toast.makeText(TemperatureConverter.this, blankWarning, Toast.LENGTH_LONG).show();
                 }
                 else{
                     double valueBefore = new Double(input.getText().toString());
                     double valueAfter = 0;
                     if(FtoC.isChecked()){
                         valueAfter = FtoC(valueBefore);
                         historyList.add(0,ftocHistory+": " + valueBefore + " -> " + new Double(valueAfter).toString());
                     }
                     else{
                         valueAfter = CtoF(valueBefore);
                         historyList.add(0,ctofHistory+": " + valueBefore + " -> " + new Double(valueAfter).toString());
                     }
                     output.setText(new Double(valueAfter).toString());
                     StringBuilder builder = new StringBuilder();
                     for ( String item : historyList){
                         builder.append(item).append("\n");
                     }
                     history.setText(builder.toString());
                 }

             }
        });


    }
}