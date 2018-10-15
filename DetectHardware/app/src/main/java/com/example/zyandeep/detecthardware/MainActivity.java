package com.example.zyandeep.detecthardware;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
    private static final int MY_REQUEST_CODE = 98;

    private TextView contactTextView;

    // user provided phone number
    public static String mPhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactTextView = findViewById(R.id.contact_tv);

        // start the service
        startService(new Intent(this, PowerButtonService.class));

        // Do we have the required permissions
        // ACCESS_LOCATION and READ_SMS
        askPermission();

        (findViewById(R.id.my_fab)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Make sure mobile data is ON and so is GPS

                //checkGPS();

                //checkDataConnection();

                Intent i = new Intent(MainActivity.this, PowerButtonService.class);
                i.putExtra(MyReceiver.SOS_TRIGGERED, true);

                // Trigger the SOS Service
                startService(i);

                Snackbar.make(findViewById(R.id.my_layout), "SOS Service Triggered", Snackbar.LENGTH_LONG).show();
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


    public void chooseContact(View view) {
        // Start an activity for the user to pick a phone number from contacts
        Intent i = new Intent(Intent.ACTION_PICK);

        //i.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);

        // or set MIME TYPE
        i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);


        if (i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, MY_REQUEST_CODE);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK) {

            //  Get the contact URI for the selected contact and query the content provider for the phone number

            Uri uri = data.getData();

            //Log.d(TAG, "phone : " + uri.toString());

            String[] cols = new String[]{
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };

            try (Cursor cursor = getContentResolver().query(uri, cols, null, null, null)) {

                if (cursor != null && cursor.moveToNext()) {
                    String name = cursor.getString(0);
                    mPhoneNumber = cursor.getString(1);

                    contactTextView.setText(String.format("%s : %s", name, mPhoneNumber));
                }
            }
            catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}