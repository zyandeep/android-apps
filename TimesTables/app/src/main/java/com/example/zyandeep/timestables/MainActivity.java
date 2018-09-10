package com.example.zyandeep.timestables;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv = (ListView) findViewById(R.id.tableListView);
        SeekBar sb = (SeekBar) findViewById(R.id.mySeekBar);

        sb.setMax(20);
        sb.setProgress(10);         // Initislising seekbar value


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int min = 1;

                if (i < min) {
                    i = min;

                    seekBar.setProgress(min);
                }

                Integer[] data = new Integer[10];

                // autoboxing
                for (int c = 1; c <= 10; c++) {
                    data[c-1] = i * c;
                }

                ArrayAdapter<Integer> ad = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_list_item_1, data);


                lv.setAdapter(ad);

                //Log.i("Seekbar", String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });








    }
}
