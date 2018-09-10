package com.example.zyandeep.materialshape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setAllowEnterTransitionOverlap(true);


//        Slide s = new Slide();
//        s.setSlideEdge(Gravity.TOP);


        getWindow().setEnterTransition(new Explode().setDuration(1000));

        setContentView(R.layout.activity_second);
    }
}
