package com.example.zyandeep.bootloaderapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            //Log.i("Receiver", "boot completed");

            final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);


            new CountDownTimer(5000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {}

                @Override
                public void onFinish() {
                    // show a notification
                    builder.setSmallIcon(R.drawable.ic_sync)
                            .setContentTitle("My App Notification")
                            .setContentText("Synced has been started")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setDefaults(NotificationCompat.DEFAULT_ALL);

                    manager.notify(22, builder.build());
                }
            }.start();
        }
    }
}