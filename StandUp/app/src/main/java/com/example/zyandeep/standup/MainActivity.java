package com.example.zyandeep.standup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ToggleButton tb;

    AlarmManager alarmManager;

    public static final int NOTIFICATION_ID = 12;
    public static final String MY_ACTION = "zyandeep.standup_alarm.ACTION_NOTIFY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Check whether a pending intent already exist
        boolean alarmExist =
                PendingIntent.getBroadcast(this, NOTIFICATION_ID,  new Intent(MY_ACTION), PendingIntent.FLAG_NO_CREATE) != null;

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, new Intent(MY_ACTION), 0);

        tb = findViewById(R.id.tb);
        tb.setChecked(alarmExist);

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showToast("Stand Up! alarm on!");

                    long triggerTime = SystemClock.elapsedRealtime() + 3000;
                    long reapeatTime = 5000;

                    alarmManager.
                            setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, reapeatTime, pendingIntent);
                }
                else {
                    showToast("Stand Up! alarm off!");
                    NotificationManagerCompat.from(getApplicationContext()).cancelAll();
                    alarmManager.cancel(pendingIntent);
                }
            }
        });
    }


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showNextAlarm(View view) {
        showToast(alarmManager.getNextAlarmClock().toString());
    }
}