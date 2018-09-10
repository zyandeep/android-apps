package com.example.zyandeep.implicitintentdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText web;
    private EditText loc;
    private EditText text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web = findViewById(R.id.website_editText);
        loc = findViewById(R.id.loc_editText);
        text = findViewById(R.id.share_editText);


    }

    public void openWebsite(View view) {
        String webData = web.getText().toString();

        // Create the implicit intent and fire it
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(webData));

        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

    public void openMap(View view) {
        // maps uri scheme: geo:0,0?q=my+street+address

        String data = loc.getText().toString();
        Uri l = Uri.parse("geo:0,0?q=" + data);

        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setData(l);


        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }

    public void shareText(View view) {
        String data = text.getText().toString();

        /*Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, data);

        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
        else {
            Log.d(MainActivity.class.getSimpleName(), "No app to share a piece of text!");
        }*/

        // With the help of Helper class
        ShareCompat.IntentBuilder.
                from(this).setType("text/plain").setChooserTitle("Share this text with").setText(data).startChooser();
    }
}