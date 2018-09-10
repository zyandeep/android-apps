package com.example.zyandeep.whowroteit2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    EditText editText;
    TextView title;
    TextView author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.bookInput);
        title = findViewById(R.id.title_text);
        author = findViewById(R.id.author_text);


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
            clearTextView("Please enter a search term");
        }
        else if (!isNetworkAvailable()) {
            clearTextView("Please check your network connection and try again");
        }
        else {
            clearTextView("Loading...");

            //new FetchBookTask(title, author).execute(queryString);

            // Using AsyncTask Loader
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        }
    }


    private void clearTextView(String data) {
        title.setText(data);
        author.setText("");
    }


    boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info == null || info.isConnected() == false)
            return false;
        else
            return true;
    }



    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader(this, args.getString("queryString"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        if (!s.isEmpty()) {
            try {
                JSONObject data = new JSONObject(s);
                JSONArray array = data.getJSONArray("items");

                for (int i = 0; i < array.length(); i++) {
                    data = array.getJSONObject(i);
                    data = data.getJSONObject("volumeInfo");

                    String title = null;
                    String author = null;

                    title = data.getString("title");
                    author = data.getString("authors");

                    if (title != null && author != null) {
                        this.title.setText(title);
                        this.author.setText(author);

                        break;
                    }
                }

            }
            catch (JSONException e) {
                e.printStackTrace();

                clearTextView("No Results Found");
            }
        }
        else {
            clearTextView("No Results Found");
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }
}