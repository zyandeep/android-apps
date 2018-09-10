package com.example.zyandeep.myreceverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class MyReceiver02 extends BroadcastReceiver {

    public static final String MY_ACTION = "com.example.zyandeep.broadcastreceiverdemo.myOwnBroadcastEvent";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MY_ACTION)) {
            Toast.makeText(context, "My Second Receiver", Toast.LENGTH_SHORT).show();
        }
    }
}