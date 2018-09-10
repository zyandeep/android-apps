package com.example.zyandeep.mycontactaccess;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    MyRVadptar adptar;
    EditText ed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed = findViewById(R.id.editText);
        rv = findViewById(R.id.recyclerView);

        adptar = new MyRVadptar(this);
        rv.setAdapter(adptar);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    public void loadContacts(View view) {

        // load the contacts via AsyncTask
        new MyAsyncTask(MainActivity.this).execute();
    }

    public void serachContact(View view) {
        String query = ed.getText().toString();

        // execute an AsyncTask to load the specific contact
        new MyAnotherTask(MainActivity.this).execute(query);
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///// Connect the Contacts ContentProvider and search for the contacts ///////////////////////////
    private List<MyContactInfo> searchContactsDB(String query) {

        List<MyContactInfo> myList = new ArrayList<>();
        Cursor cursor = null;

        String[] columns = new String[]{ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                ContactsContract.Contacts.HAS_PHONE_NUMBER};

        String selection = null;
        String[] args = null;

        if (!query.isEmpty()) {
            selection = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?";
            args = new String[]{"%" + query + "%"};
        }

        String order = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

        cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, columns,
                selection, args, order);

        if (cursor != null) {
            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    // get the contact
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    int hasPhNo = Integer.parseInt(cursor.getString
                            (cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                    if (hasPhNo > 0) {                  // hasPhNo == 1
                        // get the phone number
                       selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?";
                       args = new String[]{id};


                        Cursor c2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, selection, args, null);

                        // A contact might have many phone numbers
                        while (c2.moveToNext()) {

                            String ph = c2.getString(c2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            String acType = c2.getString(c2.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.ACCOUNT_TYPE_AND_DATA_SET));

                            // Only add phone numbers of account type com.google
                            // not whatsapp, duo, fb etc

                            if (acType.equalsIgnoreCase("com.google")) {
                                MyContactInfo contactInfo = new MyContactInfo();
                                contactInfo.setName(name);
                                contactInfo.setPhoneNo(ph);

                                // add the object in the list
                                myList.add(contactInfo);
                            }
                        }

                        c2.close();
                    }
                }
            }
            else {
                // No contacts found
                Toast.makeText(MainActivity.this, "No contacts found on the device", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        }

        return myList;
    }



    // Load only specific contacts in the background
    //////////////////////////////////////////////////////////////////////////////
    private class MyAnotherTask extends AsyncTask<String, Void, List<MyContactInfo>> {

        Context context;
        ProgressDialog pd;

        public MyAnotherTask(Context context) {
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(context);
            pd.setTitle("Contacts");
            pd.setMessage("Loading... Please wait");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected List<MyContactInfo> doInBackground(String... strings) {

            // load contacts in the background
            return searchContactsDB(strings[0]);
        }

        @Override
        protected void onPostExecute(List<MyContactInfo> myContactInfos) {
            super.onPostExecute(myContactInfos);

            pd.cancel();

            // update the UI
            // give the data to the recyclerview adaptar
            adptar.addContacts(myContactInfos);
        }
    }


    // Load all the contacts in the background
    //////////////////////////////////////////////////////////////////////////////
    private class MyAsyncTask extends AsyncTask<Void, Void, List<MyContactInfo>> {

        Context context;
        ProgressDialog pd;

        public MyAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Show an indeterminate progress dialog

            pd = new ProgressDialog(context);
            pd.setTitle("Contacts");
            pd.setMessage("Loading... Please wait");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected List<MyContactInfo> doInBackground(Void... voids) {

            // load contacts in the background
            return searchContactsDB("");
        }

        @Override
        protected void onPostExecute(List<MyContactInfo> myContactInfos) {
            super.onPostExecute(myContactInfos);

            pd.dismiss();

            // update the UI
            // give the data to the recyclerview adaptar
            adptar.addContacts(myContactInfos);
        }
    }
}