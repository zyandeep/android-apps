package com.example.zyandeep.minimalistcontentprovider;

import android.net.Uri;

public final class MyContract {

    public static final String AUTHORITY = "com.example.zyandeep.minimalistcontentprovider.provider";
    public static final String CONTENT_PATH = "words";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);

    // Declaring MIME Type
    static final String SINGLE_RECORD_MIME_TYPE =
            "vnd.android.cursor.item/vnd.com.example.zyandeep.minimalistcontentprovider.provider.words";

    static final String MULTIPLE_RECORDS_MIME_TYPE =
            "vnd.android.cursor.dir/vnd.com.example.zyandeep.minimalistcontentprovider.provider.words";


    private MyContract() { }

}