package com.example.zyandeep.materialme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private ImageView pic;
    private TextView heading;
    private TextView detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        pic = findViewById(R.id.imageView_detail);
        heading = findViewById(R.id.heading_detail);
        detail = findViewById(R.id.subTitle_detail);

        Intent intent = getIntent();

        heading.setText(intent.getStringExtra("heading"));
        //pic.setImageResource(intent.getIntExtra("image", 0));
        detail.setText(intent.getStringExtra("detail"));

        // Load image with Glide
        Glide.with(this).load(intent.getIntExtra("image", 0)).into(pic);

    }
}
