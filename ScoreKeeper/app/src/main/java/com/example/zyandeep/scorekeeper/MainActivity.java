package com.example.zyandeep.scorekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView team1Score, team2Score;
    int score1 = 0, score2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        team1Score = findViewById(R.id.t1_score);
        team2Score = findViewById(R.id.t2_score);

        if (savedInstanceState != null) {
            score1 = savedInstanceState.getInt("score1");
            score2 = savedInstanceState.getInt("score2");

            setScore(1); setScore(2);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        // change the menu item
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        MenuItem menuItem = menu.findItem(R.id.theme);

        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menuItem.setTitle(R.string.day_mode);
        }
        else {
            menuItem.setTitle(R.string.night_mode);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.theme) {
            int nightMode = AppCompatDelegate.getDefaultNightMode();


            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }

        // recreate the activity to take affect
        recreate();

        return  true;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("score1", score1);
        outState.putInt("score2", score2);
    }


    public void decreaseScore(View view) {
        switch (view.getId()) {
            case R.id.t1_minus:
                this.score1--;
                setScore(1);
                break;

            case R.id.t2_minus:
                score2--;
                setScore(2);
                break;
        }
    }

    public void increaseScore(View view) {
        switch (view.getId()) {
            case R.id.t1_plus:
                this.score1++;
                setScore(1);
                break;

            case R.id.t2_plus:
                score2++;
                setScore(2);
                break;
        }
    }

    private void setScore(int teamID) {
        if (teamID == 1) {
            team1Score.setText(String.valueOf(score1));
        }
        else {
            team2Score.setText(String.valueOf(score2));
        }
    }
}