package com.example.zyandeep.droidcafe;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean wantToQuit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.order_menu_item:
                displayToast(getString(R.string.action_order_message));
                break;

            case R.id.status_menu_item:
                displayToast(getString(R.string.action_status_message));
                break;

            case R.id.fav_menu_item:
                displayToast(getString(R.string.action_favorites_message));
                break;

            case R.id.contact_menu_item:
                displayToast(getString(R.string.action_contact_message));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (wantToQuit) {
            finish();
        }
        else {
            Snackbar.make(findViewById(R.id.relative_layout), "Press again to quit", Snackbar.LENGTH_SHORT).show();
            wantToQuit = true;
        }
    }



    public void displayToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public void showFoodOrder(String message) {
        displayToast(message);

        Intent i = new Intent(this, OrderActivity.class);
        startActivity(i);
    }

    public void showDonutOrder(View view) {
        showFoodOrder(getString(R.string.donut_order_message));
    }

    public void showIcecreamOrder(View view) {
        showFoodOrder(getString(R.string.froyo_order_message));
    }

    public void showFroyoOrder(View view) {
        showFoodOrder(getString(R.string.ice_cream_order_message));
    }

    public void fabTapped(View view) {
       /* //Log.i("FAB: ", "Tapped");
        Snackbar sb =Snackbar.make(view, "Not avaiable at this time", Snackbar.LENGTH_SHORT);
        sb.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to handle the event
            }
        });

        sb.show();*/

       Intent i = new Intent(Intent.ACTION_VIEW);
       Uri uri = Uri.parse("geo:37.422114,-122.086744?z=12");
       i.setData(uri);

       if (i.resolveActivity(getPackageManager()) != null) {
           startActivity(i);
       }
       else {
           Snackbar.make(view, "Google Maps not found", Snackbar.LENGTH_LONG).show();
       }
    }
}