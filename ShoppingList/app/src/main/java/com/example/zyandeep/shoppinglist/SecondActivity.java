package com.example.zyandeep.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    public static final String ITEM_EXTRA = "item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void selectItem(View view) {
        Button b = findViewById(view.getId());
        //Log.d("Button", b.getText().toString());

        Intent reply = new Intent();
        reply.putExtra(SecondActivity.ITEM_EXTRA, b.getText().toString());

        setResult(RESULT_OK, reply);

        finish();
    }
}