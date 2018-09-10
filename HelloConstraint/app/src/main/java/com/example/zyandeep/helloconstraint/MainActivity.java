package com.example.zyandeep.helloconstraint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private int myCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.show_count);

        if (savedInstanceState != null) {
            textView.setText(savedInstanceState.getString("data"));

            myCount = Integer.parseInt(savedInstanceState.getString("data"));
        }
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toastMessage, Toast.LENGTH_LONG);
        toast.show();
    }

    public void countUp(View view) {
        myCount++;

        textView.setText(String.valueOf(myCount));
    }


    // Save the activity instance state

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("data", textView.getText().toString());
    }
}