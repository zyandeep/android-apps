package com.example.zyandeep.whowroteit2;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {
    private static  HttpURLConnection conn = null;
    private static  InputStream is = null;
    private static  Scanner sc = null;

    private static  String jsonString = "";


    public static String getBookInfo(String query) {

        String baseAddress = "https://www.googleapis.com/books/v1/volumes?q=" + query;
        String maxResults = "&maxResults=10";
        String printType = "&printType=books";
        String finalQuery = baseAddress + maxResults + printType;


        try {
            URL url = new URL(finalQuery);

            // Open the network conn
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            is = conn.getInputStream();

            StringBuilder builder = new StringBuilder();
            sc = new Scanner(is);

            while (sc.hasNext()) {
                builder.append(sc.nextLine());
            }

            jsonString = builder.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (sc != null)
                sc.close();

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null)
                conn.disconnect();

            // return json String
            return jsonString;
        }
    }
}