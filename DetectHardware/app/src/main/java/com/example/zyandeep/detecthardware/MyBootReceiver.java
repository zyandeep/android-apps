package com.example.zyandeep.detecthardware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(MainActivity.TAG, "boot completed");
        Toast.makeText(context, "Boot completed", Toast.LENGTH_SHORT).show();

        // start the PowerButton Service
        context.startService(new Intent(context, PowerButtonService.class));
    }
}
