package com.example.zyandeep.appwithsettings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_SWITCH = "example_switch";
    public static final String KEY_EDIT_TEXT = "edit_text_pref";
    public static final String KEY_LIST = "list_pref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // save the default values
        PreferenceManager.setDefaultValues(this, R.xml.preference, false);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);

        boolean switchPref = sh.getBoolean(KEY_SWITCH, false);
        String editTextPref = sh.getString(KEY_EDIT_TEXT, "");
        String listPref = sh.getString(KEY_LIST, "");


        Toast.makeText(this, switchPref + "", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, editTextPref, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, listPref, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting) {

            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }

        return true;
    }
}