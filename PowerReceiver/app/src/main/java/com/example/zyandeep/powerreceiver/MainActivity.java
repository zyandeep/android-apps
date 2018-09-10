package com.example.zyandeep.powerreceiver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private static final String MY_ACTION = "com.example.zyandeep.powerreceiver.CUSTOM_ACTION";
    MyReceiver myReceiver = new MyReceiver();
    IntentFilter filter = new IntentFilter(MY_ACTION);

    PackageManager packageManager;
    ComponentName componentName;

    ToggleButton tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        packageManager = getPackageManager();
        componentName = new ComponentName(this, MyReceiver.class);


        tb = findViewById(R.id.toggleButton);
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    // enable the receiver
                }
                else {
                    // disable the receiver
                }
            }
        });


        // register local broadcast receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, filter);
    }

    public void sendCustomBroadcast(View view) {
        Intent customBroadcast = new Intent();
        customBroadcast.setAction(MY_ACTION);

        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcast);
    }


    @Override
    protected void onStart() {
        super.onStart();

        //packageManager
        //        .setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }


    @Override
    protected void onStop() {
        super.onStop();

        //packageManager
                //.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // unregister local broadcast receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
    }
}
