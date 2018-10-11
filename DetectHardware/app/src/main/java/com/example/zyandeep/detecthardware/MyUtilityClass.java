package com.example.zyandeep.detecthardware;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.net.URL;
import java.util.List;
import java.util.Locale;


public class MyUtilityClass {

    FusedLocationProviderClient mLocationProviderClient;
    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;

    Context mContext;

    private int mCounter = 0;

    private static final String DEFAULT_SOS_MSG = "I'M IN DANGER";
    private static final String DEFAULT_CONTACT_NO = "9859335453";


    public MyUtilityClass(final Context mContext) {
        this.mContext = mContext;

        mLocationProviderClient =  LocationServices.getFusedLocationProviderClient(mContext);

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
                    new MyAsyncTask(mContext).execute(location);
                }
            }
        };
    }


    @SuppressLint("MissingPermission")
    public void startSOSProcess() {

        if (mLocationProviderClient != null) {
            mCounter = 0;

            // request periodic location updates
            mLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper() /* null */);
        }
    }


    private void sendSMS(String location, String mapUrl,  String phNo) {

        // remove periodic location updates
        mLocationProviderClient.removeLocationUpdates(mLocationCallback);

        Log.d(MainActivity.TAG, mapUrl);

        SmsManager smsManager = SmsManager.getDefault();
        String smsBody = DEFAULT_SOS_MSG + "\n" + mapUrl;


        PendingIntent sentIntent = PendingIntent.getBroadcast(mContext, 20, new Intent(MyReceiver.ACTION_SMS_SENT),
                PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent deliveryIntent = PendingIntent.getBroadcast(mContext, 73, new Intent(MyReceiver.ACTION_SMS_DELIVERED),
                PendingIntent.FLAG_UPDATE_CURRENT);

        smsManager.sendTextMessage(phNo, null, smsBody, sentIntent, deliveryIntent);
    }



    /////////////////////// GeoCoding / Reverse-geocoding in the background
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private class MyAsyncTask extends AsyncTask<Location, Void, String> {

        private Context mContext;

        private Location mLocation;


        public MyAsyncTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(Location... locations) {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            mLocation = locations[0];
            List<Address> addresses = null;
            String result = "";

            try {
                // maxResult : No of addressess to map
                addresses = geocoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);


                if (addresses == null || addresses.size() == 0) {

                    // didn't find an address equivalent to the lat and long
                    result = "Couldn't get the Location data";
                }
                else {
                    // found an address equivalent to the lat and long
                    Address address = addresses.get(0);
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                        stringBuilder.append(address.getAddressLine(i));
                    }

                    result = String.format("Address: %s \nTime: %tr", stringBuilder.toString(), mLocation.getTime());
                }
            }
            catch (Exception e) {
                Log.d(MainActivity.TAG, "Error in reverse-geocoding");
            }

            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // send the Location to emergency contacts

            // Google Map URL format
            // https://www.google.com/maps/search/?api=1&query=47.5951518,-122.3316393

            String url = String.format("https://www.google.com/maps/search/?api=1&query=%f,%f",
                                        mLocation.getLatitude(), mLocation.getLongitude());

            sendSMS(s, url, DEFAULT_CONTACT_NO);
        }
    }
}