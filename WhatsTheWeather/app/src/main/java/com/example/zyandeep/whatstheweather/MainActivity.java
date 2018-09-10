package com.example.zyandeep.whatstheweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private TextView result;


    private class DownloadWeather extends AsyncTask<String, Void, String> {

        // API call: http://api.openweathermap.org/data/2.5/weather?q=<city_name>&APPID=4e3e70732dc1c64f42cf98aec90f3b6b

        @Override
        protected String doInBackground(String... params) {

            String json = "";

            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + params[0] + "&APPID=4e3e70732dc1c64f42cf98aec90f3b6b");

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStream is = con.getInputStream();

                Scanner sc = new Scanner(is);
                if (sc.hasNext()) {
                    json += sc.nextLine();
                }

            }
            catch (MalformedURLException e) {
                Log.i("URL Error: ", e.getMessage());
            }
            catch (IOException e) {
                Log.i("Network Error: ", e.getMessage());
            }

           return json;
        }

        // Can change UI elements
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            if (json.isEmpty()) {
                showToast("Invalid city name");
            }
            else {
                try {
                    JSONObject jsob = new JSONObject(json);
                    JSONArray jarr = jsob.getJSONArray("weather");

                    JSONObject weatherObject = jarr.getJSONObject(0);
                    String main = weatherObject.getString("main");
                    String desc = weatherObject.getString("description");


                    JSONObject tempObject = jsob.getJSONObject("main");
                    double tempKelvin = tempObject.getDouble("temp");
                    double tempCel = tempKelvin - 273.15;

                    String msg = String.format("%s : %s \nTemp: %.2f", main, desc, tempCel);

                    result.setText(msg);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText cityName = findViewById(R.id.cityEditText);
        result = (TextView) findViewById(R.id.result);


        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = cityName.getText().toString();

                if (input.isEmpty()) {
                    showToast("Enter a city name");
                }
                else {
                    new DownloadWeather().execute(input);
                }
            }
        });
    }
}
