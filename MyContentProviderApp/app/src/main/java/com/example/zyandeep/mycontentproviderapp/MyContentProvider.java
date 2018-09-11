package com.example.zyandeep.mycontentproviderapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.zyandeep.mycpapp.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + MyDBHelper.DB_TABLE_NAME);

    private static final int URI_ONE_ITEM_CODE = 1;
    private static final int URI_ALL_ITEM_CODE = 10;

    private static final String SINGLE_ITEM_TYPE = "vnd.android.cursor.item/com.zyandeep.mycpapp.provider.employee";
    private static final String MULTIPLE_ITEM_TYPE = "vnd.android.cursor.dir/com.zyandeep.mycpapp.provider.employee";



    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private SQLiteDatabase mDb;


    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_ONE_ITEM_CODE:
                return SINGLE_ITEM_TYPE;

            case URI_ALL_ITEM_CODE:
                return MULTIPLE_ITEM_TYPE;

            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = mDb.insert(MyDBHelper.DB_TABLE_NAME, null, values);

        return Uri.parse(CONTENT_URI + "/" + row);
    }

    @Override
    public boolean onCreate() {
        // add all CONTENT_URIs to the UriMatcher
        uriMatcher.addURI(AUTHORITY, MyDBHelper.DB_TABLE_NAME, URI_ALL_ITEM_CODE);
        uriMatcher.addURI(AUTHORITY, MyDBHelper.DB_TABLE_NAME + "/#", URI_ONE_ITEM_CODE);

        // create the database
        MyDBHelper dbHelper = new MyDBHelper(getContext());
        mDb = dbHelper.getWritableDatabase();

        return mDb != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor = null;

        switch (uriMatcher.match(uri)) {
            case URI_ONE_ITEM_CODE:
                cursor = mDb.query(MyDBHelper.DB_TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case URI_ALL_ITEM_CODE:
                cursor = mDb.query(MyDBHelper.DB_TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case UriMatcher.NO_MATCH:
                Log.d("URI_MATCHER", "No match for this uri");
        }

        return cursor;
    }
}