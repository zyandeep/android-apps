package com.example.zyandeep.helloandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(MainActivity.class.getSimpleName(), "Hello World");

        try {
            int a = 12 / 0;
        }
        catch (Exception e) {
            //Log.e("Error: ", "Divide/0", e);
            Log.getStackTraceString(e);
        }


    }
}
