package com.example.zyandeep.androidruntimepermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_CONTACT_PERMISSION_CODE = 10;
    private static final int MY_SMS_PERMISSION_CODE = 20;
    private static final int MY_LOCATION_PERMISSION_CODE = 30;
    private static final int MY_ALL_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void doRequest(View view) {

        // Asking for all the permissions

        // Check for all the permissions

        int contactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int smsPermisson = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


        if(contactsPermission != PackageManager.PERMISSION_GRANTED && smsPermisson != PackageManager.PERMISSION_GRANTED
                && locationPermission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, MY_ALL_PERMISSION_CODE);
        }
    }


    public void doRequestContact(View view) {

        if( ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    MY_CONTACT_PERMISSION_CODE);
        }
        else {
            Toast.makeText(this, "You can read the Contacts", Toast.LENGTH_SHORT).show();
        }
    }


    public void doRequestSMS(View view) {

        if( ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    MY_SMS_PERMISSION_CODE);
        }
        else {
            Toast.makeText(this, "You can send SMS", Toast.LENGTH_SHORT).show();
        }
    }


    public void doRequestLocation(View view) {

        if( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_CONTACT_PERMISSION_CODE);
        }
        else {
            Toast.makeText(this, "You can read location data", Toast.LENGTH_SHORT).show();
        }
    }




    ////// Handle the permissions request response

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_CONTACT_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Reading contacts...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Permission denied! Can't read contacts", Toast.LENGTH_SHORT).show();
                }
                break;

            case MY_SMS_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Sending SMS...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Permission denied! Can't send sms", Toast.LENGTH_SHORT).show();
                }
                break;

            case MY_LOCATION_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Reading location data...", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Permission denied! Can't reading location data", Toast.LENGTH_SHORT).show();
                }
                break;

            case MY_ALL_PERMISSION_CODE:

                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "You can access location, contacts and send SMS", Toast.LENGTH_SHORT).show();
                }
        }
    }

}