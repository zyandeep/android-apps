package com.example.zyandeep.phrasetranslate;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    private boolean isAudioPlaying = false;
    private MediaPlayer mp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void buttonTapped(View v) {
        int id = v.getId();
        String ourId = getResources().getResourceEntryName(id);

        int audioId = getResources().getIdentifier(ourId, "raw", getPackageName());

        //Log.i("Button", getPackageName());

        if (isAudioPlaying) {
            mp.stop();
        }

        mp = MediaPlayer.create(this, audioId);

        mp.start();

        this.isAudioPlaying = true;
    }
}
