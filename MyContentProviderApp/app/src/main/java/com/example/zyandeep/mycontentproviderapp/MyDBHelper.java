package com.example.zyandeep.mycontentproviderapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {

    Context ctx;
    static final String DB_NAME = "employee.db";
    static final String DB_TABLE_NAME = "employee";
    static final int DB_VERSION = 1;


    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.ctx = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, job TEXT);";
        db.execSQL(query);

        Log.d("MY_DB", "database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + DB_TABLE_NAME;
        db.execSQL(query);

        onCreate(db);
    }
}
