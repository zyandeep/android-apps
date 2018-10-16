package com.example.zyandeep.appwithsettings;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener{

    SharedPreferences sh;
    Preference editText;
    Preference list;
    Preference checkbox;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);

        sh = PreferenceManager.getDefaultSharedPreferences(getContext());

        editText = findPreference("edit_text_pref");
        editText.setSummary("Your name is " + sh.getString("edit_text_pref", ""));
        editText.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                editText.setSummary("Your name is " + newValue.toString());

                return true;
            }
        });


        list = findPreference("list_pref");
        list.setSummary("You like to live in " + sh.getString("list_pref", ""));
        list.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                list.setSummary("You like to live in " + newValue.toString());
                return true;
            }
        });



        checkbox = findPreference("check_box_pref");
        checkbox.setOnPreferenceChangeListener(SettingsFragment.this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        if ( (boolean)newValue ) {
            // Clear all the settings
            SharedPreferences.Editor edit = sh.edit();
            edit.clear();
            edit.apply();
        }

        return true;
    }
}