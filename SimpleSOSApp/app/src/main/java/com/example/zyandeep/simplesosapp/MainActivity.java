package com.example.zyandeep.simplesosapp;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "SOS_APP";

    private static final int MY_PERMISSION_REQUEST_CODE = 122;
    private static final int MY_LOCATION_REQUEST_CODE = 19;

    private static final String PREF_FILE_NAME = "com.example.zyandeep.contactpickerapp.my_pref";
    private static final String KEY_CONTACTS_NO = "no_of_contacts";

    private int emgContacts = 0;

    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sh = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);

        // Do we have the required permissions
        // ACCESS_LOCATION and READ_SMS
        askPermission();

        // start the service
        startService(new Intent(this, PowerButtonService.class));

        (findViewById(R.id.my_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Make sure the GPS is ON
                checkGPS();
            }
        });

        View backgroundImage = findViewById(R.id.my_layout);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(50);


    }


    @Override
    protected void onStart() {
        super.onStart();

        emgContacts = sh.getInt(KEY_CONTACTS_NO, 0);

        if (emgContacts == 0) {
            Snackbar.make(findViewById(R.id.my_layout), "Add emergency contacts first", Snackbar.LENGTH_LONG).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.emg_ph_menu:
                Intent i1 = new Intent(this, ContactsActivity.class);
                startActivity(i1);
                break;

            case R.id.msg_menu:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;

            case R.id.about_menu:
                Intent i2 = new Intent(this, InfoActivity.class);
                startActivity(i2);
        }

        return true;
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
                        ((ResolvableApiException) e).startResolutionForResult(MainActivity.this, MY_LOCATION_REQUEST_CODE);

                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        })

        .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                triggerSOSService();
            }
        });
    }


    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // ask for the permission
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.SEND_SMS }, MY_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE && grantResults.length > 0) {

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

        snackbar.setAction("Go to Settings", new View.OnClickListener() {
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


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                triggerSOSService();
            }
            else if (resultCode == RESULT_CANCELED) {
                triggerSOSService();
            }
        }
    }


    private void triggerSOSService() {

        Intent i = new Intent(MainActivity.this, PowerButtonService.class);
        i.putExtra(MyReceiver.SOS_TRIGGERED, true);

        Log.d(TAG, emgContacts + "");


        if (emgContacts >= 2) {
            startService(i);
            Snackbar.make(findViewById(R.id.my_layout), "SOS service triggered", Snackbar.LENGTH_LONG).show();
        }
        else {
            startService(i);
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

        // start the service again
        Log.d(TAG, MainActivity.class.getSimpleName() + " destroyed");
    }
}