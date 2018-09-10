package com.example.zyandeep.minimalistcontentprovider;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class MyAsyncLoader2 extends AsyncTaskLoader<Cursor> {

    private Context ctx;

    public MyAsyncLoader2(@NonNull Context context) {
        super(context);

        this.ctx = context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }


    @Nullable
    @Override
    public Cursor loadInBackground() {
        Cursor cursor = ctx.getContentResolver()
                .query(MyContract.CONTENT_URI, null, null,
                null, null);

        return cursor;
    }
}