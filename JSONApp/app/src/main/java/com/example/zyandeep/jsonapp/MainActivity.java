package com.example.zyandeep.jsonapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    // API call
    // http://api.openweathermap.org/data/2.5/weather?q=Jorhat&APPID=4e3e70732dc1c64f42cf98aec90f3b6b

    private class DownloadJson extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String json = "";

            try {
                URL url = new URL(params[0]);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStream is = con.getInputStream();

                Scanner sc = new Scanner(is);
                while (sc.hasNext()) {
                    json += sc.nextLine();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return json;
        }


        // This method can interact with UI elements
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            // {"coord":{"lon":94.21,"lat":26.76},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"02d"}],
            // "base":"stations","main":{"temp":303.245,"pressure":999.99,"humidity":87,"temp_min":303.245,"temp_max":303.245,"sea_level":1011.98,"grnd_level":999.99},
            // "wind":{"speed":1.97,"deg":55.5057},"clouds":{"all":8},"dt":1531623664,
            // "sys":{"message":0.0058,"country":"IN","sunrise":1531609143,"sunset":1531658339},"id":1268820,"name":"Jorhat","cod":200}


            try {
                JSONObject jsonObject = new JSONObject(json);

                JSONArray jsonArray =  jsonObject.getJSONArray("weather");

                //Log.i("weather: ", jsonArray.toString());

                JSONObject weather = jsonArray.getJSONObject(0);

                Log.i("Main: ", weather.getString("main"));
                Log.i("Description : ", weather.getString("description"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       new DownloadJson().execute("http://api.openweathermap.org/data/2.5/weather?q=Jorhat&APPID=4e3e70732dc1c64f42cf98aec90f3b6b");
    }
}
