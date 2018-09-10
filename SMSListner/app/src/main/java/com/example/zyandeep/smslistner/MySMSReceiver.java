package com.example.zyandeep.smslistner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

public class MySMSReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(final Context context, Intent intent) {

        final Context ctx = context;
        String action = intent.getAction();

        if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            //Log.i("My Receiver", "SMS received");

            Toast.makeText(context, "SMS received", Toast.LENGTH_SHORT).show();

            // start a service
            context.startService(new Intent(context, MyIntentService.class));
        }
    }
}