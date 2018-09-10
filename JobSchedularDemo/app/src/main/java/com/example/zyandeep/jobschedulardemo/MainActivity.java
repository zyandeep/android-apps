package com.example.zyandeep.jobschedulardemo;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    ToggleButton tb;
    JobScheduler jobScheduler;

    public static final int MY_JOB_ID = 21;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tb = findViewById(R.id.tb);
        tb.setOnCheckedChangeListener(this);

        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {

            ComponentName componentName = new ComponentName(this, MyJobService.class);
            JobInfo.Builder builder = new JobInfo.Builder(MY_JOB_ID, componentName)
                                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                                        .setBackoffCriteria(3000, JobInfo.BACKOFF_POLICY_LINEAR)
                                        .setRequiresCharging(true);

            jobScheduler.schedule(builder.build());
        }
        else {
            jobScheduler.cancelAll();
        }
    }
}
