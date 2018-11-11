package com.example.zyandeep.simdetailsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREF_FILE_NAME = "mca.project.my_pref";
    private static final String KEY_SERVICE_STATUS = "status";
    private static final String KEY_CONTACTS = "contacts";
    private static final String DELIMITER = "/";
    private static final String SPINNER_FIRST_ITEM = "None";
    private static final int MAX_EMERGENCY_CONTACTS = 4;
    private static final int MY_CONTACT_REQUEST_CODE = 10;

    Switch aSwitch;
    Button searchButton;
    TextView contactsTextView;
    Spinner spinner;

    SharedPreferences sh;
    SharedPreferences.Editor editor;
    boolean status;
    Set<String> constactSet;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sh = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        status = sh.getBoolean(KEY_SERVICE_STATUS, true);
        constactSet = sh.getStringSet(KEY_CONTACTS, null);

        editor = sh.edit();

        contactsTextView = findViewById(R.id.contact_textView);

        aSwitch = findViewById(R.id.switch1);
        if (status) {
            aSwitch.setChecked(true);               // enable the service
        }
        else {
            aSwitch.setChecked(false);              // disable the service
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Snackbar.make(findViewById(R.id.my_constLayout), "Service enabled", Snackbar.LENGTH_LONG).show();
                }
                else {
                    Snackbar.make(findViewById(R.id.my_constLayout), "Service disabled", Snackbar.LENGTH_LONG).show();
                }

                editor.putBoolean(KEY_SERVICE_STATUS, isChecked);
                editor.apply();
            }
        });


        searchButton = findViewById(R.id.search_button);
        if (constactSet == null) {
            // no contacts added
            contactsTextView.setText(R.string.contacts_msg);

            // create a new set
            constactSet = new HashSet<>(5);
        }
        else {
            displayContactList();
        }


        //
        spinner = findViewById(R.id.spinner);
        populateAdapter();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data = parent.getItemAtPosition(position).toString();

                Log.d(MainActivity.TAG, "item selected : " + data);


                if (!data.equals(SPINNER_FIRST_ITEM)) {
                    Log.d(MainActivity.TAG, "item removed " + constactSet.remove(data));

                    // save it on the pref
                    editor.putStringSet(KEY_CONTACTS, constactSet);
                    editor.apply();


                    // update textView
                    displayContactList();

                    // update spinner
                    populateAdapter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // display info about emergency contacts
        Snackbar.make(findViewById(R.id.my_constLayout), "You can add maximum of 4 emergency contacts", Snackbar.LENGTH_LONG).show();

    }


    // create or update SpinnerAdapter
    private void populateAdapter() {
        List<String> stringList = new ArrayList<>(constactSet);
        stringList.add(0, SPINNER_FIRST_ITEM);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }


    public void addNewContacts(View view) {
        // pick a contact from the System's Contact App
        // Start an activity for the user to pick a phone number from contacts
        Intent i = new Intent(Intent.ACTION_PICK);

        //i.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);

        // or set MIME TYPE
        i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);


        if (i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, MY_CONTACT_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_CONTACT_REQUEST_CODE && resultCode == RESULT_OK) {

            //  Get the contact URI for the selected contact and query the content provider for the phone number
            Uri uri = data.getData();

            String[] cols = new String[]{
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };

            try (Cursor cursor = getContentResolver().query(uri, cols, null, null, null)) {

                if (cursor != null && cursor.moveToNext()) {
                    String name = cursor.getString(0);
                    String ph = cursor.getString(1);

                    String contactInfo = name + DELIMITER + ph;

                    // check if we already have that emergency contact

                    if (constactSet.contains(contactInfo)) {

                        Snackbar.make(findViewById(R.id.my_constLayout), "Already included in the list", Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        // add the new contact in the list
                        constactSet.add(contactInfo);


                        // save it on the pref
                        editor.putStringSet(KEY_CONTACTS, constactSet);
                        editor.apply();

                        // update textView
                        displayContactList();

                        // update spinner
                        populateAdapter();
                    }
                }
            } catch (Exception e) {
                Log.e(MainActivity.TAG, e.getMessage());
            }
        }
    }


    private void displayContactList() {

        // update the textView

        if (constactSet.size() == 0) {
            contactsTextView.setText(R.string.contacts_msg);
        }
        else {

            contactsTextView.setText("");

            for (String contact : constactSet) {
                contactsTextView.append(contact.replace(DELIMITER, " --> ") + "\n");
            }
        }


        if (constactSet.size() < MAX_EMERGENCY_CONTACTS) {
            searchButton.setEnabled(true);
        } else {
            searchButton.setEnabled(false);
        }
    }
}