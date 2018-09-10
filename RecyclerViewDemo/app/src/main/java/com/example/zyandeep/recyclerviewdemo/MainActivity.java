package com.example.zyandeep.recyclerviewdemo;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdaptar myAdaptar;
    String[] s1, s2;
    int[] p = {R.drawable.puppy, R.drawable.kitten, R.drawable.turtle, R.drawable.rabbit, R.drawable.parrot,
            R.drawable.goldfish, R.drawable.hamster, R.drawable.cow, R.drawable.sheep};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        s1 = getResources().getStringArray(R.array.pets_name);
        s2 = getResources().getStringArray(R.array.desctiption);

        myAdaptar = new MyAdaptar(this, s1, s2, p);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(myAdaptar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        findViewById(R.id.main_heading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, R.string.sb_message, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.sb_action_message, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.this.finish();
                    }
                });
                snackbar.show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Snackbar.make(findViewById(R.id.relative_layout), R.string.exit_msg, Snackbar.LENGTH_LONG).show();
    }
}