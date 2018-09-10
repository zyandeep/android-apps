package com.example.zyandeep.audiovideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView video = (VideoView) findViewById(R.id.videoID);

        video.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.ani);


        MediaController mc = new MediaController(this);

        mc.setAnchorView(video);

        video.setMediaController(mc);

        video.start();
    }
}
