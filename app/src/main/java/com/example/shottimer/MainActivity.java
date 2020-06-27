package com.example.shottimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    Button btStart;
    Button btStop;
    Button btReset;

    private boolean isResume;
    Handler handler;
    long tMillisec, tStart, tBuff, tUpdate = 0L;
    int sec, min, milliSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        btStart = findViewById(R.id.startButton);
        btStop = findViewById(R.id.stopButton);
        btReset = findViewById(R.id.resetButton);

        handler = new Handler();

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                    isResume = true;
                }
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tBuff += tMillisec;
                handler.removeCallbacks(runnable);
                chronometer.stop();
                isResume = false;
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    tMillisec = 0L;
                    tStart = 0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    sec = 0;
                    min = 0;
                    milliSec = 0;
                    chronometer.setText("00:00:00");
                }
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMillisec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMillisec;
            sec = (int) (tUpdate/1000);
            min = sec / 60;
            sec = sec % 60;
            milliSec = (int) (tUpdate % 100.00);
            chronometer.setText(String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" + String.format("%02d", milliSec));
            handler.postDelayed(this, 60);
        }
    };
}
