package ca.group6.it.thedocky.ui.home;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ca.group6.it.thedocky.databinding.ActivityTimerBinding;

public class TimerActivity extends AppCompatActivity {

    private ActivityTimerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTimerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        long totalSeconds = 90;
        long intervalSeconds = 1;
        CountDownTimer countDownTimer = new CountDownTimer(totalSeconds * 1000, intervalSeconds * 1000) {
            @Override
            public void onTick(long l) {

                long h = (totalSeconds * 1000 - l) / 1000;

                Date date = new Date(h);
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                String dateFormatted = formatter.format(date);

//                binding.countDownTimer.setText(dateFormatted);
//                binding.customBar.setProgress((int) (totalSeconds * 1000 - l )/ 1000);
            }

            @Override
            public void onFinish() {
            }
        };

        binding.starRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.timer.start();

            }
        });

        binding.stopDockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.timer.stop();
            }
        });

    }


}