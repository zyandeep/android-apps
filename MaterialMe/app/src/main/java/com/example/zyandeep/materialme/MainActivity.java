package com.example.zyandeep.materialme;

import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    //Member variables
    private RecyclerView recyclerView;
    private ArrayList<Sport> sportsData;
    private SportsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int column = getResources().getInteger(R.integer.grid_column_count);
        int swipDirs = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, column));

        sportsData = new ArrayList<Sport>();

        //Initialize the adapter and set it ot the RecyclerView
        adapter = new SportsAdapter(sportsData, getApplicationContext());
        recyclerView.setAdapter(adapter);


        initializeData();


        if (column > 1) {
            swipDirs = 0;
        }




        // handle touch event on each itemView
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT|ItemTouchHelper.UP|ItemTouchHelper.DOWN,
                        swipDirs) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                Collections.swap(sportsData, from, to);

                adapter.notifyItemMoved(from, to);

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // delete the data item
                sportsData.remove(viewHolder.getAdapterPosition());

                // notify the adapter
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });


        // attach the ItemTouchHelper to the recycler view
        helper.attachToRecyclerView(recyclerView);
    }



    private void initializeData() {
        //Get the resources from the XML file
        String[] sportsList = getResources().getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
        String[] sportsDetail = getResources().getStringArray(R.array.sports_detail);
        TypedArray sportsImages = getResources().obtainTypedArray(R.array.sports_image);


        //Clear the existing data (to avoid duplication)
        sportsData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for(int i=0;i<sportsList.length;i++){
            sportsData.add(new Sport(sportsList[i],sportsInfo[i], sportsImages.getResourceId(i, 0), sportsDetail[i]));
        }

        //Notify the adapter of the change
        adapter.notifyDataSetChanged();
    }

    public void resetSports(View view) {
        initializeData();
    }
}