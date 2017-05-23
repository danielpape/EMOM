package com.danielpape.EMOM;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.res.Configuration;
import android.view.WindowManager;

import java.io.IOException;

public class EMOMWorkoutActivity extends AppCompatActivity {

    TextView countdownLabel;
    TextView roundTextView;
    TextView workRestTextView;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    ProgressBar periodProgressBar;
    Integer workoutLength;
    Integer minutes;
    Integer seconds;
    Integer restTime;
    Integer workTime;
    Integer currentRound;
    Integer progress = 1;

    public void endWorkout (View view){
        countDownTimer.cancel();
        Intent intent = new Intent(getBaseContext(), SetUpIntervalEMOMActivity.class);
        startActivity(intent);

    }

    public void updateTimer(int secondsLeft) {
        if(seconds == 00 || seconds == 59 || seconds == 58 || seconds == 57){
            View view = this.getWindow().getDecorView();
            view.setBackgroundColor(Color.rgb(29,233,182));
            periodProgressBar.setMax(workTime);
            if (seconds == 0 && currentRound == 1) {
                periodProgressBar.setProgress(0);
            }else {
                periodProgressBar.setProgress(60 - seconds);
            }
        }
        else if(seconds >= 62-workTime){
            View view = this.getWindow().getDecorView();
            view.setBackgroundColor(Color.rgb(29,233,182));
            periodProgressBar.setMax(workTime);
            periodProgressBar.setProgress(60-seconds);
        }else {
            View view = this.getWindow().getDecorView();
            view.setBackgroundColor(Color.rgb(239,83,80));
            periodProgressBar.setMax(restTime);
            periodProgressBar.setProgress(restTime-seconds);
        }

        minutes = (int)secondsLeft / 60;
        seconds = secondsLeft - minutes * 60;

        String minuteString = Integer.toString(minutes);
        String secondString = Integer.toString(seconds);

        if (seconds <= 9){
            secondString = "0" + secondString;
        }

        countdownLabel.setText(minuteString+":"+secondString);

    }

    public void controlCountdown (){
            MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.workoutstarted);
            mPlayer.start();

            periodProgressBar.setProgress(0);

            countDownTimer = new CountDownTimer(workoutLength * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);

                    currentRound = (workoutLength/60)-minutes;
                    if (currentRound  == 0){
                        currentRound = 1;
                    }
                    roundTextView.setText("Round "+currentRound+" of "+(workoutLength/60));

                    if (seconds == 3) {
                        final MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.countdownbegin);
                        mPlayer.start();
                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mPlayer.release();
                            }
                        });
                    } else if((minutes == 0) && (seconds == restTime+3)) {
                       final MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.countdownworkoutcomplete);
                        mPlayer.start();
                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mPlayer.release();
                            }
                        });
                    } else if ((seconds == restTime+3)) {
                        final MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.countdownrest);
                        mPlayer.start();
                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                mPlayer.release();
                            }
                        });
                    }
                    else if((minutes == 0) && (seconds == restTime)) {
                        finish();
                    }

                }

                @Override
                public void onFinish() {
                    finish();

                }
            }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        countdownLabel = (TextView)findViewById(R.id.countdownLabel);
        roundTextView = (TextView)findViewById(R.id.roundTextView);
        periodProgressBar = (ProgressBar)findViewById(R.id.periodProgressBar);
        Button endWorkoutButton = (Button)findViewById(R.id.endWorkoutButton);

        workoutLength = getIntent().getIntExtra("workoutLength",600);
        System.out.print("Workout length is: "+workoutLength);
        minutes = workoutLength / 60;
        seconds = 0;
        System.out.print("processed workout length is: "+minutes+":"+seconds);
        restTime = getIntent().getIntExtra("restTime",20);
        workTime = 60-restTime;
        countdownLabel.setText((workoutLength/60)+":00");

        currentRound = (workoutLength/60)-minutes+1;
        if (currentRound  == 0){
            currentRound = 1;
        }

        roundTextView.setText("Round "+currentRound+" of "+(workoutLength/60));
        periodProgressBar.setMax(60);
        periodProgressBar.setProgress(0);

        controlCountdown();

    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), MainActivityOptions.class);
        startActivity(intent);
    }

}
