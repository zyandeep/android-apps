package com.example.zyandeep.mygeofenceapp;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;


public class GeofenceService extends IntentService {

    private static final int NOTIFICATION_ID = 102;

    public GeofenceService() {

        super("GeofenceService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        //Log.d(MainActivity.TAG, "IntentService");

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // Get the transition type
        int transitionType = geofencingEvent.getGeofenceTransition();

        // Get the geofences that were triggered
        List<Geofence> geofences =  geofencingEvent.getTriggeringGeofences();

        Log.d(MainActivity.TAG, "No of geofences triggred: " + geofences.size());


        String geoFenceID = "";

        // hopefully we get 1 ID
        for (Geofence g : geofences) {
            geoFenceID = g.getRequestId();
        }


        if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
            sendNotification("You've entered " + geoFenceID);
        }
        else if (transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
            sendNotification("You've left " + geoFenceID);
        }
    }


    private void sendNotification(String s) {

        // Get a reference to system's notification manager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 91,
                contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.noti_icon)
                        .setContentTitle("Geofence Activated")
                        .setContentText(s)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}