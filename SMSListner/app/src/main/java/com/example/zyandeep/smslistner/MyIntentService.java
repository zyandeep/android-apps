package com.example.zyandeep.smslistner;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class MyIntentService extends IntentService {

    public static final String MY_ACTION = "zyandeep.SHOW_NOTIFICATION";


    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // send out a broadcast to notify user
        Intent i = new Intent(MY_ACTION);
        i.putExtra("name", "Dipsikha Phukan");
        i.putExtra("ph_no", "12345");

        getApplicationContext().sendBroadcast(i);
    }
}