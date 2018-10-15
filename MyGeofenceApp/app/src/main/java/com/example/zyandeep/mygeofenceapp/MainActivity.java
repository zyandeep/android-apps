package com.example.zyandeep.mygeofenceapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_REQUEST_CODE = 10;
    private static final int RADIUS = 130;                          // in meters

    Button b1, b2;

    public static final String TAG = "MY_GEOFENCE";


    // Google geofence API
    private GeofencingClient mGeofencingClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.start_button);
        b2 = findViewById(R.id.stop_button);

        // Get a reference to Google Geofence API
        mGeofencingClient = LocationServices.getGeofencingClient(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        // prompt the user to turn on location service if not available

        askPermission();
    }


    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MainActivity.LOCATION_REQUEST_CODE) {

            if (permissions[0].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0]
                    == PackageManager.PERMISSION_DENIED) {

                Toast.makeText(this, "Enable the permission for LOCATION ACCESS", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @SuppressLint("MissingPermission")
    public void startGeofence(View view) {

        // JEC : Location[fused 26.746288,94.248856 acc=78 et=+5d10h35m13s814ms]
        // HOME :  Location[fused 26.764288,94.204667 acc=1700 et=+1d22h12m50s821ms]

        List<Geofence> geofenceList = new ArrayList<>();

        // add two geofences to monitor
        geofenceList.add( new Geofence.Builder()
                .setRequestId("JEC")
                .setCircularRegion(26.746288, 94.248856, RADIUS)

                // until the user turn it of manually

                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        geofenceList.add(new Geofence.Builder()
                .setRequestId("Malow Aali")
                .setCircularRegion(26.764288, 94.204667, RADIUS)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());


        // Geofence request object
        GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
                .addGeofences(geofenceList)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .build();


        // do we have the location permission??
        mGeofencingClient.addGeofences(geofencingRequest, getPendingIntent())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Geofence created", Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Couldn't create the geofence
                    }
                });
    }


    public void stopGeofence(View view) {

        mGeofencingClient.removeGeofences(getPendingIntent())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Geofence removed", Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Couldn't remove the geofence
                    }
                });
    }


    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, GeofenceService.class);
        return PendingIntent.getService(this, 90, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
