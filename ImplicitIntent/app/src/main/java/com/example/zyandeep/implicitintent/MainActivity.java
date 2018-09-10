package com.example.zyandeep.implicitintent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doAction(View view) {
        Intent i = new Intent();        // new Intent(action, uri)

        switch (view.getId()) {
            case R.id.web:
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.howtogeek.com/"));
                break;

            case R.id.call:
                i.setAction(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:9859335453"));
                break;

            case R.id.map:
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("geo:26.746520,94.202586"));
                break;

        }

        // start the activity or fire the intent
        // But resolve the activity first

        if (i.resolveActivity(getPackageManager()) != null ) {
            startActivity(i);
        }
        else {
            // No app to handle that request
        }
    }
}