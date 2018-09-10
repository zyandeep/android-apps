package com.example.zyandeep.networkconnectiondemo;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DownloadTextTask extends AsyncTask<String, Void, String> {

    private TextView textView;


    public DownloadTextTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder response = new StringBuilder("");

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Read data
            InputStream is = conn.getInputStream();
            Scanner sc = new Scanner(is);
            while (sc.hasNext()) {
                response.append(sc.nextLine());
            }

            // close resource
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Close network connection

            return response.toString();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (!s.isEmpty()) {
            textView.setText(s);
        }
    }
}