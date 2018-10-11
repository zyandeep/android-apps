package com.example.zyandeep.sqlitedemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "demo_db.db";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE_NAME = "student";

    SQLiteDatabase db;
    Context ctx;

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.ctx = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the table
        String query = "create table " + DB_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name text, college text);";
        db.execSQL(query);

        Log.d("MY_DB", "database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop the table and re-create it
        String query = "drop table if EXISTS " + DB_TABLE_NAME;
        db.execSQL(query);

        onCreate(db);
    }

    public void insertData(String s1, String s2) {
        if (this.db == null) {
            this.db = getWritableDatabase();
        }

        String query = "insert into " + DB_TABLE_NAME + "(name, college) values('" + s1 + "','" + s2 + "');";
        db.execSQL(query);

        Toast.makeText(ctx, "Record inserted", Toast.LENGTH_SHORT).show();
    }

    public String getAll() {
        if (this.db == null) {
            this.db = getReadableDatabase();
        }

        Cursor c = null;
        StringBuilder res = new StringBuilder();

        try {
            c = this.db.rawQuery("select * from " + DB_TABLE_NAME, null);

            while (c.moveToNext()) {
                String name = c.getString(c.getColumnIndex("name"));
                String college = c.getString(c.getColumnIndex("college"));

                res.append(name + " : " + college + "\n");
            }
        }
        catch (Exception e) {
            Log.d("MY_DB", e.getMessage());
        }
        finally {
            c.close();
        }

        return res.toString();
    }
}