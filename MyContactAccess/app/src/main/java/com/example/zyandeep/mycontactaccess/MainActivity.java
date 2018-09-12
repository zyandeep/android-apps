package com.example.zyandeep.mycontactaccess;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    RecyclerView rv;
    MyRVadptar adptar;
    EditText ed;

    private static final int LOADER_ID = 11;
    private String searchFor = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed = findViewById(R.id.editText);
        rv = findViewById(R.id.recyclerView);

        adptar = new MyRVadptar(this);
        rv.setAdapter(adptar);
        rv.setLayoutManager(new LinearLayoutManager(this));


        // configuration has changed
        if (savedInstanceState != null) {
            this.searchFor = savedInstanceState.getString("search_key");

            if (getSupportLoaderManager().getLoader(LOADER_ID) != null) {
                getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("search_key", this.searchFor);
    }


    public void loadContacts(View view) {
        this.searchFor = "";

        // restart the loader
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    public void serachContact(View view) {
        this.searchFor = ed.getText().toString();

        // restart the loader
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Uri queryUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] cols = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.ACCOUNT_TYPE_AND_DATA_SET
        };

        String selection = null;
        String[] arg = null;

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " ASC";

        if (!searchFor.isEmpty()) {
            selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " LIKE ?";
            arg = new String[]{"%" + searchFor + "%"};
        }

        return new CursorLoader(this, queryUri, cols, selection, arg, sortOrder);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        List<MyContactInfo> contacts = new ArrayList<>();


        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {

                String acType = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.ACCOUNT_TYPE_AND_DATA_SET));

                if (acType.equalsIgnoreCase("com.google")) {

                    MyContactInfo obj = new MyContactInfo();
                    obj.setName(data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY)));
                    obj.setPhoneNo( data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                    // add the object into the list
                    contacts.add(obj);
                }

            }
        }

        // give data to the adaptar
        adptar.addContacts(contacts);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) { }
}