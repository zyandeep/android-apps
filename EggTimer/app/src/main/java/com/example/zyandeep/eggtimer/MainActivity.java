package com.example.zyandeep.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SeekBar sb;
    private TextView tv;
    private Button bt;
    private CountDownTimer cd;


    private int seekBarValue;
    private boolean isCounterRunning;



    private void initialiseTimer() {
        sb.setProgress(0);
        tv.setText("00:00");
        bt.setText("Go!");
        isCounterRunning = false;

        sb.setEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sb = (SeekBar) findViewById(R.id.mySeekBar);
        sb.setMax(600);


        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarValue = i;

                int min = seekBarValue / 60;
                int sec = seekBarValue % 60;


                tv.setText(String.format("%02d:%02d", min, sec));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });



        tv = (TextView) findViewById(R.id.displayTextView);
        bt = (Button) findViewById(R.id.myButton);


        initialiseTimer();
    }


    public void buttonTapped(View v) {

        if (isCounterRunning && cd != null) {

            cd.cancel();

            isCounterRunning = false;

            sb.setEnabled(true);
            bt.setText("Go!");

        }
        else if(seekBarValue > 0) {

            // Start the timer
            isCounterRunning = true;
            bt.setText("Stop!");
            sb.setEnabled(false);


            cd = new CountDownTimer(seekBarValue * 1000, 1000) {

                @Override
                public void onTick(long l) {
                    // convert milisecond into second
                    l /= 1000;

                    seekBarValue = (int)l;


                    int min = (int) l / 60;
                    int sec = (int) l % 60;

                    tv.setText(String.format("%02d:%02d", min, sec));
                }

                @Override
                public void onFinish() {

                    // timer completed. Reset it.
                    initialiseTimer();

                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mp.start();
                }
            };

            // Start the timer
            cd.start();

        }
        else {
            Toast.makeText(getApplicationContext(), "Set a value first", Toast.LENGTH_SHORT).show();
        }

    }
}
