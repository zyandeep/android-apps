package com.example.zyandeep.myasynctaskloaderdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class MyOwnLoader extends AsyncTaskLoader<List<String>> {

    Context ctx;

    public MyOwnLoader(@NonNull Context context) {
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
    public List<String> loadInBackground() {
        return MainActivity.myList;
    }
}