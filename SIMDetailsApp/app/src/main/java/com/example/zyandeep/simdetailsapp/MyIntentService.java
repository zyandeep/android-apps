package com.example.zyandeep.simdetailsapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Set;


public class MyIntentService extends IntentService {

    private static final String PREF_FILE_NAME = "mca.project.my_pref";
    private static final String KEY_SIM = "sim_details";

    public MyIntentService() {
        super("MyIntentService");

        Log.d(MainActivity.TAG, "IntentService created");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sh = getApplicationContext().getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);

        Set<String> simDetails = sh.getStringSet(KEY_SIM, null);

        if (simDetails != null) {
            Log.d(MainActivity.TAG, simDetails.toString());


        }
    }
}