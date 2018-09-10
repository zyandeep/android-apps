package com.example.zyandeep.explicitintentdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView t = findViewById(R.id.result);

        //Intent i = getIntent();
        //String data = i.getStringExtra("UserData");

        t.setText(getIntent().getExtras().getString("UserData"));
    }
}
