package com.example.zyandeep.myasynctaskdemo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openDialog(View view) {
        new MyAsyncTask().execute();
    }



    class MyAsyncTask extends AsyncTask<Void, Integer, String> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("File Download");
            dialog.setMessage("Downloading... Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(10);

            dialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();

                    // cancel the background task
                    MyAsyncTask.this.cancel(true);
                }
            });

            dialog.show();
        }



        @Override
        protected String doInBackground(Void... voids) {

            String res = "default";

           try {
               for (int i = 1; i <= 30; i++) {
                   Thread.sleep(1000);

                   Log.i("Thread", String.valueOf(i));

                   publishProgress(i);
               }

               // close the dialog box
               dialog.dismiss();

               res = "Successful";
           }
           catch (Exception e) {
               res = "Failure";
           }

           return  res;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            dialog.setProgress(values[0]);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
