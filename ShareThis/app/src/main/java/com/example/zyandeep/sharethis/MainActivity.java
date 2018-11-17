package com.example.zyandeep.sharethis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "SHARE_APP";

    public static final String KEY_NAME = "name";
    public static final String KEY_PH = "ph";
    public static final String KEY_AGE = "age";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_SEM = "sem";

    EditText name, ph, age, address, sem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name_ed);
        ph = findViewById(R.id.ph_ed);
        age = findViewById(R.id.age_ed);
        address = findViewById(R.id.address_ed);
        sem = findViewById(R.id.sem_ed);
    }


    public void shareDate(View view) {
        Intent i = new Intent(getApplicationContext(), ShareDataActivity.class);

        // assume data validation has been done
        i.putExtra(KEY_NAME, name.getText().toString());
        i.putExtra(KEY_PH, ph.getText().toString());
        i.putExtra(KEY_AGE, age.getText().toString());
        i.putExtra(KEY_ADDRESS, address.getText().toString());
        i.putExtra(KEY_SEM, sem.getText().toString());

        startActivity(i);
    }
}
