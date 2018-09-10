package com.example.zyandeep.lifecycle2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Message";
    public static final int SECOND_ACTIVITY_REQUEST = 1;

    private EditText ed;
    private TextView header;
    private TextView reply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header = findViewById(R.id.reply_header);
        reply = findViewById(R.id.reply_message);

        ed = findViewById(R.id.editText_main);

        Log.i("MainActivity", "MainActivity.onCreate");
    }

    public void launchSecondActivity(View view) {
        //Log.d("Button", "Button clicked!" );

        String msg = ed.getText().toString();

        Intent i = new Intent(this, SecondActivity.class);
        i.putExtra(MainActivity.EXTRA_MESSAGE, msg);

        startActivityForResult(i, SECOND_ACTIVITY_REQUEST);
    }

    // callback method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.SECOND_ACTIVITY_REQUEST) {

            // successfull reply
            if (resultCode == RESULT_OK) {

                // get data from the intent
                String r = data.getExtras().getString(SecondActivity.EXTRA_REPLY);

                header.setVisibility(View.VISIBLE);
                reply.setText(r);

                //Log.d("Reply Intent: ", "OK!" );
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        Log.i("MainActivity", "MainActivity.onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i("MainActivity", "MainActivity.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("MainActivity", "MainActivity.onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("MainActivity", "MainActivity.Restart");
    }
}