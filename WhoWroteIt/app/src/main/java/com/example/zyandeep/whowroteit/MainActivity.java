package com.example.zyandeep.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText editText;
    private RecyclerView recyclerView;
    private ArrayList<JSONObject> mDataSet = new ArrayList<>();
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.bookInput);

        recyclerView = findViewById(R.id.myRecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);


        // Re-connect with the loader
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

    }


    public void serachBooks(View view) {
        String queryString = editText.getText().toString();

        // Hide the keyboard
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        // Check input query
        if (queryString.isEmpty()) {
            notifyUser(getString(R.string.enter_book));
        }
        else if (!isNetworkAvailable()) {
            notifyUser(getString(R.string.no_network));
        }
        else {

            //new FetchBookTask(title, author).execute(queryString);

            // Using AsyncTask Loader
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        }
    }


    boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info == null || info.isConnected() == false)
            return false;
        else
            return true;
    }




    //////////////////////////////////////////////////////////////////////////////////////
    /// Loader Callbacks    ////

    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader(this, args.getString("queryString"));
    }


    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        if (!s.isEmpty()) {

            // clear the old data
            this.mDataSet.clear();

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray array = jsonObject.getJSONArray("items");

                for (int i = 0; i < array.length(); i++) {
                    mDataSet.add(array.getJSONObject(i).getJSONObject("volumeInfo"));
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            // Update recyclerview with new data set
            adapter.updateDataset(this.mDataSet);

            // Scroll to top
            recyclerView.smoothScrollToPosition(0);
        }
        else {
            // No results found
            notifyUser(getResources().getString(R.string.not_found));
        }
    }


    public void notifyUser(String msg) {

        adapter.clearDataSet();

        Snackbar snackbar = Snackbar.make(findViewById(R.id.myLayout), msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}