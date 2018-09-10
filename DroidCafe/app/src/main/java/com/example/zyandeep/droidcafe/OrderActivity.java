package com.example.zyandeep.droidcafe;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    private EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ed = findViewById(R.id.phone_editText);

        ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // whether the event has handled or not

                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    // dial the phone number
                    dialPhone();
                    return true;
                }

                return false;
            }
        });
    }



    private void dialPhone() {
        String ph = ed.getText().toString();
        Uri data = Uri.parse("tel:" + ph);

        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(data);

        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
        else {
            Log.i(OrderActivity.class.getSimpleName(), "Can't perform this action");
        }

    }



    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton)view).isChecked();

        switch (view.getId()) {
            case R.id.sameday:
                if (checked) {
                    displayToast(getString(R.string.same_day_messenger_service));
                    break;
                }

            case R.id.nextday:
                if (checked) {
                    displayToast(getString(R.string.next_day_ground_delivery));
                    break;
                }

            case R.id.pickup:
                if (checked) {
                    displayToast(getString(R.string.pick_up));
                    break;
                }

            default:
                Log.d(OrderActivity.class.getSimpleName(), getString(R.string.nothing_selected));
        }
    }
}