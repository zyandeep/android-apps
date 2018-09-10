package com.example.zyandeep.wordlistsqlite;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Contract {

    private static final String DB_NAME = "wordlist";
    private static final String DB_TABLE_NAME = "word_entries";

    public static final String AUTHORITY = "com.example.zyandeep.wordlistsqlite.provider";

    private Contract() {
    }

    public static abstract class WordEntries implements BaseColumns {
        public static final String COL_ID = "id";
        public static final String COL_WORD = "word";
        public static final String COL_DESC = "word_desc";

        public static final String CONTENT_PATH = DB_TABLE_NAME;

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);
        public static final Uri ROW_COUNT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH + "/count");


        static final String SINGLE_RECORD_MIME_TYPE = "vnd.android.cursor.item/com.example.zyandeep.wordlistsqlite.word_entries";
        static final String MULTIPLE_RECORDS_MIME_TYPE =
                "vnd.android.cursor.dir/com.example.zyandeep.wordlistsqlite.word_entries";

    }
}