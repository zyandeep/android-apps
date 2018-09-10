package com.example.zyandeep.mynotificationdemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.myTextView);
        Intent intent = getIntent();

        if (intent.getStringExtra("data") != null) {
            tv.setText(intent.getStringExtra("data"));
        }
    }

    public void showNotification(View view) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, getString(R.string.my_not_channel))
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_music)
                .setContentTitle("Refreshing Song")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentText("Ordinary World!");

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("data", "Yeah, It's beautiful!");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, i, 0);
        builder.setContentIntent(pendingIntent);


        // add action
        builder.addAction(R.drawable.ic_music, "Play", pendingIntent);


        manager.notify(1, builder.build());

        // close the activity
        finish();
    }
}