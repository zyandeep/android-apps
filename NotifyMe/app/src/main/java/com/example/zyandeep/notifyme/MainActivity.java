package com.example.zyandeep.notifyme;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button b1, b2, b3;
    NotificationManagerCompat manager;
    NotificationCompat.Builder builder;

    private static final int NOTIFICATION_ID = 100;
    private static final String ACTION_UPDATE_NOT = "com.example.zyandeep.notifyme.ACTION_UPDATE_NOT";
    private static final String ACTION_CLEAR_NOT = "com.example.zyandeep.notifyme.ACTION_CLEAR_NOT";

    MyBroadCastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // My Receiver
        receiver = new MyBroadCastReceiver();

        // register receiver
        registerReceiver(receiver, new IntentFilter(ACTION_UPDATE_NOT));
        registerReceiver(receiver, new IntentFilter(ACTION_CLEAR_NOT));

        b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

        b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotification();
            }
        });


        b3 = findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNotification();
            }
        });



        b1.setEnabled(true);
        b2.setEnabled(false);
        b3.setEnabled(false);
    }


    private void cancelNotification() {
        manager.cancel(NOTIFICATION_ID);

        b1.setEnabled(true);
        b2.setEnabled(false);
        b3.setEnabled(false);
    }


    private void updateNotification() {

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);

        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/design/patterns/notifications.html"));
        PendingIntent pendingWeb = PendingIntent.getActivity(this, NOTIFICATION_ID, web, PendingIntent.FLAG_ONE_SHOT);

        manager = NotificationManagerCompat.from(this);
        builder = new NotificationCompat.Builder(this);         // depreciated, still
        builder.setContentTitle("You've been notified!")
                .setContentText("This is your notification text")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_info, "Learn More", pendingWeb)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigPictureStyle()
                              .setBigContentTitle("Notification Updated")
                              .bigPicture(image));


        manager.notify(NOTIFICATION_ID, builder.build());

        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(true);
    }


    public void sendNotification() {
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, i, 0);

        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/design/patterns/notifications.html"));
        PendingIntent pendingWeb = PendingIntent.getActivity(this, NOTIFICATION_ID, web, PendingIntent.FLAG_ONE_SHOT);

        Intent brodast = new Intent(MainActivity.ACTION_UPDATE_NOT);
        PendingIntent pendingBroadcast = PendingIntent.getBroadcast(this, NOTIFICATION_ID, brodast, PendingIntent.FLAG_ONE_SHOT);


        Intent del = new Intent(MainActivity.ACTION_CLEAR_NOT);
        PendingIntent pendingClear = PendingIntent.getBroadcast(this, NOTIFICATION_ID, del, PendingIntent.FLAG_ONE_SHOT);



        manager = NotificationManagerCompat.from(this);
        builder = new NotificationCompat.Builder(this);         // depreciated, still
        builder.setContentTitle("You've been notified!")
                .setContentText("This is your notification text")
                .setSmallIcon(R.drawable.ic_android)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1))        // a jpeg image
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_info, "Learn More", pendingWeb)
                .addAction(R.drawable.ic_update, "Update", pendingBroadcast)
                .setDeleteIntent(pendingClear);
                //.setAutoCancel(true);


        // let the notification "PEEK"
        builder.setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);


        manager.notify(NOTIFICATION_ID, builder.build());

        // close the activity
        //finish();


        b1.setEnabled(false);
        b2.setEnabled(true);
        b3.setEnabled(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // unregister the receiver
        unregisterReceiver(receiver);
    }

    // Receiver class
    public class MyBroadCastReceiver extends BroadcastReceiver {

        public MyBroadCastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // check the action
            switch (intent.getAction()) {
                case ACTION_UPDATE_NOT:
                    updateNotification();
                    break;

                case ACTION_CLEAR_NOT:
                    cancelNotification();
            }
        }
    }
}