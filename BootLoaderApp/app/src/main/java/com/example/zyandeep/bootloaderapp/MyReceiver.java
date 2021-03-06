package com.example.zyandeep.bootloaderapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MY_BOOT_APP";


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "boot completed");
        Toast.makeText(context, "Boot completed", Toast.LENGTH_SHORT).show();
    }
}