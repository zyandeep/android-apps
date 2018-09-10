package com.example.zyandeep.takemedicinealarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class MyAlarmReceiver extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 2002;
    public static final String ACTION_MY_ALARM = "zyandeep.ACTION_MY_ALARM";
    public static final String ACTION_DISMISS_ALARM = "zyandeep.ACTION_DISMISS_ALARM";


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);


        if (action.equals(ACTION_MY_ALARM)) {

            Intent i = new Intent(context, SurviveActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, i, 0);

            Intent i2 = new Intent(ACTION_DISMISS_ALARM);
            PendingIntent p2 = PendingIntent.getBroadcast(context, NOTIFICATION_ID, i2, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.drawable.ic_alarm)
                    .setContentTitle("It's Medicine Time!")
                    .setContentText("Take the freaking meds, You Dying Moron")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .addAction(R.drawable.ic_no_icon, "Dismiss Alarm", p2)
                    .setAutoCancel(true);

            manager.notify(NOTIFICATION_ID, builder.build());
        }
        else if (action.equals(ACTION_DISMISS_ALARM)) {
            MainActivity.dismissAlarm(context);

            manager.cancelAll();
        }
    }
}