package com.example.zyandeep.implicitintentreceiver;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t = findViewById(R.id.uri_textView);

        Intent i = getIntent();
        Uri uri = i.getData();

        if (uri != null) {
            t.setText(uri.toString());
        }
    }
}