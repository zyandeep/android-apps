package com.example.zyandeep.simdetailsapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "SIM_APP";
    private static final int MY_PERMISSION_REQUEST_CODE = 20;
    private static final String PREF_FILE_NAME = "mca.project.my_pref";

    private static final String KEY_SIM = "sim_details";

    SharedPreferences sh;

    TextView tv1, tv2, tv3, tv4, tv5;

    Button myButton, saveSimButton;


    TelephonyManager telephonyManager;
    SubscriptionManager subscriptionManager;

    Set<String> simDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.gen_textView);
        tv2 = findViewById(R.id.sim1_textView);
        tv3 = findViewById(R.id.sim2_textView);
        tv4 = findViewById(R.id.sim3_textView);
        tv5 = findViewById(R.id.sim4_textView);

        myButton = findViewById(R.id.button);
        saveSimButton = findViewById(R.id.save_button);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        subscriptionManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);

        sh = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);

        askPermission();

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED ) {

            // disable the buttons
            myButton.setEnabled(false);
            saveSimButton.setEnabled(false);

            // ask for the permission
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE && grantResults.length > 0) {

            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                showSnackBar("Grant READ_PHONE_STATE permission");
            }
            else {
                // enable the button
                myButton.setEnabled(true);
                saveSimButton.setEnabled(true);
            }
        }
    }

    private void showSnackBar(String msg) {
        Snackbar.make(findViewById(R.id.my_layout), msg, Snackbar.LENGTH_LONG).show();
    }


    @SuppressLint("MissingPermission")
    private void loadData() {

        /*
        * Get Some general info about the phone
        * */

        // No of SIM slot
        String simSlots = "NO OF SIM SLOTS = ";
        simSlots += subscriptionManager.getActiveSubscriptionInfoCountMax();


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
        List<SubscriptionInfo> subscriptionInfos =  subscriptionManager.getActiveSubscriptionInfoList();

        String insertedSim = "NO OF INSERTED + ACTIVATED SIMS = ";

        if (subscriptionInfos != null) {
           insertedSim += subscriptionInfos.size();

           simDetails = new HashSet<>(subscriptionInfos.size());

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
                else {
                    // SIM 2 details
                    tv3.setText(String.format("NETWORK OPERATOR = %s\nNETWORK IDENTIFIER = %s\nSIM SERIAL NO = %s\nCOUNTRY ISO CODE = %s\nPHONE NO = %s\nSIM SLOT = %d\nSUB ID = %d", carrierName, displayName, simSerialNo,
                            country, number, simSlotIndex, id));
                }

                // store SIM details in the set
                simDetails.add(simSerialNo + ":" + id);
            }
        }

        tv1.setText(String.format("%s\n%s\n%s", phoneType, simSlots, insertedSim));
    }

    public void saveSimDetails(View view) {
        if (simDetails != null && simDetails.size() > 0) {
            SharedPreferences.Editor editor = sh.edit();

            //Log.d(TAG, simDetails.toString());

            editor.putStringSet(KEY_SIM, simDetails);
            editor.apply();

            showSnackBar("SIM details saved");
        }
    }
}