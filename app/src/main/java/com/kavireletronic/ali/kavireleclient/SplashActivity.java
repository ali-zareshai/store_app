package com.kavireletronic.ali.kavireleclient;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import static maes.tech.intentanim.CustomIntent.customType;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startTimer();
    }

    private void startTimer() {
        progressBar=(ProgressBar)findViewById(R.id.progressBarSpalsh);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));

        Timer timer=new Timer();

        timer.schedule(new TimerTask() {
            double i;
            @Override
            public void run() {
                progressBar.setProgress((int)i);
                i=i+2;

            }
        },0,100);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                customType(SplashActivity.this,"fadein-to-fadeout");

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
