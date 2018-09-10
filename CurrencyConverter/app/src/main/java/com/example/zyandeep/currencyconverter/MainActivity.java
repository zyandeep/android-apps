package com.example.zyandeep.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void makeToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }



    public void convertCurrency(View v) {

        EditText amount = (EditText) findViewById(R.id.amountEditText);
        String amountInRupee = amount.getText().toString();

        if(amountInRupee.isEmpty()) {
            makeToast("Enter an amount");
        }
        else {
            //Log.i("Button", amountInRupee);

            double amt = 0.0;

            try {
                amt = Double.parseDouble(amountInRupee);
            }
            catch (Exception e) {
                makeToast("Invalid amount");
            }


            double amountInDollar = amt * 0.015;

            makeToast(String.format("Amount in Dollar %.2f", amountInDollar));
        }

    }
}
