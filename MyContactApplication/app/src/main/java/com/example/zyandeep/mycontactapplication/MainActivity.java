package com.example.zyandeep.mycontactapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 20;
    private static final int VIEW_CONTACT_REQUEST_CODE = 210;
    private static final String TAG = "MY_APP";

    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.data_textView);
    }


    public void selectContact(View view) {
        // Start an activity for the user to pick a phone number from contacts
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);

        // or set MIME TYPE

        if (i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, MY_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK) {

            //  Get the contact URI for the selected contact and query the content provider for the phone number

            Uri uri = data.getData();

            Log.d(TAG, "phone : " + uri.toString());



            String[] cols = new String[]{
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };

            try (Cursor cursor = getContentResolver().query(uri, cols, null, null, null)) {

                if (cursor != null && cursor.moveToNext()) {
                    String name = cursor.getString(0);
                    String number = cursor.getString(1);

                    tv.append(String.format("%s : %s\n", name, number));
                }
            }
            catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

        else if (requestCode == VIEW_CONTACT_REQUEST_CODE && resultCode == RESULT_OK) {

            Uri contactUri = data.getData();

            Log.d(TAG, "people : " + contactUri.toString());


            // view the details of a particular contact
            Intent i1 = new Intent(Intent.ACTION_VIEW, contactUri);

            startActivity(i1);
        }
    }



    public void viewContact(View view) {
        Intent i1 = new Intent(Intent.ACTION_PICK);
        //i1.setType(ContactsContract.Contacts.CONTENT_TYPE);

        i1.setData(ContactsContract.Contacts.CONTENT_URI);

        // display a list of all the contacts

        if (i1.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i1, VIEW_CONTACT_REQUEST_CODE);
        }
    }
}