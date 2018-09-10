package com.example.zyandeep.takemedicinealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_MY_ALARM = "zyandeep.ACTION_MY_ALARM";
    public static final int MY_ID = 100;

    static Button b1, b2;
    static AlarmManager manager;
    boolean alarmExist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);

        manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Check if an alarm already exist
        alarmExist = PendingIntent.getBroadcast(this, MY_ID, new Intent(ACTION_MY_ALARM), PendingIntent.FLAG_NO_CREATE) != null;

        checkButtons(alarmExist);
    }


    public void startAlarm(View view) {
        Intent intent = new Intent(ACTION_MY_ALARM);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, MY_ID, intent, 0);

        long triggerTime = SystemClock.elapsedRealtime() + 3000;
        long intervalTime = 5000;

        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,  triggerTime, intervalTime, pendingIntent);

        checkButtons(true);

        Toast.makeText(this, "Alarm has been set", Toast.LENGTH_SHORT).show();
    }

    public void stopAlarm(View view) {
        dismissAlarm(this);
    }


    public static void checkButtons(boolean b) {

        if (b) {
            b1.setEnabled(false);
            b2.setEnabled(true);
        } else {
            b1.setEnabled(true);
            b2.setEnabled(false);
        }
    }


    public static void dismissAlarm(Context ctx) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, MY_ID, new Intent(ACTION_MY_ALARM), 0);

        manager.cancel(pendingIntent);
        checkButtons(false);

        Toast.makeText(ctx, "Alarm has been canceled", Toast.LENGTH_SHORT).show();
    }
}