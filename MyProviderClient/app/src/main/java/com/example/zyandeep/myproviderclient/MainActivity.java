package com.example.zyandeep.myproviderclient;

import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    TextView tv;

    // content uri
    private static final Uri queryUri = Uri.parse("content://com.zyandeep.mycpapp.provider/employee");
    private static final int LOADER_ID = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.data_tv);

        // connect to the loader
        if (getSupportLoaderManager().getLoader(LOADER_ID) != null) {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }


    public void loadData(View view) {
        // restart the loader to load new data
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, queryUri, null, null, null, "id DESC");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        StringBuilder res = new StringBuilder();

        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                res.append(data.getInt(0) + " : "
                        + data.getString(1) + " : " + data.getString(2) + "\n");
            }

            tv.setText(res.toString());
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // discard old data
    }
}