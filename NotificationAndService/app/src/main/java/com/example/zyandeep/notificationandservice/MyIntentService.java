package com.example.zyandeep.notificationandservice;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;


public class MyIntentService extends Service {

    public static final String MY_ACTION = "zyandeep.UPDATE_TEXTVIEW";
    public static final int MY_NOTIFICATION = 22;

    Boolean isRunning = true;
    int counter = 1;



    @Override
    public void onCreate() {
        super.onCreate();

        // intent to lauch the main activity
        Intent launch = new Intent(this, MainActivity.class);       // expliciti intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, MY_NOTIFICATION, launch, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                                            .setSmallIcon(R.drawable.ic_android)
                                            .setContentTitle("A Service is running!")
                                            .setContentText("You can't dismiss it, HAHA!!")
                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
                                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                                            .setContentIntent(pendingIntent)
                                            .setDefaults(NotificationCompat.DEFAULT_ALL);

        // make it a forground service
        startForeground(MY_NOTIFICATION, builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Run a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        Thread.sleep(1000);

                        // send out a local broadcast
                        Intent broad = new Intent(MY_ACTION);
                        broad.putExtra("data", counter++);


                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broad);
                    }
                    catch (Exception e) {}
                }
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        // stop the thread
        this.isRunning = false;

        super.onDestroy();
    }
}