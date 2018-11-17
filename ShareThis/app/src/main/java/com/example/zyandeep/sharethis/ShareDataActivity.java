package com.example.zyandeep.sharethis;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

public class ShareDataActivity extends AppCompatActivity {

    private static final int ARRAY_SIZE = 5;

    TextView[] textViews;
    CheckBox[] checkBoxes;

    String dataToShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_data);

        // get the calling intent
        Intent intent = getIntent();

        //
        textViews = new TextView[] {
                findViewById(R.id.tv_name),
                findViewById(R.id.tv_phone),
                findViewById(R.id.tv_sem),
                findViewById(R.id.tv_age),
                findViewById(R.id.tv_address)
        };

        //
        for (int j = 0; j < ARRAY_SIZE; j++) {
            switch (j) {
                case 0:
                    textViews[j].setText(intent.getStringExtra(MainActivity.KEY_NAME));
                    break;
                case 1:
                    textViews[j].setText(intent.getStringExtra(MainActivity.KEY_PH));
                    break;
                case 2:
                    textViews[j].setText(intent.getStringExtra(MainActivity.KEY_SEM));
                    break;
                case 3:
                    textViews[j].setText(intent.getStringExtra(MainActivity.KEY_AGE));
                    break;
                case 4:
                    textViews[j].setText(intent.getStringExtra(MainActivity.KEY_ADDRESS));
                    break;
            }
        }

        //
        checkBoxes = new CheckBox[] {
                findViewById(R.id.name_cb),
                findViewById(R.id.ph_cb),
                findViewById(R.id.sem_cb),
                findViewById(R.id.age_cb),
                findViewById(R.id.address_cb)
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        dataToShare = "";

        if (item.getItemId() == R.id.share_mi) {
            for (int i = 0; i < ARRAY_SIZE; i++) {
                if (checkBoxes[i].isChecked()) {

                    switch (i) {
                        case 0:
                            dataToShare = dataToShare + "Name: " + textViews[i].getText().toString() + "\n";
                            break;
                        case 1:
                            dataToShare = dataToShare + "Phone: " + textViews[i].getText().toString() + "\n";
                            break;
                        case 2:
                            dataToShare = dataToShare + "Sem: " + textViews[i].getText().toString() + "\n";
                            break;
                        case 3:
                            dataToShare = dataToShare + "Age: " + textViews[i].getText().toString() + "\n";
                            break;
                        case 4:
                            dataToShare = dataToShare + "Address: " + textViews[i].getText().toString() + "\n";
                    }

                }
            }

            // app chooser
            ShareCompat.IntentBuilder
                    .from(this)
                    .setChooserTitle("Share Data via...")
                    .setType("text/plain")
                    .setText(dataToShare)
                    .startChooser();
        }
        return true;
    }
}


////////////////////////////////////////////////////////////////////
/*
* package com.example.zyandeep.sharethis;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class ShareDataActivity extends AppCompatActivity {

    TextView nameTv, phTv, semTv, ageTv, addTv;
    CheckBox nameCb, phCb, semCb, ageCb, addCb;
    String nameData = "", phData = "", semData = "", ageData = "", addData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_data);

        // get the calling intent
        final Intent intent = getIntent();

        //
        nameTv = findViewById(R.id.tv_name);
        nameTv.setText(intent.getStringExtra(MainActivity.KEY_NAME));
        phTv = findViewById(R.id.tv_phone);
        phTv.setText(intent.getStringExtra(MainActivity.KEY_PH));
        semTv = findViewById(R.id.tv_sem);
        semTv.setText(intent.getStringExtra(MainActivity.KEY_SEM));
        ageTv = findViewById(R.id.tv_age);
        ageTv.setText(intent.getStringExtra(MainActivity.KEY_AGE));
        addTv = findViewById(R.id.tv_address);
        addTv.setText(intent.getStringExtra(MainActivity.KEY_ADDRESS));

        //
        nameCb = findViewById(R.id.name_cb);
        nameCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nameData = "Name: " + intent.getStringExtra(MainActivity.KEY_NAME) + "\n";
                } else {
                    nameData = "";
                }
            }
        });
        phCb = findViewById(R.id.ph_cb);
        phCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    phData = "Phone: " + intent.getStringExtra(MainActivity.KEY_PH) + "\n";
                } else {
                    phData = "";
                }
            }
        });
        semCb = findViewById(R.id.sem_cb);
        semCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    semData = "Semester: " + intent.getStringExtra(MainActivity.KEY_SEM) + "\n";
                } else {
                    semData = "";
                }
            }
        });
        ageCb = findViewById(R.id.age_cb);
        ageCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ageData = "Age: " + intent.getStringExtra(MainActivity.KEY_AGE) + "\n";
                } else {
                    ageData = "";
                }
            }
        });
        addCb = findViewById(R.id.address_cb);
        addCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addData = "Address: " + intent.getStringExtra(MainActivity.KEY_ADDRESS) + "\n";
                } else {
                    addData = "";
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String dataToShare = nameData + phData + semData + ageData + addData;

        // app chooser
        ShareCompat.IntentBuilder
                .from(this)
                .setChooserTitle("Share Data via...")
                .setType("text/plain")
                .setText(dataToShare)
                .startChooser();

        return true;
    }
}
*/