package com.example.zyandeep.wordlistloader;

import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    RecyclerView rv;
    MyRVAdapatar adapatar;

    private static final int LOADER_ID = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.recycler_view);
        adapatar = new MyRVAdapatar(this);

        rv.setAdapter(adapatar);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onResume() {
        super.onResume();

        // check if the loader exist then reconnect to it

        if (getSupportLoaderManager().getLoader(LOADER_ID) != null) {
            // reload the data;
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        }
        else {
            // add a loader
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, Contract.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // List of Words to display
        List<Word> words = new ArrayList<>();


        if (data != null && data.getCount() > 0) {
            while (data.moveToNext()) {

                Word obj = new Word();

                String w = data.getString(data.getColumnIndex(Contract.COL_WORD));
                String d = data.getString(data.getColumnIndex(Contract.COL_DESC));

                obj.setWord(w);
                obj.setDesc(d);

                // add the word to the list
                words.add(obj);
            }

            // inform the adaptar
            adapatar.addNewData(words);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }
}
