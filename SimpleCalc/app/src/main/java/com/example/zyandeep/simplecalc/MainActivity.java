package com.example.zyandeep.simplecalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Calculator cal;
    private EditText op1;
    private EditText op2;
    private TextView res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cal = new Calculator();
        op1 = findViewById(R.id.op1_editText);
        op2 = findViewById(R.id.op2_editText);
        res = findViewById(R.id.result_textView);
    }

    public void doAdd(View view) {
        compute(Calculator.Operator.ADD);
    }

    public void doSub(View view) {
        compute(Calculator.Operator.SUB);
    }

    public void doMul(View view) {
        compute(Calculator.Operator.MUL);
    }

    public void doDiv(View view) {
        compute(Calculator.Operator.DIV);
    }


    private void compute(Calculator.Operator e) {
        String res = "";

        double o1 = getOperand(op1);
        double o2 = getOperand(op2);

        switch (e) {
            case ADD:
                res = Double.toString(cal.doAdd(o1, o2));
                break;

            case SUB:
                res = Double.toString(cal.doSub(o1, o2));
                break;

            case MUL:
                res = Double.toString(cal.doMul(o1, o2));
                break;

            case DIV:
                res = Double.toString(cal.doDiv(o1, o2));
        }

        this.res.setText(res);
    }


    private double getOperand(EditText e) {
        String op = getOperandText(e);

        return Double.parseDouble(op);
    }


    private String getOperandText(EditText e) {
        return e.getText().toString();
    }
}
