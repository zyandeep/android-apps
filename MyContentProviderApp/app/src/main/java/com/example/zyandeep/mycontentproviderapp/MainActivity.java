package com.example.zyandeep.mycontentproviderapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText e1, e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.edit1);
        e2 = findViewById(R.id.edit2);
    }


    public void doSave(View view) {
        ContentValues values = new ContentValues();
        values.put("name", e1.getText().toString());
        values.put("job", e2.getText().toString());

        // connect to the content provider
        Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);

        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
    }


    public void doLoad(View view) {
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, null,
                null, null, "id ASC");

        StringBuilder msg = new StringBuilder();

       if (cursor != null && cursor.getCount() > 0) {
           while (cursor.moveToNext()) {

               msg.append(cursor.getInt(0) + " : " +
                       cursor.getString(cursor.getColumnIndex("name")) + " : " +
                       cursor.getString(cursor.getColumnIndex("job")) + "\n");
           }

           Toast.makeText(this, msg.toString(), Toast.LENGTH_LONG).show();
       }

       if (cursor != null) {
           cursor.close();
       }
    }
}