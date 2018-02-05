package com.example.homepc.acbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by home pc on 26/01/2018.
 */

public class FirstTimeActivity extends Activity{
    EditText cashText;
    EditText hdfcText;
    EditText sbiText;
    EditText hdfcccText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forFirstTime);

        cashText = (EditText)findViewById(R.id.cashCol);
        sbiText = (EditText)findViewById(R.id.sbiCol);
        hdfcText = (EditText)findViewById(R.id.hdfcCol);
        hdfcccText = (EditText)findViewById(R.id.hdfcccCol);



    }

    void returnToMain(View view){

       if(cashText.getText().toString().isEmpty() || sbiText.getText().toString().isEmpty() || hdfcText.getText().toString().isEmpty() || hdfcccText.getText().toString().isEmpty()){

           Toast.makeText(getApplicationContext(), "Enter record in proper format", Toast.LENGTH_SHORT).show();

       }else{


           Intent returnIntent = new Intent();
        returnIntent.putExtra("cash12",cashText.getText().toString());
        returnIntent.putExtra("sbi12",sbiText.getText().toString());
        returnIntent.putExtra("hdfc12",hdfcText.getText().toString());
        returnIntent.putExtra("hdfccc12",hdfcccText.getText().toString());

        setResult(RESULT_OK,returnIntent);
        finish();
       }
    }

}
