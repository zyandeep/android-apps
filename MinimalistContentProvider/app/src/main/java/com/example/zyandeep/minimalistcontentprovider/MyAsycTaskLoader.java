package com.example.zyandeep.minimalistcontentprovider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class MyAsycTaskLoader extends AsyncTaskLoader<Cursor> {

    Context context;

    public MyAsycTaskLoader(@NonNull Context context) {
        super(context);

        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    @Nullable
    @Override
    public Cursor loadInBackground() {
        Uri uri = Uri.parse(MyContract.CONTENT_URI.toString() + "/1");
        Cursor cursor = context.getContentResolver().query(uri, null, null,
                null, null);

        return cursor;
    }
}
