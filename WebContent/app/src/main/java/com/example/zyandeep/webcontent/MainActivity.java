package com.example.zyandeep.webcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    private class DownLoadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            //Log.i("URL: ", urls[0]);

            String webPage = "";

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.connect();

                InputStream in = con.getInputStream();

                // Read the html content
                Scanner sc = new Scanner(in);

                while (sc.hasNext()) {
                    webPage += sc.next();
                }

                return webPage;

            }
            catch (Exception e) {
                Log.i("Error: ", e.getMessage());

                return "Failed";
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DownLoadTask task = new DownLoadTask();

        try {
            String result = task.execute("https://www.google.com/").get();

            Log.i("Result", result);

        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();
        }


    }
}
