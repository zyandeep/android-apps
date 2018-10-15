package com.example.zyandeep.detecthardware;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class PowerButtonService extends Service{
    private MyReceiver myReceiver;

    MyUtilityClass utility;


    public PowerButtonService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(MainActivity.TAG, "PowerButtonService created");

        // For "ACTION_SCREEN_ON", "ACTION_SCREEN_OFF", "ACTION_USER_PRESENT"
        // Register the broadcast receiver dynamically
        // called when the service gets created                 // only once

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(MyReceiver.ACTION_SMS_SENT);
        filter.addAction(MyReceiver.ACTION_SMS_DELIVERED);

        myReceiver = new MyReceiver();
        registerReceiver(myReceiver, filter);


        utility = new MyUtilityClass(getApplicationContext());
    }



    // do the SOS related work here
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getBooleanExtra(MyReceiver.SOS_TRIGGERED, false)) {

            // Is LOCATION_PERMISSION enable ?
            // Is SMS_PERMISSION enable ?
            // Check whether Mobile Data and Location Setting are ON

            // IF ALL ARE YES then start SOS process

            Log.d(MainActivity.TAG, "Starting SOS Service...");

            if (utility != null) {
                utility.startSOSProcess();
            }
            // give user a haptic feedback only when Power button was pressed

            if (intent.getIntExtra(MyReceiver.BUTTON_PRESSED, 0) == 5) {
                Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(1000);
            }
        }

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
        }

        Log.d(MainActivity.TAG, "Service destroyed");
    }





    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
}
