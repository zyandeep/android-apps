package com.example.zyandeep.standup;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(MainActivity.MY_ACTION)) {
            deliverNotification(context);
        }
    }

    private void deliverNotification(Context context) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent intent = PendingIntent.getActivity(context, MainActivity.NOTIFICATION_ID, i, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(intent);

        manager.notify(MainActivity.NOTIFICATION_ID, builder.build());
    }
}
