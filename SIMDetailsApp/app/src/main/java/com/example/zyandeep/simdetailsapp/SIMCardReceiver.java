package com.example.zyandeep.simdetailsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class SIMCardReceiver extends BroadcastReceiver {

    private static final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";

    private static final String PREF_FILE_NAME = "mca.project.my_pref";
    private static final String KEY_SERVICE_STATUS = "status";

    SharedPreferences sh;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        sh = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean status = sh.getBoolean(KEY_SERVICE_STATUS, true);


        Log.d(MainActivity.TAG, "Service status: " + status);


        if (action.equals(ACTION_SIM_STATE_CHANGED) && status) {
            Bundle extras = intent.getExtras();

            if (extras != null) {
                for (String key : extras.keySet()) {

                    Object value = extras.get(key);


                    // start the service when SIM(s) are fully loaded

                    if (key.equals("ss") && value.equals("LOADED")) {
                        // start the service
                        Intent i = new Intent(context, MySimService.class);
                        context.startService(i);
                    }
                }
            }
        }
    }
}