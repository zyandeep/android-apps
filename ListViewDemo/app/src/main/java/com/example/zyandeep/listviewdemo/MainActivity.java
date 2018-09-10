package com.example.zyandeep.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myList = (ListView) findViewById(R.id.myListView);

        final List<String> peopel = new ArrayList<String>();
        peopel.add("Diego");
        peopel.add("Ravi");
        peopel.add("Maria");
        peopel.add("Simon");
        peopel.add("Bob");
        peopel.add("Mohan");

        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, peopel);
        myList.setAdapter(ad);


        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.i("Item", Integer.toString(i));

                //Log.i("Item", peopel.get(i));

                Toast.makeText(MainActivity.this, "Hello " + peopel.get(i), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
