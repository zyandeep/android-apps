package com.example.zyandeep.explicitintentdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText e1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.editText);
    }

    public void nextScreen(View view) {
        Intent i = new Intent(this, SecondActivity.class);
        i.putExtra("UserData", e1.getText().toString());

        startActivity(i);
    }
}
