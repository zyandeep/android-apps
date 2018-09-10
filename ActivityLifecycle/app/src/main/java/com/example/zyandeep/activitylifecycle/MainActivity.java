package com.example.zyandeep.activitylifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText e;
    TextView t;

    private void showToast(String msg) {
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showToast("onCreate");

        e = findViewById(R.id.editText);
        t = findViewById(R.id.textView);

        if (savedInstanceState != null) {
            //Log.i("MainActivity", savedInstanceState.toString());
            //e.setText(savedInstanceState.getString("data"));

            t.setText("Silence of the lamb");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        showToast("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        showToast("onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        showToast("onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();

        showToast("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        showToast("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        showToast("onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        showToast("Saving activity state");

        outState.putString("data", e.getText().toString());

    }
}