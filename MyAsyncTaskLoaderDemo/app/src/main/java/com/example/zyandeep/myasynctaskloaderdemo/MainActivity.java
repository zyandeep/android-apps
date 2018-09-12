package com.example.zyandeep.myasynctaskloaderdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<String>> {

    // data source
    static List<String> myList = new ArrayList<>();

    EditText ed;
    TextView tv;
    Button button;

    private static final int LOADER_ID = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // fill the datasource
        fillDatasource();

        // load data
        if (getSupportLoaderManager().getLoader(LOADER_ID) != null) {
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }

        //getSupportLoaderManager().restartLoader(LOADER_ID, null, this);


        ed = findViewById(R.id.editText);
        tv = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myList.add(ed.getText().toString());

                // reload the data;
                getSupportLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
            }
        });
    }

    private void fillDatasource() {
        myList.add("Amy");
        myList.add("Bob");
        myList.add("Catherin");
    }

    @NonNull
    @Override
    public Loader<List<String>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyOwnLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<String>> loader, List<String> data) {
        StringBuilder builder = new StringBuilder();

        for (String word : data) {
            builder.append(word + "\n");
        }

        this.tv.setText(builder.toString());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<String>> loader) { }
}