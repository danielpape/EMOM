package com.danielpape.testapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import junit.framework.Test;

public class MainActivity extends AppCompatActivity {

    SeekBar timeSlider;
    SeekBar workRestSlider;
    TextView countdownTextView;
    Button statusChangeButton;
    int minutes;
    int seconds;
    int restTime;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void updateTimer(int secondsLeft) {
        minutes = (int)secondsLeft / 60;
        seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);

        if (seconds <= 9){
            secondString = "0" + secondString;
        }

        countdownTextView.setText(Integer.toString(minutes)+":"+secondString);
    }

    public void resetTimer (){
        countDownTimer.cancel();
        counterIsActive = false;
        timeSlider.setEnabled(true);
        workRestSlider.setEnabled(true);
        statusChangeButton.setText("Start Workout");
        countdownTextView.setText(timeSlider.getProgress()/60+":00");
    }

    public void controlTimer(View view) {

        if(counterIsActive == false) {
            counterIsActive = true;
            timeSlider.setEnabled(false);
            workRestSlider.setEnabled(false);
            statusChangeButton.setText("Stop Workout");

            countDownTimer = new CountDownTimer(timeSlider.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);

                    if (seconds == 3) {
                        MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.countdown);
                        mPlayer.start();
                    } else if (seconds == restTime+3) {
                        MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.countdown);
                        mPlayer.start();
                    }

                }

                @Override
                public void onFinish() {

                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.complete);
                    mPlayer.start();
                    resetTimer();

                }
            }.start();
        } else {

            resetTimer();

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSlider = (SeekBar)findViewById(R.id.timeSlider);
        workRestSlider = (SeekBar)findViewById(R.id.workRestSlider);
        countdownTextView = (TextView)findViewById(R.id.countdownTextView);
        statusChangeButton = (Button)findViewById(R.id.statusChangeButton);


        final TextView totalTimeTextView = (TextView)findViewById(R.id.totalTimeTextView);
        final TextView workTextView = (TextView)findViewById(R.id.workTextView);
        final TextView restTimeTextView = (TextView)findViewById(R.id.restTextView);

        timeSlider.setMax(1800);
        timeSlider.setProgress(600);
        workRestSlider.setMax(40);
        workRestSlider.setProgress(30);
        restTime = 20;

        timeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                minutes = (int) i / 60;
                int second = i - minutes * 60;
                if(minutes == 0){
                    minutes = 1;
                }
                if(minutes == 1){
                    totalTimeTextView.setText(Integer.toString(minutes)+" minute");
                }else {
                    totalTimeTextView.setText(Integer.toString(minutes) + " minutes");
                }



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                timeSlider.setProgress(minutes*60);
                countdownTextView.setText(minutes+":00");

            }
        });

        workRestSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                i = i+10;

                restTime = (int) 60 - i;
                workTextView.setText(Integer.toString(i)+" secs work");
                restTimeTextView.setText(Integer.toString(restTime)+" secs rest");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
