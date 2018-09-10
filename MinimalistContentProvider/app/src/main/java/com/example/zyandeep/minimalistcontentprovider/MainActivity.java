package com.example.zyandeep.minimalistcontentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    TextView tv;
    LoaderManager loaderManager;

    private static final int LOADER_SINGLE_ITEM = 10;
    private static final int LOADER_MULTIPLE_ITEMS = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.response_tv);

        this.loaderManager = getSupportLoaderManager();

        // reconnect to the loaders if already exist
        if (loaderManager.getLoader(LOADER_SINGLE_ITEM) != null) {
            getSupportLoaderManager().initLoader(LOADER_SINGLE_ITEM, null, this);
        }

        if (loaderManager.getLoader(LOADER_MULTIPLE_ITEMS) != null) {
            loaderManager.initLoader(LOADER_MULTIPLE_ITEMS, null, this);
        }
    }


    public void onClickDisplayEntries(View view) {

        switch (view.getId()) {
            case R.id.display_first_button:
                loaderManager.restartLoader(LOADER_SINGLE_ITEM, null, this);
                break;

            case R.id.display_all_button:
                loaderManager.restartLoader(LOADER_MULTIPLE_ITEMS, null, this);
                break;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        switch (id) {
            case LOADER_SINGLE_ITEM:
                return new MyAsycTaskLoader(this);

            case LOADER_MULTIPLE_ITEMS:
                return new MyAsyncLoader2(this);

            default:
                return null;
        }
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {
                tv.append(data.getString(0) + "\n");
            }
        }
        else {
            tv.setText("No data found");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }
}