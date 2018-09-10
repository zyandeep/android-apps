package com.example.zyandeep.sounddemo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private SeekBar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this, R.raw.bird);
        sb = (SeekBar) findViewById(R.id.musicSeekBar);

        sb.setMax(mp.getDuration());


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                //Log.i("SeekBar value", String.valueOf(i));

                mp.seekTo(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }


    public void playMusic(View v) {
        mp.start();
    }

    public void pauseMusic(View v) {
        mp.pause();
    }
}
