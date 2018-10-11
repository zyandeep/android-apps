package com.example.zyandeep.walkmyandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int MY_LOCATION_PERMISSION = 912;
    private final String TAG = "MY_LOCATION_APP";

    TextView mTextView;
    Button startButton, stopButton;


    private boolean havePermission = false;

    private boolean trackingLocation = false;       // whether the location is being tracked???

    Location mLocation;
    FusedLocationProviderClient locationProviderClient;
    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.my_tv);
        startButton = findViewById(R.id.button_start);
        stopButton = findViewById(R.id.button_stop);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);


        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mLocation = locationResult.getLastLocation();
                Log.d(TAG, mLocation.toString());

                new MyAsyncTask(getApplicationContext()).execute(mLocation);
            }
        };


        updateUI();
    }



    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // ask for the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_LOCATION_PERMISSION);
        } else {
            // Permission granted for accessing Location data
            this.havePermission = true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_LOCATION_PERMISSION) {
            if (permissions[0].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // access the Location service
                this.havePermission = true;
            }
            else {

                this.havePermission = false;
                Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void checkGPSEnable() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

        // Get a reference to device's setting
        SettingsClient client = LocationServices.getSettingsClient(this);
        client.checkLocationSettings(builder.build()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {

                    // Location settings are not satisfied
                    // show the user a dialog

                    try {
                        ((ResolvableApiException) e).startResolutionForResult(MainActivity.this, 220);

                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }



    @SuppressLint("MissingPermission")
    public void getLocation(View view) {
        askPermission();

        if (havePermission) {
            // get the location

            // Need to check the correct location setting on the device
            checkGPSEnable();


            locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location == null) {
                        mTextView.setText("Location data not available");
                    }
                    else {
                        mLocation = location;

                        Log.d(TAG, mLocation.getLatitude() + " : " + mLocation.getLongitude());

                        new MyAsyncTask(getApplicationContext()).execute(mLocation);
                    }
                }
            });
        }
    }



    @SuppressLint("MissingPermission")
    public void startTrackingLocation(View view) {
        askPermission();

        if (havePermission) {

            // Need to check the correct location setting on the device
            checkGPSEnable();

            this.trackingLocation = true;
            locationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper() /* null */);

            updateUI();
        }
    }

    public void stopTrackingLocation(View view) {
        this.trackingLocation = false;

        locationProviderClient.removeLocationUpdates(mLocationCallback);

        updateUI();
    }


    private void updateUI() {
        if (trackingLocation) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
        else {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (trackingLocation) {
            stopTrackingLocation(null);

            trackingLocation = true;
        }
    }




    @Override
    protected void onResume() {
        super.onResume();

        if (trackingLocation) {
            startTrackingLocation(null);
        }
    }









    /////////////////////// GeoCoding / Reverse-geocoding in the background
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    private class MyAsyncTask extends AsyncTask<Location, Void, String> {

        private Context mContext;

        public MyAsyncTask(Context mContext) {
            this.mContext = mContext;
        }


        @Override
        protected String doInBackground(Location... locations) {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            Location loc = locations[0];
            List<Address> addresses = null;
            String result = "";

            try {
                // maxResult : No of addressess to map
                addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);


                if (addresses == null || addresses.size() == 0) {
                    // didn't find an address equivalent to the lat and long
                    result = "";
                }
                else {
                    // found an address equivalent to the lat and long
                    Address address = addresses.get(0);
                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                        stringBuilder.append(address.getAddressLine(i));
                    }

                    result = String.format("Address: %s \nTimestamp: %tr", stringBuilder.toString(), loc.getTime());
                }
            }
            catch (Exception e) {
                Log.d("MY_Location:", "Error in reverse-geocoding");
            }

            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.isEmpty()) {
                mTextView.setText("Unknown location");
            }
            else {
                mTextView.append(s + "\n");
            }
        }
    }
}