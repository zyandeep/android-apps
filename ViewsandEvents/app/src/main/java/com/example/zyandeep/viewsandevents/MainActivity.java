package com.example.zyandeep.viewsandevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.myTextView);
        button = findViewById(R.id.button1);

        button.setOnClickListener(this);
    }

    public void buttonTapped(View view) {
        textView.setText("You Clicked Button 2");
    }


    @Override
    public void onClick(View view) {
        textView.setText("You Clicked Button 1");
    }
}
