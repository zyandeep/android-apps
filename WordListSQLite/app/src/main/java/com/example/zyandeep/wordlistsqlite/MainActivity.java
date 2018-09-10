package com.example.zyandeep.wordlistsqlite;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdaptar adaptar;
    FloatingActionButton fab;

    MyDBHelper dbHelper;

    public static final int ADD_REQUEST = 22;
    public static final int EDIT_REQUEST = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDBHelper(getApplicationContext());

        recyclerView = findViewById(R.id.recyclerview);
        adaptar = new MyAdaptar(MainActivity.this);
        recyclerView.setAdapter(adaptar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditWordActivity.class);
                startActivityForResult(i, ADD_REQUEST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search_mi) {
            // open Search activity
            startActivity(new Intent(this, SearchWordActivity.class));
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String newWord = data.getStringExtra("new_word");
        String newDesc = data.getStringExtra("word_desc");

        if (requestCode == ADD_REQUEST) {
            if (resultCode == RESULT_OK) {
                dbHelper.insert(newWord, newDesc);

                // Notify the adaptar
                adaptar.notifyDataSetChanged();
            }
        }
        else {
            if (resultCode == RESULT_OK) {

                int id = data.getIntExtra("id", -99);
                int pos = data.getIntExtra("position", -99);


                if (id != -99 && pos != -99) {
                    dbHelper.update(id, newWord, newDesc);

                    // Notify the adaptar
                    adaptar.notifyItemChanged(pos);
                }
            }
        }
    }
}