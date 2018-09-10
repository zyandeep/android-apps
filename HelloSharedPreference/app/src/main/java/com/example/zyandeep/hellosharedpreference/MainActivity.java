package com.example.zyandeep.hellosharedpreference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mShowCountTextView;
    private static  SharedPreferences sh;

    private static int mCount = 0;
    public static int mColor;
    public final static String MY_PREF_FILE = "zyandeep.hello_pref";
    public static final String COUNT_KEY = "count";
    public static final String COLOR_KEY = "color";
    public static boolean autoSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views, color
        mShowCountTextView = (TextView) findViewById(R.id.count_textview);

        sh = getSharedPreferences(MY_PREF_FILE, MODE_PRIVATE);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Restore the app preference
        mCount = sh.getInt(COUNT_KEY, 0);
        mColor = sh.getInt(COLOR_KEY, ContextCompat.getColor(this, R.color.default_background));

        // apply the preference
        mShowCountTextView.setText(String.format("%s", mCount));
        mShowCountTextView.setBackgroundColor(mColor);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (autoSave) {
            savePreference();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_option_menu, menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item) {
            startActivity(new Intent(this, SettingActivity.class));
        }

        return true;
    }


    public void changeBackground(View view) {
        mColor = ((ColorDrawable) view.getBackground()).getColor();

        mShowCountTextView.setBackgroundColor(mColor);
    }

    public void countUp(View view) {
        mCount++;
        mShowCountTextView.setText(String.format("%s", mCount));
    }

    public void countDown(View view) {
        mCount--;
        mShowCountTextView.setText(String.format("%s", mCount));
    }


    public static void savePreference() {
        SharedPreferences.Editor editor = sh.edit();
        editor.putInt(COUNT_KEY, mCount);
        editor.putInt(COLOR_KEY, mColor);

        editor.apply();
    }
}