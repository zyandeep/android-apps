package com.example.zyandeep.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

   // private ImageView bart;
    private ImageView homer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bart = (ImageView) findViewById(R.id.bart);
        homer = (ImageView) findViewById(R.id.homer);

        /*homer.setTranslationX(-2000f);
        homer.setTranslationY(-2000f);*/

    }


    /*public void fadeBart(View view) {

        bart.animate().alpha(0.0f).setDuration(2000L);

        homer.animate().alpha(1f).setDuration(2000L);
    }*/


    public void fadeHomer(View view) {

        // Fade

//        homer.animate().alpha(0.0f).setDuration(2000L);

//        bart.animate().alpha(1f).setDuration(2000L);

//        homer.animate().translationYBy(500f).setDuration(2000);

        // Screen co-ordinate system
        // top-left [0,0]
        // towards right +ve, down +ve

        // Check where homer is on the screen
        // Translation

        /*if(homer.getTranslationX() < 0) {
            // bring it in the view

            homer.animate().translationXBy(2000f).setDuration(2000);
            homer.animate().translationYBy(2000f).setDuration(2000);
        }
        else {
            homer.animate().translationXBy(-2000f).setDuration(2000);
            homer.animate().translationYBy(-2000f).setDuration(2000);
        }
*/

        // Rotation
        homer.animate().rotation(3600f).setDuration(2000);


        // Scalling
        //homer.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000);

        //homer.animate().translationYBy(500f).rotation(3600).setDuration(3000);
    }
}
