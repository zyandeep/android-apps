package com.example.zyandeep.sharedpreferencedemo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    EditText age, dob;
    Button button;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        dob = findViewById(R.id.dob);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        checkBox = findViewById(R.id.checkBox);
    }


    // Reload data from the shared preference
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sh = getSharedPreferences("zyandeep.my_pref", MODE_PRIVATE);

        if (sh.getBoolean("save_data", false)) {
            String name = sh.getString("name", "");
            int age = sh.getInt("age", 0);
            String dob = sh.getString("dob", "");

            this.name.setText(name);
            this.age.setText(age+"");
            this.dob.setText(dob);
        }

    }

    @Override
    public void onClick(View v) {
        SharedPreferences sh = getSharedPreferences("zyandeep.my_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();

        if (checkBox.isChecked()) {

            // Save the user data
            editor.putBoolean("save_data", true);
            editor.putString("name", name.getText().toString());
            editor.putInt("age", Integer.parseInt(age.getText().toString()));
            editor.putString("dob", dob.getText().toString());
            editor.apply();
        }
        else {
            editor.clear();
            editor.apply();
        }

        Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();
    }
}