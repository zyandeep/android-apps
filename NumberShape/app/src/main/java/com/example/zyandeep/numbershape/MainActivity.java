package com.example.zyandeep.numbershape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int number;
    private int check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void checkTriangular() {
        int x;
        int n = 1;

        do {
            x = (n * (n + 1)) / 2;
            n++;
        } while (x < this.number);

        if (x == this.number) {
            this.check += 1;
        }
    }

    private void checkSquare() {
        int x = (int) Math.sqrt(this.number);

        if (x * x == this.number) {
            this.check += 2;
        }
    }



    private void makeToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }


    public void checkNumber(View v) {

         /*
            0 = null
            1 = triangular
            2 = square
            3 = both
        */
        this.number = 0;
        this.check = 0;


        EditText input = (EditText) findViewById(R.id.inputEditText);
        String str = input.getText().toString();

        try {
            this.number = Integer.parseInt(str);

            checkTriangular();
            checkSquare();

            if (check == 0) {
                makeToast("Neither Triangular, nor Square");
            }
            else if (check == 1) {
                makeToast("It's Triangular");
            }
            else if (check == 2) {
                makeToast("It's Square");
            }
            else {
                makeToast("Both Triangular and Square");
            }
        }
        catch (Exception e) {
            makeToast("Invalid input");
        }

    }
}
