package com.example.zyandeep.smsreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReaderReceiver extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.mContext = context;

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


                Toast.makeText(context, senderAddress + " : " + msgBody, Toast.LENGTH_LONG).show();

                sendReply(senderAddress);
            }
        }
    }


    private void sendReply(String number) {
        SmsManager manager = SmsManager.getDefault();
        String reply = mContext.getString(R.string.reply_text);

        manager.sendTextMessage(number, null, reply, null, null);
    }
}