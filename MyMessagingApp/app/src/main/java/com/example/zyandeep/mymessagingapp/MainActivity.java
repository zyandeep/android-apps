package com.example.zyandeep.mymessagingapp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mPhNo;
    EditText mMsg;

    private static final int MY_SMS_PERMISSION = 19;
    private boolean buttonClicked = false;

    public static final String ACTION_SMS_SENT = "zyandeep.mymessagingapp.SMS_SENT";
    public static final String ACTION_SMS_DELIVERED = "zyandeep.mymessagingapp.SMS_DELIVERED";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMsg = findViewById(R.id.msg_editText);
        mPhNo = findViewById(R.id.ph_no_editText);

        // Keyboard's "Action key" is handled
        mPhNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage(v);
                }

                return true;
            }
        });


        // check for the SMS permission
        askPermission();
    }


    public void sendMessage(View view) {

        buttonClicked = true;

       /* Intent i = new Intent();
        i.setAction(Intent.ACTION_SENDTO);          // so that only text messing apps get called
        i.setData(Uri.parse("smsto:" + ph));
        i.putExtra("sms_body", msg);

        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
        else {
            Toast.makeText(this, "No SMS app installed", Toast.LENGTH_SHORT).show();
        }*/


        // check for the SMS permission
        askPermission();
    }


    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // ask for the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_SMS_PERMISSION);
        }
        else {
            if (buttonClicked) {
                // send SMS
                mySMSSender();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == MY_SMS_PERMISSION) {
            if (permissions[0].equalsIgnoreCase(Manifest.permission.SEND_SMS) && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {

                if (buttonClicked) {
                    // send SMS
                    mySMSSender();
                }
            }
            else {
                // permission denied
                Toast.makeText(this, "User denied permission", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void mySMSSender() {
        String ph = mPhNo.getText().toString();
        String msg = mMsg.getText().toString();


        if (ph.isEmpty() && msg.isEmpty()) {
            return;
        }

        // hope they are non-empty strings

        SmsManager smsManager = SmsManager.getDefault();

        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 2, new Intent(ACTION_SMS_SENT),
                PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent deliveryIntent = PendingIntent.getBroadcast(this, 3, new Intent(ACTION_SMS_DELIVERED),
                PendingIntent.FLAG_UPDATE_CURRENT);


        smsManager.sendTextMessage(ph, null, msg, sentIntent, deliveryIntent);
    }

}