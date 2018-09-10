package com.example.zyandeep.guessthesuperhero;

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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class MainActivity extends AppCompatActivity {


    private String[] imageURLs;
    private String[] names;

    private Button[] buttons;
    private ImageView pic;

    private Bitmap superheroPic;

    private int current;




    private class DownloadHTMLPage extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            try {

                Document document = Jsoup.connect(params[0]).get();


                // Get the image urls
                Elements elements = document.select("div.img.imgboxart > img[src]");

                imageURLs = new String[elements.size()];


                int i = 0;

                for (Element img : elements) {
                    imageURLs[i++] = img.attr("src");
                }


                // Get the image names
                Elements h3s = document.select("div.img.imgboxart + h3");

                names = new String[h3s.size()];


                i = 0;

                for (Element h3 : h3s) {
                   names[i++] = h3.text().replaceAll("^\\d+\\.[ *]", "");
                }



            } catch (Exception e) {
                Log.i("IOError: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new DownloadImage().execute();
        }
    }




    private class DownloadImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // choose a random url (value of the current)
            // make sure the same image doesn't appear
            // it's coming up in V2 ;)

            current = new Random().nextInt(100);
        }

        @Override
        protected Void doInBackground(Void... voids) {

          // Download the pic

            try {

                URL url = new URL(imageURLs[current]);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStream is = con.getInputStream();

                superheroPic = BitmapFactory.decodeStream(is);

            }
            catch (Exception e) {
                Log.i("Error: ", e.getMessage());
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            createView();

        }
    }



    private void createView() {

        pic.setAlpha(0f);

        pic.setImageBitmap(superheroPic);
        pic.animate().alpha(1f).setDuration(4000);


        // now set the options

        int buttonIndex = new Random().nextInt(4);

        buttons[buttonIndex].setText(names[current]);

        for (int i = 0; i < buttons.length; i++) {
            if (i != buttonIndex) {

                // randomly pic a celebrity name

                int j = current;

                while (j == current) {
                    j = new Random().nextInt(100);
                }

                buttons[i].setText(names[j]);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pic = findViewById(R.id.superheroImageView);

        buttons = new Button[4];
        buttons[0] = findViewById(R.id.button1);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);

        DownloadHTMLPage htmlPage = new DownloadHTMLPage();
        htmlPage.execute("https://comicvine.gamespot.com/profile/lvenger/lists/my-100-favourite-superheroes/17520/");
    }



    private void giveAnswer(String msg) {
        Toast.makeText(getApplicationContext(), msg , Toast.LENGTH_SHORT).show();
    }



    public void buttonTapped(View v) {

        Button b = findViewById(v.getId());
        String userInput = b.getText().toString();

        if (names[current].equals(userInput)) {
            giveAnswer("Correct!");
        }
        else {
            giveAnswer("Wrong! It was " + names[current]);
        }

        // generate next image
        new DownloadImage().execute();
    }
}
