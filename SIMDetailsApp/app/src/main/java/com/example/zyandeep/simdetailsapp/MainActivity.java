package com.example.zyandeep.simdetailsapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "SIM_APP";
    private static final int MY_PERMISSION_REQUEST_CODE = 20;
    private static final String PREF_FILE_NAME = "mca.project.my_pref";
    private static final String KEY_SIM = "sim_details";
    private static final String KEY_IMIE = "imie";
    private static final String KEY_NO_DATA = "no_data";


    SharedPreferences sh;

    TextView tv1, tv2, tv3, tv4, tv5;

    Button myButton, saveSimButton;

    TelephonyManager telephonyManager;
    SubscriptionManager subscriptionManager;

    Set<String> mSimDetails;
    String mIMIENo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // general info about the phone
        tv1 = findViewById(R.id.gen_textView);

        // info about the sim details
        tv2 = findViewById(R.id.sim1_textView);
        tv3 = findViewById(R.id.sim2_textView);
        tv4 = findViewById(R.id.sim3_textView);
        tv5 = findViewById(R.id.sim4_textView);


        myButton = findViewById(R.id.button);
        saveSimButton = findViewById(R.id.save_button);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        subscriptionManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        sh = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });


        askPermission();


        if (savedInstanceState != null) {
            // restore saved data

            // KEY_NO_DATA = true, when there's no data in the textViews

            if (!savedInstanceState.getBoolean(KEY_NO_DATA)) {
                loadData();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.settings_menu_item) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }

        return true;
    }


    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            // disable the buttons
            myButton.setEnabled(false);
            saveSimButton.setEnabled(false);

            // ask for the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS},
                    MY_PERMISSION_REQUEST_CODE);
        }
        else {
            // enable the buttons
            myButton.setEnabled(true);
            saveSimButton.setEnabled(true);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE && grantResults.length > 0) {

            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                showSnackBar("Grant READ_PHONE_STATE permission");
            }
            else if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
                showSnackBar("Grant ACCESS_LOCATION permission");
            }
            else if (grantResults[2] == PackageManager.PERMISSION_DENIED) {
                showSnackBar("Grant SEND_SMS permission");
            }
            else {
                // enable the buttons
                myButton.setEnabled(true);
                saveSimButton.setEnabled(true);
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


    @SuppressLint("MissingPermission")
    private void loadData() {
        //Get some general info about the phone

        // No of SIM slots
        String simSlots = "NO OF SIM SLOTS = ";
        simSlots += subscriptionManager.getActiveSubscriptionInfoCountMax();

        // The primary imie no; SIM 1
        mIMIENo = telephonyManager.getDeviceId();


        // GSM OR CDMA PHONE??
        String phoneType = "PHONE TYPE = ";

        switch (telephonyManager.getPhoneType()) {
            case TelephonyManager.PHONE_TYPE_GSM:
                phoneType += "GSM";
                break;

            case TelephonyManager.PHONE_TYPE_CDMA:
                phoneType += "CDMA";
        }


        // No of inserted + activated sims
        List<SubscriptionInfo> subscriptionInfos = subscriptionManager.getActiveSubscriptionInfoList();

        String insertedSim = "NO OF INSERTED + ACTIVATED SIMS = 0";

        if (subscriptionInfos != null) {

            insertedSim = insertedSim.replace("0", subscriptionInfos.size() + "");

            mSimDetails = new HashSet<>(subscriptionInfos.size());

            // Details about all the inserted + activated sims
            for (int i = 0; i < subscriptionInfos.size(); i++) {

                SubscriptionInfo s = subscriptionInfos.get(i);

                String carrierName = s.getCarrierName().toString();
                String displayName = s.getDisplayName().toString();
                String simSerialNo = s.getIccId();
                String country = s.getCountryIso();
                String number = s.getNumber();
                int simSlotIndex = s.getSimSlotIndex() + 1;
                int id = s.getSubscriptionId();


                // Only considering dual sim phones
                // to show SIM details
                if (i == 0) {
                    // SIM 1 details
                    tv2.setText(String.format("NETWORK OPERATOR = %s\nNETWORK IDENTIFIER = %s\nSIM SERIAL NO = %s\nCOUNTRY ISO CODE = %s\nPHONE NO = %s\nSIM SLOT = %d\nSUB ID = %d", carrierName, displayName, simSerialNo,
                            country, number, simSlotIndex, id));
                }
                else if (i == 1) {
                    // SIM 2 details
                    tv3.setText(String.format("NETWORK OPERATOR = %s\nNETWORK IDENTIFIER = %s\nSIM SERIAL NO = %s\nCOUNTRY ISO CODE = %s\nPHONE NO = %s\nSIM SLOT = %d\nSUB ID = %d", carrierName, displayName, simSerialNo,
                            country, number, simSlotIndex, id));
                }

                // store SIM details in the set
                mSimDetails.add(simSerialNo + ":" + id);
            }
        }

        tv1.setText(String.format("%s\n%s\n%s\nIMIE NO = %s\n", phoneType, simSlots, insertedSim, mIMIENo));
    }


    public void saveSimDetails(View view) {

        if (mSimDetails != null && !mSimDetails.isEmpty()) {
            SharedPreferences.Editor editor = sh.edit();
            editor.putStringSet(KEY_SIM, mSimDetails);
            editor.putString(KEY_IMIE, mIMIENo);
            editor.apply();

            Snackbar.make(findViewById(R.id.my_layout), "SIM details saved", Snackbar.LENGTH_LONG).show();
        }
        else {
            Snackbar.make(findViewById(R.id.my_layout), "No SIM details to save", Snackbar.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_NO_DATA, tv1.getText().toString().isEmpty());
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //notifyUser();
    }



   /* private void notifyUser() {
        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingContentIntent = PendingIntent.getActivity(this, 92,
                contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // notify the user
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_add_alert)
                .setContentTitle("You're closing the app!")
                .setContentText("Put the app in the background")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingContentIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.alert_bitmap))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.noti_text)))
                .addAction(R.drawable.ic_add_alert, "open app", pendingContentIntent);


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(22, builder.build());
    }*/
}