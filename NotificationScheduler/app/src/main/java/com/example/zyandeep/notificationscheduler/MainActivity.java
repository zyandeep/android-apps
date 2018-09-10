package com.example.zyandeep.notificationscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_JOB_ID = 110;
    private int seekBarValue = 0;

    RadioGroup radioGroup;
    JobScheduler scheduler;
    Switch s1, s2, ps;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radio_group);
        scheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);

        final TextView seekBarProgress = findViewById(R.id.seekBar_value);
        final TextView dynamicTv = findViewById(R.id.myTextView);

        s1 = findViewById(R.id.switch1);
        s2 = findViewById(R.id.switch2);
        ps = findViewById(R.id.p_switch);
        ps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dynamicTv.setText("Periodic Interval:");
                }
                else {
                    dynamicTv.setText("Override Deadline:");
                }
            }
        });


        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                seekBarValue = progress;

                if (progress > 0) {
                    seekBarProgress.setText("" + progress);
                }
                else {
                    seekBarProgress.setText("Not Set");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }


    public void scheduleJob(View view) {

        int selectedNetwork = 0;
        boolean constraintSet = false;

        // Select network type
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.no_network:
                selectedNetwork = JobInfo.NETWORK_TYPE_NONE;
                break;

            case R.id.any_network:
                selectedNetwork = JobInfo.NETWORK_TYPE_ANY;
                constraintSet = true;
                break;

            case R.id.wifi:
                selectedNetwork = JobInfo.NETWORK_TYPE_UNMETERED;
                constraintSet = true;
                break;
        }

        // Select device status
        constraintSet = constraintSet || s1.isChecked() || s2.isChecked() || seekBarValue > 0;


        JobInfo.Builder builder = new JobInfo.Builder(MY_JOB_ID, new ComponentName(this, MyService.class))
                                    .setRequiredNetworkType(selectedNetwork)
                                    .setRequiresCharging(s2.isChecked())
                                    .setRequiresDeviceIdle(s1.isChecked());


        if (seekBarValue > 0) {
            if (ps.isChecked()) {
                builder.setPeriodic(seekBarValue * 1000);
            }
            else {
                builder.setOverrideDeadline(seekBarValue * 1000);
            }
        }


        if (constraintSet) {
            scheduler.schedule(builder.build());
            Toast.makeText(this, "Job Scheduled", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Set atleast one constraint", Toast.LENGTH_SHORT).show();
        }

    }


    public void cancelJob(View view) {
        if (scheduler != null) {
            scheduler.cancelAll();
            Toast.makeText(this, "Job Canceled", Toast.LENGTH_SHORT).show();
        }
    }
}
