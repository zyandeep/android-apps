package com.example.zyandeep.shoppinglist;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView[] items;
    private int currEmpty;
    private EditText location;

    public static final int SECOND_ACTIVITY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = findViewById(R.id.loc_editText);

        // reassign it when the orientation changes
        currEmpty = 0;

        items = new TextView[10];
        items[0] = findViewById(R.id.item1);
        items[1] = findViewById(R.id.item2);
        items[2] = findViewById(R.id.item3);
        items[3] = findViewById(R.id.item4);
        items[4] = findViewById(R.id.item5);
        items[5] = findViewById(R.id.item6);
        items[6] = findViewById(R.id.item7);
        items[7] = findViewById(R.id.item8);
        items[8] = findViewById(R.id.item9);
        items[9] = findViewById(R.id.item10);

        if (savedInstanceState != null) {
            currEmpty = savedInstanceState.getInt("currEmpty");
            String[] t = savedInstanceState.getStringArray("items");

            // Initialise the list
            for (int i = 0; i < currEmpty; i++) {
                items[i].setText(t[i]);
            }
        }
    }

    public void addNewItem(View view) {

        if (currEmpty >= 10) {
            Toast.makeText(this, "List is full", Toast.LENGTH_LONG).show();
        }
        else {
            Intent i = new Intent(this, SecondActivity.class);
            startActivityForResult(i, MainActivity.SECOND_ACTIVITY_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.SECOND_ACTIVITY_REQUEST) {
            if (resultCode == RESULT_OK) {
                String item = data.getStringExtra(SecondActivity.ITEM_EXTRA);

                items[currEmpty++].setText(item);
            }
        }
    }

    // Save the current activity instance state

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (currEmpty > 0) {
            String[] temp = new String[currEmpty];

            for (int i = 0; i < currEmpty; i++) {
                temp[i] = items[i].getText().toString();
            }

            outState.putStringArray("items", temp);
            outState.putInt("currEmpty", currEmpty);
        }
    }

    public void locateStore(View view) {
        String data = location.getText().toString();

        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setData(Uri.parse("geo:0,0?q=" + data));

        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }
}
