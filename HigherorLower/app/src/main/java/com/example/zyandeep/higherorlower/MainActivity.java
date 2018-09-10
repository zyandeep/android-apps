package com.example.zyandeep.higherorlower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    // Computer generated number
    private int randNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randNum = genRandom();
    }



    public void makeToast(String msg) {
        Toast t = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
        t.show();
    }



    public int genRandom() {
        Random r = new Random();
        return r.nextInt(20) + 1;
    }



    public void makeGuess(View v) {


        EditText input = (EditText) findViewById(R.id.inputEditText);

        // User guess
        int number = 0;

        try {
            number = Integer.parseInt(input.getText().toString());
        }
        catch (Exception e) {

        }


        if (number < randNum) {
            makeToast("Too Low");
        }
        else if(number > randNum) {
            makeToast("Too High");
        }
        else {
            makeToast("That was the right guess");

            randNum = genRandom();            

            makeToast("Game's been restarted!");

            
        }

        //Log.i("Rand number", "" + randNum);

    }
}
