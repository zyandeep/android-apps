package com.example.zyandeep.countproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView number;

    int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = findViewById(R.id.t1);

    }

    public void show_number(View view) {

        Toast.makeText(this,  number.getText().toString(), Toast.LENGTH_SHORT).show();

    }

    public void count_up(View view) {
        a=a+1;
        number.setText(String.valueOf(a));
    }
}
