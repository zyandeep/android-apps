package com.example.zyandeep.simplecalc;

public class Calculator {

    public enum Operator {ADD, SUB, MUL, DIV};

    public double doAdd(double op1, double op2) {
        return op1 + op2;
    }

    public double doSub(double op1, double op2) {
        return op1 - op2;
    }

    public double doMul(double op1, double op2) {
        return op1 * op2;
    }

    public double doDiv(double op1, double op2) {
        return op1 / op2;
    }
}