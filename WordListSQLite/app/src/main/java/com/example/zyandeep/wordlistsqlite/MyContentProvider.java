package com.example.zyandeep.wordlistsqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


public class MyContentProvider extends ContentProvider {

    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MyDBHelper dbHelper;

    private static final int URI_ALL_ITEMS_CODE = 10;
    private static final int URI_ONE_ITEM_CODE = 20;
    private static final int URI_COUNT_CODE = 30;



    public MyContentProvider() { }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
       return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MyDBHelper(getContext());

        uriMatcher.addURI(Contract.AUTHORITY, Contract.WordEntries.CONTENT_PATH, URI_ALL_ITEMS_CODE);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.WordEntries.CONTENT_PATH + "/#", URI_ONE_ITEM_CODE);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.WordEntries.CONTENT_PATH + "/count", URI_COUNT_CODE);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;

        switch (uriMatcher.match(uri)) {
            case URI_ALL_ITEMS_CODE:
                cursor = dbHelper.query(-100);
                break;

            case URI_ONE_ITEM_CODE:
                cursor = dbHelper.query(Integer.parseInt(uri.getLastPathSegment()));
                break;

            case URI_COUNT_CODE:
                cursor = dbHelper.count();
                break;

            default:
                Log.d("MY_PROVIDER", "INVALID URI - URI NOT RECOGNIZED: " + uri);
        }

        return cursor;
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}