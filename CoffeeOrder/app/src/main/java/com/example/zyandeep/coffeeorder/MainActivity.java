package com.example.zyandeep.coffeeorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView quantity;
    private TextView totalAmount;

    private int count = 0;
    private final int PRICE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantity = findViewById(R.id.userQuantity);
        totalAmount = findViewById(R.id.totalAmount);

        Button down = findViewById(R.id.down);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count <= 0) {
                    displayToast("Invalid amount");
                }
                else {
                    count--;

                    showTotal();
                }
            }
        });


        Button up = findViewById(R.id.up);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count >= 20) {
                    displayToast("Max order amount 20");
                }
                else {
                    count++;

                    showTotal();
                }
            }
        });
    }

    private void showTotal() {
        quantity.setText(String.format("%d", count));
        totalAmount.setText(String.format("Rs. %d", count * 10));
    }

    private void displayToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.show();
    }

}
