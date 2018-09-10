package com.example.zyandeep.lifecycle2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "Reply";

    private EditText r;
    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.i("SecondActivity", "SecondActivity.onCreate");

        r = findViewById(R.id.editText_reply);
        t = findViewById(R.id.text_message);

        // get the intent object
        Intent i = getIntent();
        String msg = i.getStringExtra(MainActivity.EXTRA_MESSAGE);

        t.setText(msg);
    }

    public void returnReply(View view) {
        String msg = r.getText().toString();

        Intent reply = new Intent();
        reply.putExtra(SecondActivity.EXTRA_REPLY, msg);

        setResult(RESULT_OK, reply);        // successfull reply

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("SecondActivity", "SecondActivity.onDestroy");
    }
}