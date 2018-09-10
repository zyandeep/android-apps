package com.example.zyandeep.startedservicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void doSomething(View view) {
        Intent i = new Intent(this, MyStartedService.class);

        switch (view.getId()) {
            case R.id.button1:
                //Log.i("Service", "start");
                startService(i);
                break;

            case R.id.button2:
                //Log.i("Service", "stop");
                stopService(i);
                break;

            case R.id.button3:
                //Log.i("Service", "start again");
                //break;
                Intent i1 = new Intent(this, MyIntentService.class);
                i1.putExtra("name", "John");
                startService(i1);

                Intent i2 = new Intent(this, MyIntentService.class);
                i2.putExtra("name", "Bob");
                startService(i2);
        }
    }
}