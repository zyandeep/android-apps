package com.example.zyandeep.minimalistcontentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.response_tv);
    }

    public void onClickDisplayEntries(View view) {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = null;

        switch (view.getId()) {
            case R.id.display_first_button:

                Uri uri = Uri.parse(MyContract.CONTENT_URI.toString() + "/1");
                cursor = contentResolver.query(uri, null, null,
                        null, null);

               break;

            case R.id.display_all_button:
                cursor = contentResolver.query(MyContract.CONTENT_URI, null, null,
                        null, null);
                break;
        }

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                tv.append(cursor.getString(0) + "\n");
            }
        }
        else {
            tv.setText("No data found");
        }
    }
}