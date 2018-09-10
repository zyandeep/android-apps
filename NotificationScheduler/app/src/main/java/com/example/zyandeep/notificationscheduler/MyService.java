package com.example.zyandeep.notificationscheduler;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class MyService extends JobService {
    public static final int MY_ID = 10;

    public MyService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent intent = PendingIntent.getActivity(this, MY_ID, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.ic_job)
                                    .setContentTitle("Job Service")
                                    .setContentText("Your Job is running!")
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                                    .setAutoCancel(true)
                                    .setContentIntent(intent);

        manager.notify(MY_ID, builder.build());

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}