package com.example.zyandeep.notificationandservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Switch aSwitch;

    MyReceiver receiver;
    public static final String MY_ACTION = "zyandeep.UPDATE_TEXTVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv = findViewById(R.id.counter_textView);
        aSwitch = findViewById(R.id.my_switch);

        final Intent serviceIntent = new Intent(getApplicationContext(), MyIntentService.class);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    // reset the textview
                    tv.setText("0");

                    // start the service
                    startService(serviceIntent);
                }
                else {
                    // stop the service
                    stopService(serviceIntent);
                }
            }
        });

        receiver = new MyReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(MY_ACTION));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }


    // My Receiver
    public class MyReceiver extends BroadcastReceiver {
        MyReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.i("Receiver", intent.getAction());
            MainActivity.this.tv.setText("" + intent.getIntExtra("data", 0));
        }
    }
}