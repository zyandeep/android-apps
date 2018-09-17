package com.example.zyandeep.smsreader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int SMS_REQUST_CODE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ask for the necessary permissions
        askPermissoin();
    }

    private void askPermissoin() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_REQUST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MainActivity.SMS_REQUST_CODE) {

            if (permissions[0].equalsIgnoreCase(Manifest.permission.RECEIVE_SMS) && grantResults[0]
                    == PackageManager.PERMISSION_DENIED) {

                Toast.makeText(this, "No permission for reading SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}