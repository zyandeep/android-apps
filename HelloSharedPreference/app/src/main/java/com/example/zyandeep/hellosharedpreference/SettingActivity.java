package com.example.zyandeep.hellosharedpreference;

import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ToggleButton tb;
    Spinner spinner;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sh = getSharedPreferences(MainActivity.MY_PREF_FILE, MODE_PRIVATE);

        tb = findViewById(R.id.toggle_button);
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.autoSave = true;

                    showToast("AutoSave On");
                }
                else {
                    MainActivity.autoSave = false;

                    showToast("AutoSave Off");
                }
            }
        });

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.color_list, android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
    }

    public void saveSetting(View view) {
        if (MainActivity.autoSave) {
            showToast("AutoSave is already On");
        }
        else {
            // Save the user preference
            MainActivity.savePreference();
            showToast("User preference saved");
        }
    }

    public void resetSetting(View view) {
        // Clear preferences
        SharedPreferences.Editor ed = this.sh.edit();
        ed.clear();
        ed.apply();

        Toast.makeText(this, "Preference Reset", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getItemAtPosition(position).toString()) {
            case "Blue":
                MainActivity.mColor = ContextCompat.getColor(this, R.color.blue_background);
                break;

            case "Green":
                MainActivity.mColor = ContextCompat.getColor(this, R.color.green_background);
                break;

            case "Red":
                MainActivity.mColor = ContextCompat.getColor(this, R.color.red_background);
                break;

            case "Black":
                MainActivity.mColor = ContextCompat.getColor(this, android.R.color.black);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }



    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}