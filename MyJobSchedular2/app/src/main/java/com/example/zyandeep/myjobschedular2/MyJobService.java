package com.example.zyandeep.myjobschedular2;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyJobService extends JobService {

    JobParameters params;
    boolean finishedJob;

    public MyJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        this.params = params;

        Log.i("MY_JOB",  "Job started");

        new MyAsyncTask(getApplicationContext()).execute();

        return true;         // Do it the the background
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        this.params = params;
        Toast.makeText(this, "Job failed! Rescheduling it...", Toast.LENGTH_SHORT).show();

        return true;            // reschedule the job
    }




    ///////////////////////////////////////////////////////
    //// MyAsyncTask
    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        Context context;

        public MyAsyncTask(Context context) {
            this.context = context;
            finishedJob = false;
        }

        @Override
        protected Void doInBackground(Void... voids) {
           for (int i = 1; i <= 10; i++) {
               try {
                   Thread.sleep(2 * 1000);

                   Log.i("MY_JOB",  i+"");
               }
               catch (Exception e) { }
           }

            finishedJob = true;

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (finishedJob) {
                Toast.makeText(context, "Job finished", Toast.LENGTH_SHORT).show();
            }

            jobFinished(MyJobService.this.params, false);
        }
    }
}