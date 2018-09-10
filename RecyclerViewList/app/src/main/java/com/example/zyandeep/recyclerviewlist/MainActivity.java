package com.example.zyandeep.recyclerviewlist;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> dataSet = new ArrayList<String>();
    private int counter = 0;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 20; i++) {
            dataSet.add("Word" + counter++);
        }

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new MyAdapter(this, dataSet);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSet.add("Word" + counter++);

                adapter.notifyDataSetChanged();
                //recyclerView.getAdapter().notifyDataSetChanged();

                // scroll to the end
                recyclerView.smoothScrollToPosition(dataSet.size());
            }
        });
    }
}