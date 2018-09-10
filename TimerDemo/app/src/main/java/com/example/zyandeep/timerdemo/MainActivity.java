package com.example.zyandeep.timerdemo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*final Handler handler = new Handler();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Log.i("Timer", "Hello");

                handler.postDelayed(this, 1000);
            }
        };

        handler.post(task);*/


        // Counter
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                Log.i("CDTimer:", String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                Log.i("CDTimer:", "Finished");
            }
        }.start();



    }
}