package com.example.zyandeep.networkconnectiondemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ConnectivityManager manager;
    NetworkInfo networkInfo;

    private TextView textView;
    static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.result_textView);
        imageView = findViewById(R.id.myImageView);
    }

    public void downloadText(View view) {
        // check the network connection

        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownLoadImageTask().execute("https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?auto=compress&cs=tinysrgb&h=350");

            new DownloadTextTask(textView).execute("https://www.lipsum.com/feed/html");

        }
        else {
            Toast.makeText(this, "No Network Available", Toast.LENGTH_LONG).show();
        }
    }
}