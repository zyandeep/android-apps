package com.example.zyandeep.myproviderclient;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.data_tv);
        uri = Uri.parse("content://com.zyandeep.mycpapp.provider/employee");
    }


    public void loadData(View view) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, "id DESC");

        StringBuilder data = new StringBuilder();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                data.append(cursor.getInt(0) + " : "
                        + cursor.getString(1) + " : " + cursor.getString(2) + "\n");
            }

            tv.setText(data.toString());
        }


        if (cursor != null) {
            cursor.close();
        }
    }
}