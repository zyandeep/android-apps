package com.example.zyandeep.threeactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String REPLY_EXTRA = "reply";

    private ImageView pic;
    private TextView heading;
    private TextView para;

    private String replyMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        pic = findViewById(R.id.image);
        heading = findViewById(R.id.heading_textView);
        para = findViewById(R.id.para);

        // get the intent
        Intent i = getIntent();
        String data = i.getStringExtra(MainActivity.DATA_EXTRA);


        if (data.equals("podcast")) {
            showPara("Podcast", R.drawable.podcast, R.string.podcast);

            this.replyMsg = "Podcast";
        }
        else if(data.equals("rick_and_morty")) {
            showPara("Rick and Morty", R.drawable.rick, R.string.rick_and_morty);

            this.replyMsg = "Rick and Morty";
        }
        else {
            showPara("MacOS Mojavee", R.drawable.macos, R.string.macos);

            this.replyMsg = "MacOS Mojavee";
        }
    }



    private void showPara(String head, int resPicID, int resStrID) {
        heading.setText(head);

        pic.setImageResource(resPicID);

        para.setText(resStrID);
    }

    public void readNewArticle(View view) {
        Intent reply = new Intent();

        reply.putExtra(SecondActivity.REPLY_EXTRA, replyMsg);

        setResult(RESULT_OK, reply);

        finish();
    }
}