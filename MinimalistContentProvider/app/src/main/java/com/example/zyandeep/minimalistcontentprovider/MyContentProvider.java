package com.example.zyandeep.minimalistcontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

    private String[] words;
    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public MyContentProvider() { }


    @Override
    public boolean onCreate() {
        // create the data source
        words = getContext().getResources().getStringArray(R.array.words);


        // add uris
        uriMatcher.addURI(MyContract.AUTHORITY, MyContract.CONTENT_PATH, 0);
        uriMatcher.addURI(MyContract.AUTHORITY, MyContract.CONTENT_PATH + "/#", 1);

        return true;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case 0:
                return MyContract.MULTIPLE_RECORDS_MIME_TYPE;
            case 1:
                return MyContract.SINGLE_RECORD_MIME_TYPE;

            default:
                return null;
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        MatrixCursor cursor = new MatrixCursor(new String[]{MyContract.CONTENT_PATH});


        switch (uriMatcher.match(uri)) {
            case 0:

                // get multiple records
                for (String word : words) {
                    cursor.addRow(new Object[]{word});
                }
                break;

            case 1:
                // get a single record
                cursor.addRow(new Object[]{words[0]});
                break;

            default:
                Log.d("MY_PROVIDER", "No matching Uri");
        }

        return cursor;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}