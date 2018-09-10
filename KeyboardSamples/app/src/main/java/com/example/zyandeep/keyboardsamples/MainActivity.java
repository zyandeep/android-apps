package com.example.zyandeep.keyboardsamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private String label = "";
    private EditText ed;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed = findViewById(R.id.editText);
        tv = findViewById(R.id.phoneTextView);


        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                label = adapterView.getItemAtPosition(i).toString();

                showText(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        ArrayAdapter<CharSequence> ad =
                ArrayAdapter.createFromResource(this, R.array.labels_array, android.R.layout.simple_spinner_item);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(ad);
    }

    public void showText(View view) {
        String msg = ed.getText().toString();
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        String finalText = msg + " - " + label;
        tv.setText(finalText);
    }
}