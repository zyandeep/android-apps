package com.example.zyandeep.whowroteit2;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchBookTask extends AsyncTask<String, Void, String> {

    private TextView title;
    private TextView author;

    public FetchBookTask(TextView title, TextView author) {
        this.title = title;
        this.author = author;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtil.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


        if (!s.isEmpty()) {
            try {
                JSONObject data = new JSONObject(s);
                JSONArray array = data.getJSONArray("items");

                for (int i = 0; i < array.length(); i++) {
                    data = array.getJSONObject(i);
                    data = data.getJSONObject("volumeInfo");

                    String title = null;
                    String author = null;

                    title = data.getString("title");
                    author = data.getString("authors");

                    if (title != null && author != null) {
                        this.title.setText(title);
                        this.author.setText(author);
                    }
                }

            }
            catch (JSONException e) {
                e.printStackTrace();

                clearTextView();
            }
        }
        else {
            clearTextView();
        }
    }


    private void clearTextView() {
        title.setText(R.string.not_found);
        author.setText("");      // 0 to clear the textView
    }
}