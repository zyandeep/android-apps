package com.example.zyandeep.wordlistsqlite;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class SearchWordActivity extends AppCompatActivity {

    EditText ed;
    TextView heading;

    RecyclerView recyclerView;
    SearchAdaptar adaptar;
    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        dbHelper = new MyDBHelper(getApplicationContext());

        ed = findViewById(R.id.query_editText);
        heading = findViewById(R.id.heading_tv);

        recyclerView = findViewById(R.id.search_rv);
        adaptar = new SearchAdaptar(getApplicationContext());
        recyclerView.setAdapter(adaptar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void doSearch(View view) {
        String searchTerm = ed.getText().toString();

        if (searchTerm.isEmpty()) {
            // show a warning
            Snackbar sb = Snackbar.make(findViewById(R.id.my_linearLayout), "Empty search term", Snackbar.LENGTH_SHORT);
            sb.show();
        }
        else {
            // search the word in DB
            List<WordItem> words = dbHelper.searchWords(searchTerm);
            adaptar.addNewItems(words);

            if (words.size() > 0 ) {
                heading.setText("Result for " + searchTerm);
            }
            else {
                heading.setText("No data found");
            }
        }
    }
}
