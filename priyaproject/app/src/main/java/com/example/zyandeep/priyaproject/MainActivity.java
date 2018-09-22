package com.example.zyandeep.priyaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        a = findViewById(R.id.t2);
    }

    public void show_name(View view) {
        //Toast.makeText(this, "Hi Priya", Toast.LENGTH_LONG).show();

        String data = a.getText().toString();
        Toast.makeText(this, "Hi"    +  " " + data, Toast.LENGTH_SHORT).show();
    }
}
