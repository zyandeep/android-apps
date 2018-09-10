package com.example.zyandeep.wordlistsqlite;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditWordActivity extends AppCompatActivity {

    EditText ed, ed2;
    int mId;
    int mPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        ed = findViewById(R.id.editText);
        ed2 = findViewById(R.id.editText_desc);

        // when updating a word and its description
        Intent i1 = getIntent();
        mId = i1.getIntExtra("id", -11);
        mPos = i1.getIntExtra("position", -12);
        String word = i1.getStringExtra("word");
        String desc = i1.getStringExtra("word_desc");

        if (mId != -11 && !word.isEmpty() && !desc.isEmpty()) {
            // show the word to update
            ed.setText(word);
            ed2.setText(desc);
        }

    }

    public void returnReply(View view) {
        String newWord = ed.getText().toString();
        String desc = ed2.getText().toString();

        if (newWord.isEmpty() || desc.isEmpty()) {
            Snackbar.make(findViewById(R.id.liner_layout1), "Empty fields", Snackbar.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent();
            i.putExtra("new_word", newWord);
            i.putExtra("word_desc", desc);

            if (mId != -11 && mPos != -12) {
                i.putExtra("id", mId);
                i.putExtra("position", mPos);
            }

            setResult(RESULT_OK, i);
            finish();
        }
    }
}