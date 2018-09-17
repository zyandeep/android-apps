package com.example.zyandeep.smsreader;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReaderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")) {

            // retrieve all the info as a bundle
            Bundle bundle = intent.getExtras();
            String senderAddress = "";
            String msgBody = "";

            String format = bundle.getString("format");

            // get the "pdus" from the bundle
            Object[] pdus = (Object[]) bundle.get("pdus");

            SmsMessage[] smsMessages = null;

            if (pdus != null) {

                smsMessages = new SmsMessage[pdus.length];

                for (int i = 0; i < smsMessages.length; i++) {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                    senderAddress += smsMessages[i].getOriginatingAddress();

                    msgBody += smsMessages[i].getMessageBody();
                }
            }

            Toast.makeText(context, senderAddress + " : " + msgBody, Toast.LENGTH_LONG).show();
        }
    }
}