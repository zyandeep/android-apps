package com.example.zyandeep.materialshape;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        //getWindow().setExitTransition(new Slide());
        //getWindow().setSharedElementExitTransition(new ChangeImageTransform());

        setContentView(R.layout.activity_main);
    }


    public void makeTransition(View view) {
        Intent i = new Intent(this, SharedElementAnimation.class);

        View v = findViewById(R.id.droid_pic);

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v, "simple_android");

        startActivity(i, options.toBundle());
    }

    public void doAnimate(View view) {
        ImageView pic = findViewById(view.getId());

        pic.animate().rotation(180f).setDuration(1000);

    }

    public void doExplode(View view) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent i = new Intent(this, SecondActivity.class);
        startActivity(i, options.toBundle());
    }
}
