package com.example.zyandeep.simdetailsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SIMCardReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.d(MainActivity.TAG, action);


        if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
            // start the intent service
            Intent i = new Intent(context, MyIntentService.class);
            context.startService(i);
        }
    }
}