package com.example.zyandeep.smslistner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class MyNotiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(MyIntentService.MY_ACTION)) {
            String name = intent.getStringExtra("name");
            String phNo = intent.getStringExtra("ph_no");

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);

            // notify the user
            NotificationManagerCompat manager = NotificationManagerCompat.from(context);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                                                .setSmallIcon(R.drawable.ic_contact_phone)
                                                .setContentTitle("Contact Info")
                                                .setContentText(name + " : " + phNo)
                                                .setLargeIcon(bitmap)
                                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                .setDefaults(NotificationCompat.DEFAULT_ALL);

            manager.notify(122, builder.build());
        }
    }
}