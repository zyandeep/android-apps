package com.example.zyandeep.webimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    // https://vignette.wikia.nocookie.net/rickandmorty/images/3/35/Mortyprof.png/revision/latest?cb=20140129023114

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.connect();

                InputStream in = con.getInputStream();

                Bitmap myImage = BitmapFactory.decodeStream(in);

                return myImage;

            }
            catch (MalformedURLException e) {
                displayToast(e.getMessage());
            }
            catch (IOException e) {
                displayToast(e.getMessage());
            }


            return null;
        }
    }



    private void displayToast(String msg) {
        Log.i("Error: ", msg);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView image = (ImageView) findViewById(R.id.myImageView);

        Button button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Download the image in the background
                DownloadImage task = new DownloadImage();

                Bitmap pic;

                try {
                    pic = task.execute("https://tse4.mm.bing.net/th?id=OIP.Nx4Pn0HYpCs1DmzeP-QzfwHaHY&pid=Api&P=0&w=300&h=300").get();

                    if (pic != null) {
                        image.setImageBitmap(pic);
                    }
                    else {
                        displayToast("Image couldn't be downloaded");
                    }
                }
                catch (InterruptedException e) {
                    displayToast(e.getMessage());
                }
                catch (ExecutionException e) {
                    displayToast(e.getMessage());
                }
            }
        });
    }

}