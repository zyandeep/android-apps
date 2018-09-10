package com.example.zyandeep.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final String MY_ACTION = "com.example.zyandeep.powerreceiver.CUSTOM_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch (action) {
            case Intent.ACTION_POWER_CONNECTED:
                showToast(context, "Power Connected!");
                break;

            case Intent.ACTION_POWER_DISCONNECTED:
                showToast(context, "Power Disconnected!");
                break;

            case MY_ACTION:
                showToast(context, "Local broadcast received");
        }
    }

    private void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}