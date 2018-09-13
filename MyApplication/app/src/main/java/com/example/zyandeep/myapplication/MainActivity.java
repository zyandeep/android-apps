package com.example.zyandeep.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText ed;

    private static final int MY_REQUEST_CODE = 19;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        ed = findViewById(R.id.my_edit);
    }


    public void doSomthing(View view) {
        String msg = ed.getText().toString();

        askPermission();
    }


    private void askPermission() {

        // check if we have the permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_CODE);
        }
        else {
            // if only we have the permission, show the toast
            Toast.makeText(this, ed.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 &&   permissions[0].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)
                    &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // user has granted the permission
                Toast.makeText(this, ed.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}