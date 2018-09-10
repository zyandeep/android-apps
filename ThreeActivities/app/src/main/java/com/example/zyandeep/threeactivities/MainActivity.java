package com.example.zyandeep.threeactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int PODCAST_REQUEST = 0;
    public static final int RICK_MORTY_REQUEST = 1;
    public static final int MACOS_REQUEST = 2;

    public static final String DATA_EXTRA = "paragraph";


    private TextView t;         // Displaying response

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t = findViewById(R.id.response_id);
    }

    public void button1Clicked(View view) {
        // Podcast
        Intent i = new Intent(this, SecondActivity.class);

        // put data in the intent
        i.putExtra(DATA_EXTRA, "podcast");

        startActivityForResult(i, MainActivity.PODCAST_REQUEST);
    }

    public void button2Clicked(View view) {
        // Rick and Morty

        Intent i = new Intent(this, SecondActivity.class);
        i.putExtra(MainActivity.DATA_EXTRA, "rick_and_morty");
        startActivityForResult(i, MainActivity.RICK_MORTY_REQUEST);
    }

    public void button3Clicked(View view) {
        // Macos

        Intent i = new Intent(this, SecondActivity.class);
        i.putExtra(MainActivity.DATA_EXTRA, "macos");
        startActivityForResult(i, MainActivity.MACOS_REQUEST);
    }



    // Handle reply intent


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PODCAST_REQUEST) {
            if (resultCode == RESULT_OK) {
                showReply(data);
            }
        }
        else if(requestCode == RICK_MORTY_REQUEST) {
            if (resultCode == RESULT_OK) {
                showReply(data);
            }
        }
        else {
            if (resultCode == RESULT_OK) {
                showReply(data);
            }
        }
    }


    private void showReply(Intent data) {
        String reply = data.getStringExtra(SecondActivity.REPLY_EXTRA);

        t.setText(reply + " has been read");
    }
}