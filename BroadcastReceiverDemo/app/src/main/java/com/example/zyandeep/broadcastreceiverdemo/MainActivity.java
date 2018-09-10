package com.example.zyandeep.broadcastreceiverdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String MY_ACTION = "com.example.zyandeep.broadcastreceiverdemo.myOwnBroadcastEvent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendBroadcast(View view) {
        Intent i = new Intent(MY_ACTION);
        sendBroadcast(i);
    }
}
