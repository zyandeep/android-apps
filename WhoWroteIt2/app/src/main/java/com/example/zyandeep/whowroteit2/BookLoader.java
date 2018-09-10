package com.example.zyandeep.whowroteit2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;


public class BookLoader extends AsyncTaskLoader<String>{

    private String mQuery;

    public BookLoader(@NonNull Context context, String query) {
        super(context);

        this.mQuery = query;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtil.getBookInfo(mQuery);
    }
}