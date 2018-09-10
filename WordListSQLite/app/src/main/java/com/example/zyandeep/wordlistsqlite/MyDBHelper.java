package com.example.zyandeep.wordlistsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "wordlist";
    private static final String DB_TABLE_NAME = "word_entries";
    private static final int DB_VERSION = 1;

    Context ctx;
    SQLiteDatabase mDb;


    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        this.ctx = context;
        mDb = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, word TEXT, word_desc TEXT);";
        db.execSQL(query);

        Log.d("MY_DB", "database created");

        mDb = db;

        fillDBWithData();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + DB_TABLE_NAME;
        db.execSQL(query);

        onCreate(db);
    }

    private void fillDBWithData() {

        String[] words = {"Tribbiani", "Di Maria"};
        String[] desc = new String[]{"The Tribbianis are known for their surprisingly small feet",
                "Oh! He knows how to take a corner"};

        ContentValues values = new ContentValues();

        for (int i = 0; i < words.length; i++) {
            values.put("word", words[i]);
            values.put("word_desc", desc[i]);

            mDb.insert(DB_TABLE_NAME, null, values);
        }
    }


    public Cursor query(int position) {

        // position( of recyclerView ) starts form 0
        // autoincrement id starts from 1
        // SQL LIMIT postion starts from 0

        Cursor cursor = null;
        String query = "";


        if (position >= 0) {
            query = "SELECT * FROM " + DB_TABLE_NAME + " ORDER BY word COLLATE NOCASE ASC LIMIT " + position + " , 1;";
        }
        else {
            query = "SELECT * FROM " + DB_TABLE_NAME + " ORDER BY word COLLATE NOCASE ASC";
        }


        try {
            if (mDb == null) {
                mDb = getReadableDatabase();
            }

            cursor = mDb.rawQuery(query, null);
        }
        catch (Exception e) {
            Log.e("MY_DB", e.getMessage());
        }


        return cursor;
    }


    public long insert(String word, String desc) {
        long id = 0;
        ContentValues values = new ContentValues();
        values.put("word", word);
        values.put("word_desc", desc);

        try {
            if (mDb == null) {
                mDb = getWritableDatabase();
            }

            id = mDb.insert(DB_TABLE_NAME, null, values);
        }
        catch (Exception e) {
            Log.e("MY_DB", e.getMessage());
        }

        return id;
    }


    public Cursor count() {
        MatrixCursor cursor = new MatrixCursor(new String[]{DB_TABLE_NAME});

        if (mDb == null) {
            mDb = getReadableDatabase();
        }

        int rows = (int) DatabaseUtils.queryNumEntries(mDb, DB_TABLE_NAME);
        cursor.addRow(new Object[]{rows});


        return  cursor;
    }


    public int delete(int id) {
        int rows = 0;

        try {
            if (mDb == null) {
                mDb = getWritableDatabase();
            }

            rows = mDb.delete(DB_TABLE_NAME, "id = ?", new String[]{id + ""});
        }
        catch (Exception e) { }

        return  rows;
    }


    public int update(int id, String word, String desc) {
        int rows = -1;

        ContentValues values = new ContentValues();
        values.put("word", word);
        values.put("word_desc", desc);

        try {
            if (mDb == null) {
                mDb = getWritableDatabase();
            }

            rows = mDb.update(DB_TABLE_NAME, values, "id = ?", new String[]{id + ""});
        }
        catch (Exception e) { }

        return rows;
    }


    public List<WordItem> searchWords(String term) {
        List<WordItem> myList = new ArrayList<>();
        Cursor c = null;

        try {
            if (mDb == null) {
                mDb = getReadableDatabase();
            }


            c = mDb.query(DB_TABLE_NAME, new String[]{"*"}, "word LIKE ?", new String[]{"%" + term + "%"},
                            null, null, "word COLLATE NOCASE ASC");

            while (c.moveToNext()) {
                WordItem item = new WordItem();

                item.setId(c.getInt(c.getColumnIndex("id")));
                item.setWord(c.getString(c.getColumnIndex("word")));
                item.setDesc(c.getString(c.getColumnIndex("word_desc")));

                // add the word to the list
                myList.add(item);
            }
        }
        catch (Exception e) {}
        finally {
            c.close();
        }

        return myList;
    }
}