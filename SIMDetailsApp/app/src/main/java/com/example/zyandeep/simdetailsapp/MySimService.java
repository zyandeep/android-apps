package com.example.zyandeep.simdetailsapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.widget.Toast;


import java.util.List;
import java.util.Set;


public class MySimService extends Service {

    private static final String PREF_FILE_NAME = "mca.project.my_pref";
    private static final String KEY_SIM = "sim_details";

    private int counter = 1;
    private int noOfSims = 0;

    SubscriptionManager subscriptionManager;
    List<SubscriptionInfo> subscriptionInfos;


    MyUtilityClass mUtil;


    public MySimService() {
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(MainActivity.TAG, "service created");

        subscriptionManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
        subscriptionInfos = subscriptionManager.getActiveSubscriptionInfoList();

        noOfSims = subscriptionInfos.size();

        mUtil = new MyUtilityClass(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

            switch (noOfSims){

                case 1:
                    checkSIMDetails();

                    break;

                case 2:

                    if (counter < 2) {
                        counter++;
                    }
                    else {
                        checkSIMDetails();
                    }

                    break;

                default:
                   Log.d(MainActivity.TAG, "undefined case");
            }
        }
        else {
            Toast.makeText(this, "Grant all the permissions", Toast.LENGTH_LONG).show();
        }

        return START_STICKY;
    }



    private void checkSIMDetails() {

        // read already stored sim details
        SharedPreferences sh = getApplicationContext().getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        Set<String> simDetails = sh.getStringSet(KEY_SIM, null);

        String msg = "";


        if (simDetails != null) {

            // SIM details already exist
            Log.d(MainActivity.TAG, "Stored: " + simDetails.toString());

            if (subscriptionInfos != null) {
                for (SubscriptionInfo s : subscriptionInfos) {
                    String simSerialNo = s.getIccId();
                    int id = s.getSubscriptionId();

                    String data = simSerialNo + ":" + id;

                    Log.d(MainActivity.TAG, "New: " + data);


                    //Location, new sim serial no, imie no

                    if (!simDetails.contains(data)) {
                        Log.d(MainActivity.TAG, "New sim detected");

                        msg += simSerialNo + ",";
                    }
                }

                if (!msg.isEmpty() && mUtil != null) {
                    // send sim details + location
                    mUtil.initProcess(msg);
                }
                else {
                    // no new SIM detected
                    // stop the service
                    stopSelf();
                }

            }
        }
        else {
            Toast.makeText(this, "No SIM details exist. Save SIM details first", Toast.LENGTH_LONG).show();

            // stop the service
            stopSelf();
        }
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(MainActivity.TAG, "service destroyed");
    }
}