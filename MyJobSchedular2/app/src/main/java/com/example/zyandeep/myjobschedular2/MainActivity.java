package com.example.zyandeep.myjobschedular2;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private JobScheduler jobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton tb = findViewById(R.id.toggleButton);
        tb.setOnCheckedChangeListener(this);

        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            ComponentName serviceName = new ComponentName(this, MyJobService.class);

            JobInfo.Builder builder = new JobInfo.Builder(10, serviceName)
                                    .setRequiresCharging(true)
                                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);

            jobScheduler.schedule(builder.build());

            Toast.makeText(this, "Job scheduled!", Toast.LENGTH_SHORT).show();
        }
        else {
            jobScheduler.cancelAll();

            Toast.makeText(this, "Job canceled!", Toast.LENGTH_SHORT).show();
        }
    }
}