package com.example.zyandeep.debugapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText op1, op2;
    TextView r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        op1 = findViewById(R.id.op1_editText);
        op2 = findViewById(R.id.op2_editText);
        r = findViewById(R.id.ans_textView);
    }

    public void doAdd(View view) {
        int num1 = Integer.parseInt(op1.getText().toString());
        int num2 = Integer.parseInt(op2.getText().toString());


        int res = num1 + num2;
        r.setText("" + res);
    }
}