package com.example.zyandeep.mymessagingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MySMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case MainActivity.ACTION_SMS_SENT:
                showToast(context, "SMS Sent");
                break;

            case MainActivity.ACTION_SMS_DELIVERED:
                showToast(context, "SMS delivered");
                break;
        }
    }


    private void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}