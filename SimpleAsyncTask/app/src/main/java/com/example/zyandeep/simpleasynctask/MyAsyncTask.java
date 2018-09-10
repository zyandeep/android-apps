package com.example.zyandeep.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.util.Random;

public class MyAsyncTask extends AsyncTask<Void, Integer, String> {

    private TextView textView;

    public MyAsyncTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(Void... voids) {

        int random = new Random().nextInt(11);

        random *= 1000;

        publishProgress(random);

        try {
            Thread.sleep(random);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return String.format("Awake at last after %d milliseconds", random);
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        this.textView.setText("Current sleep time " + values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        textView.setText(s);
    }
}