package com.example.zyandeep.materialdesigndemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doSomething(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setCancelable(false);
        dialog.setTitle("Hello FAB");
        dialog.setMessage("This FAB is an android material component");
        dialog.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }

    public void buttonTapped(View view) {
        switch (view.getId()) {
            case R.id.win_button:
                Toast.makeText(this, "Hello, Windows!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.mac_button:
                Toast.makeText(this, "Hello, Unix!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
