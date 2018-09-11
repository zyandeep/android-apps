package com.example.zyandeep.wordlistloader;

import android.net.Uri;

public final class Contract {

    public static final String AUTHORITY = "com.example.zyandeep.wordlistsqlite.provider";
    public static final String CONTENT_PATH = "word_entries";

    public static final String COL_ID = "id";
    public static final String COL_WORD = "word";
    public static final String COL_DESC = "word_desc";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);

    private Contract() {
    }
}