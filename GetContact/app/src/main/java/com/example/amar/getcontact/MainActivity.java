package com.example.amar.getcontact;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static String KEY_LOG = "log";

    private static final int SMS_REQUST_CODE = 12;
    EditText op;
    EditText np;
    EditText cp;
    AlertDialog.Builder change_pin;
    AlertDialog dialog;
    String userSetPin;
    ListView list;
    List<Object> log_list = new ArrayList<Object>();
    //ArrayAdapter adapter;
    Object[] log_data = null;


    public  static  Set<String> logs = null;


    static SharedPreferences savedPrefs;
    LogAdapter logAdapter;
    RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //list = findViewById(R.id.list);
        rv = findViewById(R.id.rv_list);

        //reading shared preference file
        savedPrefs = getSharedPreferences(SetPinActivity.pinSharedPrefFile, MODE_PRIVATE);
        logs = savedPrefs.getStringSet(KEY_LOG, null);


        //Log.d("list", logs.toString());


        if (logs != null) {

            Log.d("logs", "stored data: " + logs.toString());

            //converting the shared pref set into string array
            log_data = logs.toArray();

            //populating lisview adapter
            populateAdapter(log_data);



//            rv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
//
//                    final String data = parent.getItemAtPosition(position).toString();
//
//                    AlertDialog.Builder alert_dialog = new AlertDialog.Builder(MainActivity.this);
//                    alert_dialog.setCancelable(false);
//                    alert_dialog.setTitle("Delete Log...");
//                    alert_dialog.setMessage("Are you sure you wanna delete?");
//
//                    alert_dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            logs.remove(data);
//                            adapter.remove(log_list.get(position));
//                            adapter.notifyDataSetChanged();
//
//                            saveLogs();
//                        }
//                    });
//
//                    alert_dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//
//                    AlertDialog dialog = alert_dialog.create();
//                    dialog.show();
//                }
//            });



//            Log.d("LOGS", Arrays.toString(new List[]{log_list}));
            //Toast.makeText(this, Arrays.toString(log_data), Toast.LENGTH_LONG).show();
        }

        userSetPin = savedPrefs.getString(SetPinActivity.PIN_KEY, "");

        //checking wheather pin is set or not
        if (userSetPin.isEmpty()) {

            Intent intent = new Intent(this, SetPinActivity.class);

            startActivity(intent);

            finish();
        }

        // ask for the necessary permissions
        askPermissoin();


        (findViewById(R.id.remove_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = savedPrefs.edit();
                //editor.clear();

                // clear the pin
                editor.remove(SetPinActivity.PIN_KEY);
                editor.apply();


                Snackbar snackbar = Snackbar.make(findViewById(R.id.layout), "PIN REMOVED", Snackbar.LENGTH_LONG);
                snackbar.show();
                finish();
            }
        });


        if (getIntent().getBooleanExtra(SetPinActivity.EXTRA_PIN_SET, false)) {

            Snackbar snackbar = Snackbar.make(findViewById(R.id.layout), "PIN SET SUCCESSFULLY", Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.pin:

                change_pin = new AlertDialog.Builder(MainActivity.this);
                View v = getLayoutInflater().inflate(R.layout.changepin_layout, null);
                op = v.findViewById(R.id.old_pin);
                np = v.findViewById(R.id.new_pin);
                cp = v.findViewById(R.id.con_pin);

                v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (isValidPin(np.getText().toString())) {

                            if (op.getText().toString().equals(userSetPin)) {

                                if (np.getText().toString().equals(cp.getText().toString())) {

                                    SharedPreferences.Editor editor = SetPinActivity.pinPreference.edit();
                                    editor.putString(SetPinActivity.PIN_KEY, np.getText().toString());
                                    editor.apply();
                                    Toast.makeText(MainActivity.this, "PIN Changed!", Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(MainActivity.this, "PIN didn't matched!", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                Toast.makeText(MainActivity.this, "Provide a Valid OLD PIN", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "PIN MUST CONTAIN FOUR DIGITS ONLY", Toast.LENGTH_SHORT).show();
                        }


                        if (dialog != null) {
                            dialog.dismiss();
                        }

                    }
                });

                v.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (dialog != null){ dialog.dismiss();}

                    }
                });

                change_pin.setView(v);
                dialog = change_pin.create();
                dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;

            case R.id.info:

                Intent info_activity = new Intent(this, Info_activity.class);
                startActivity(info_activity);
                break;
        }

        return true;
    }


    private void askPermissoin() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_CONTACTS},
                    SMS_REQUST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MainActivity.SMS_REQUST_CODE) {

            if (grantResults.length > 0) {

                if (grantResults[0] == PackageManager.PERMISSION_DENIED && grantResults[1] == PackageManager.PERMISSION_DENIED) {
                    showSnack("Allow Read SMS and Contact Access Permission");
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    showSnack("Allow  Read SMS permission");
                } else if (grantResults[1] == PackageManager.PERMISSION_DENIED) {

                    showSnack("Allow  Contact Access Permission");
                }
            }
        }
    }

    private void showSnack(String msg) {

        Snackbar snackbar = Snackbar.make(findViewById(R.id.layout), msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Go to Settings", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send user to the Settings Activity
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });

        snackbar.show();

    }

    private boolean isValidPin(String pin) {
        String PIN_PATTERN = "[0-9]{4}";

        return Pattern.compile(PIN_PATTERN).matcher(pin).matches();
    }


    private void populateAdapter(Object[] logArray){

        for (int i=0; i<logs.size(); i++){
            log_list.add(logArray[i]);
        }



        logAdapter = new LogAdapter(log_list, getApplicationContext());
        rv.setAdapter(logAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }



    public static void saveLogs() {

        Log.d("logs", logs.toString());

        // save the new set in pref
        SharedPreferences.Editor editor = savedPrefs.edit();
        editor.putStringSet(KEY_LOG, logs);
        editor.apply();
    }

}
