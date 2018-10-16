package com.example.zyandeep.detecthardware;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.preference.Preference;
import android.support.v4.app.ActivityCompat;
import android.support.v7.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


public class MyUtilityClass {

    FusedLocationProviderClient mLocationProviderClient;
    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;

    Context mContext;

    private int mCounter = 0;

    private static final String DEFAULT_PHONE_NUMBER = "9859335453";


    public MyUtilityClass(final Context mContext) {
        this.mContext = mContext;

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

                if (mCounter == 2) {

                    // get at max two location updates
                    //new MyAsyncTask(mContext).execute(location);

                    // or send sms directly

                    String url = String.format("https://www.google.com/maps/search/?api=1&query=%f,%f",
                            location.getLatitude(), location.getLongitude());

                    sendSMS(url, ((MainActivity.mPhoneNumber == null) ? DEFAULT_PHONE_NUMBER : MainActivity.mPhoneNumber));
                }
            }
        };

    }


    @SuppressLint("MissingPermission")
    public void startSOSProcess() {

        if (mLocationProviderClient != null) {
            mCounter = 0;

            mLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper() /* null */);
        }
    }


    private void sendSMS(String mapUrl,  String phNo) {

        // remove periodic location updates
        mLocationProviderClient.removeLocationUpdates(mLocationCallback);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(mContext);

        String sosMsg = sh.getString("user_msg",mContext.getString(R.string.default_msg));

        //Log.d(MainActivity.TAG, sosMsg);



        SmsManager smsManager = SmsManager.getDefault();
        String smsBody = sosMsg + "\n" + mapUrl;

        Log.d(MainActivity.TAG, smsBody);

        PendingIntent sentIntent = PendingIntent.getBroadcast(mContext, 20, new Intent(MyReceiver.ACTION_SMS_SENT),
                PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent deliveryIntent = PendingIntent.getBroadcast(mContext, 73, new Intent(MyReceiver.ACTION_SMS_DELIVERED),
                PendingIntent.FLAG_UPDATE_CURRENT);

        smsManager.sendTextMessage(phNo, null, smsBody, sentIntent, deliveryIntent);
    }
}