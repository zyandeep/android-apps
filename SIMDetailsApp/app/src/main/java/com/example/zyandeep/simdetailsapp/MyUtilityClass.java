package com.example.zyandeep.simdetailsapp;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Set;


public class MyUtilityClass {

    private static final String PREF_FILE_NAME = "mca.project.my_pref";
    private static final String KEY_IMIE = "imie";
    private static final String DELIMITER = "/";
    private static final String KEY_SERVICE_STATUS = "status";
    private static final String KEY_CONTACTS = "contacts";

    FusedLocationProviderClient mLocationProviderClient;
    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;

    Context mContext;

    private int mCounter = 0;

    private SharedPreferences sh;

    private String mSerialNos = "";
    private Set<String> mEmgContacts = null;
    private String mIMIENo = "";



    public MyUtilityClass(final Context mContext) {
        this.mContext = mContext;


        Log.d(MainActivity.TAG, "utility obj created");


        sh = mContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);


        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(3000);
        mLocationRequest.setFastestInterval(2000);


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Location location = locationResult.getLastLocation();
                mCounter++;

                Log.d(MainActivity.TAG, location.toString());

                if (mCounter == 3) {

                    // get at max three location updates
                    // and then send sms

                    String url = String.format("https://www.google.com/maps/search/?api=1&query=%f,%f",
                            location.getLatitude(), location.getLongitude());

                    sendSMS(url);
                }
            }
        };

    }


    @SuppressLint("MissingPermission")
    public void initProcess(String newSerialNos) {

        boolean status = sh.getBoolean(KEY_SERVICE_STATUS, true);
        Set<String> constactSet = sh.getStringSet(KEY_CONTACTS, null);

        Log.d(MainActivity.TAG, "utility called " + status + constactSet);


        if (status) {
            if (constactSet != null && constactSet.size() > 0) {
                if (mLocationProviderClient != null) {

                    this.mEmgContacts = constactSet;
                    this.mSerialNos = newSerialNos;
                    this.mIMIENo = sh.getString(KEY_IMIE, "");

                    mCounter = 0;
                    mLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper() /* null */);
                }
            }
            else {
                Toast.makeText(mContext, "Add emergency contacts first", Toast.LENGTH_LONG).show();

                // stop the service
                mContext.stopService(new Intent(mContext, MySimService.class));
            }
        }
        else {
            Toast.makeText(mContext, "Service is disabled", Toast.LENGTH_LONG).show();

            // stop the service
            mContext.stopService(new Intent(mContext, MySimService.class));
        }
    }


    private void sendSMS(String mapUrl) {

        // remove periodic location updates
        mLocationProviderClient.removeLocationUpdates(mLocationCallback);

        if (!mIMIENo.isEmpty() && !mSerialNos.isEmpty()) {
            Log.d(MainActivity.TAG, "IMIE No: " + mIMIENo);
            Log.d(MainActivity.TAG, "Serial No: " + mSerialNos);
            Log.d(MainActivity.TAG, "Phone Nos: " + mEmgContacts);
            Log.d(MainActivity.TAG, "Map URL: " + mapUrl);

            // send SMS
            SmsManager smsManager = SmsManager.getDefault();
            String smsBody = "IMIE no:" + mIMIENo + ",SIM serial no:" + mSerialNos + "Location:" + mapUrl;

            // send a multipart text message
           /* ArrayList<String> parts = new ArrayList<>();
            parts.add("Lost phone detected\n");
            parts.add("New SIM serial nos: " + mSerialNos);
            parts.add("Device IMIE No: " + mIMIENo);
            parts.add("Device Location: " + mapUrl);

            smsManager.sendMultipartTextMessage(PHONE_NO, null, parts, null, null);*/


           Log.d(MainActivity.TAG, smsBody);



           for (String ph : this.mEmgContacts) {
                ph = (ph.split(DELIMITER))[1];

                smsManager.sendTextMessage(ph, null, smsBody, null, null);

                Log.d(MainActivity.TAG, "sending... " + ph);
           }


            // stop the service
            mContext.stopService(new Intent(mContext, MySimService.class));
        }
        else {
            Log.d(MainActivity.TAG, "No data to send");
        }
    }
}