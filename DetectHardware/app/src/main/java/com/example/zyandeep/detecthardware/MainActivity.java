package com.example.zyandeep.detecthardware;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "SOS_APP";

    private static final int MY_PERMISSION_REQUEST = 122;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // start the service
        startService(new Intent(this, PowerButtonService.class));

        // Do we have the required permissions
        // ACCESS_LOCATION and READ_SMS
        askPermission();

        (findViewById(R.id.my_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Make sure mobile data is ON and so is GPS

                checkDataConnection();

                checkGPS();


                Intent i = new Intent(MainActivity.this, PowerButtonService.class);
                i.putExtra(MyReceiver.SOS_TRIGGERED, true);

                // Trigger the SOS Service
                //startService(i);

                //Snackbar.make(findViewById(R.id.my_layout), "SOS Service Triggered", Snackbar.LENGTH_LONG).show();
            }
        });
    }



    private void checkDataConnection() {
        ConnectivityManager conMan = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = conMan.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {
            showSnackBar("Turn on Data connection");
        }
    }

    private void checkGPS() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);

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


    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // ask for the permission
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.SEND_SMS }, MY_PERMISSION_REQUEST);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST && grantResults.length > 0) {

            if (grantResults[0] == PackageManager.PERMISSION_DENIED && grantResults[1] == PackageManager.PERMISSION_DENIED) {
                showSnackBar("Turn on Location and SMS permission");
            }
            else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                showSnackBar("Turn on Location permission");
            }
            else if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
                showSnackBar("Turn on SMS permission");
            }
        }
    }


    private void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.my_layout), msg, Snackbar.LENGTH_LONG);

        snackbar.setAction("Go to settings", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send user to the setting activity

                Intent intent = new Intent(Settings.ACTION_SETTINGS);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        snackbar.show();
    }
}