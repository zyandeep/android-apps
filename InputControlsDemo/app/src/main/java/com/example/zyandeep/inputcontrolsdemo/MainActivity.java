package com.example.zyandeep.inputcontrolsdemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    ToggleButton tb;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tb = findViewById(R.id.toggleButton);
        tb.setOnCheckedChangeListener(this);

        aSwitch = (Switch)findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("Switch", "" + b);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.d("ToggleButton", "" + b);
        Log.d("ToggleButton", "" + tb.isChecked());
    }

    public void doCheckbox(View view) {

        boolean b = ((CheckBox)view).isChecked();

        switch (view.getId()) {
            case R.id.checkBox1:
                Log.d("Checkbox 1", "" + b);
                break;

            case R.id.checkBox2:
                Log.d("Checkbox 2", "" + b);
                break;
        }
    }

    public void showAlertDialog(View view) {
        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setTitle("Quit App");
        d.setMessage("Are you sure?");
        d.setCancelable(false);

        d.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        });

        d.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Hide the dialog
                //dialogInterface.dismiss();
                dialogInterface.cancel();
            }
        });

        d.setNeutralButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        d.show();
    }

    public void showProgressDialog(View view) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);

        pd.setTitle("Downloading Image");
        pd.setMessage("Downloading...");
        //pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        pd.show();
    }
}
