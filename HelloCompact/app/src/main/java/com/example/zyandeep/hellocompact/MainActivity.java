package com.example.zyandeep.hellocompact;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private String[] colors = {"red", "pink", "purple", "deep_purple",
            "indigo", "blue", "light_blue", "cyan", "teal", "green",
            "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
            "brown", "grey", "blue_grey", "black"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);

        if (savedInstanceState != null) {
            tv.setTextColor(savedInstanceState.getInt("color"));
        }
    }


    public void changeColor(View view) {
        String picColor = colors[new Random().nextInt(20)];

        // get the resource id form the string
        int resId = getResources().getIdentifier(picColor, "color", getPackageName());

        int colorID = ContextCompat.getColor(this, resId);


        tv.setTextColor(colorID);

        //Log.d("color", "" + resId);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("color", tv.getCurrentTextColor());
    }
}
