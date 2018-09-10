package com.example.zyandeep.materialshape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeScroll;
import android.transition.ChangeTransform;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class SharedElementAnimation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setSharedElementEnterTransition(new ChangeBounds());

        setContentView(R.layout.activity_shared_element_animation);
    }

    public void sayHello(View view) {
        Toast.makeText(this, "Hello android", Toast.LENGTH_SHORT).show();
    }
}
