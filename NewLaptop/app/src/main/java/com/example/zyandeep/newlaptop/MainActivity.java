package com.example.zyandeep.newlaptop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private int[] pics;
    private int curPic;
    private int totalPics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pics =  new int[6];

        pics[0] = R.drawable.pic1;
        pics[1] = R.drawable.pic2;
        pics[2] = R.drawable.pic3;
        pics[3] = R.drawable.pic4;
        pics[4] = R.drawable.pic5;
        pics[5] = R.drawable.pic6;

        curPic = 0;
        totalPics = pics.length;
    }


    public void changePic(View v) {
        //Log.i("Button", "change");

        ImageView pic = (ImageView) findViewById(R.id.LaptopImageView);

        if(curPic < (totalPics-1)) {
            curPic++;
        }
        else {
            curPic = 0;
        }

        pic.setImageResource(pics[curPic]);

    }
}