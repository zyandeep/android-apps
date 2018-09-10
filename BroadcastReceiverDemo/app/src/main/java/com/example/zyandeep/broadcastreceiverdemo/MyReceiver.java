package com.example.zyandeep.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_BATTERY_LOW:
                Toast.makeText(context, "Your battery is Low", Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                Toast.makeText(context, "Airplane mode changed", Toast.LENGTH_SHORT).show();
                break;

            case MainActivity.MY_ACTION:
                Toast.makeText(context, "My own broadcast received", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}