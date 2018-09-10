package com.example.zyandeep.sqlitedemo;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText college;

    TextView tv;

    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        college = findViewById(R.id.college);
        tv = findViewById(R.id.text_result);

        dbHelper = new MyDBHelper(getApplicationContext());
    }

    public void doSave(View view) {
        String u_name = name.getText().toString();
        String u_college = college.getText().toString();

        dbHelper.insertData(u_name, u_college);
    }

    public void doLoad(View view) {
        String data = dbHelper.getAll();

        if (data.isEmpty()) {
            tv.setText("No Data");
        }
        else {
            tv.setText(data);
        }
    }
}