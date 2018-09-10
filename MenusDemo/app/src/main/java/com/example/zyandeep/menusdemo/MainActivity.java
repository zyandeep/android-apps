package com.example.zyandeep.menusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    EditText ed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        ed = findViewById(R.id.editText);

        registerForContextMenu(tv);
        registerForContextMenu(ed);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.option_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                showToast("Seach is selected");
                break;

            case R.id.setting:
                showToast("Setting is selected");
                break;

            case R.id.status:
                showToast("Status is selected");
                break;
        }

        return true;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (v.getId()) {
            case R.id.textView:
                getMenuInflater().inflate(R.menu.text_view_menu, menu);
                break;

            case R.id.editText:
                getMenuInflater().inflate(R.menu.option_menu, menu);
                break;
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                showToast("Seach is selected");
                break;

            case R.id.setting:
                showToast("Setting is selected");
                break;

            case R.id.status:
                showToast("Status is selected");
                break;

            case R.id.share:
                showToast("Share is selected");
                break;

            case R.id.copy:
                showToast("Copy is selected");
                break;
        }

        return true;
    }


    void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}