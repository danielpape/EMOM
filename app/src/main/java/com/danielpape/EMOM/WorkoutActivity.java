package com.danielpape.EMOM;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class WorkoutActivity extends AppCompatActivity {

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
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);

    }

    public void updateTimer(int secondsLeft) {
        if(seconds == 0 || seconds == 59 || seconds == 58 || seconds == 57){
            View view = this.getWindow().getDecorView();
            view.setBackgroundColor(Color.rgb(29,233,182));
        }
        else if(seconds >= 62-workTime){
            View view = this.getWindow().getDecorView();
            view.setBackgroundColor(Color.rgb(29,233,182));
        }else {
            View view = this.getWindow().getDecorView();
            view.setBackgroundColor(Color.rgb(239,83,80));
        }

        minutes = (int)secondsLeft / 60;
        seconds = secondsLeft - minutes * 60;

        String minuteString = Integer.toString(minutes);
        String secondString = Integer.toString(seconds);

        if (seconds <= 9){
            secondString = "0" + secondString;
        }

        countdownLabel.setText(minuteString+":"+secondString);
        periodProgressBar.setProgress(60-seconds);

    }

    public void controlCountdown (){
            MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.workoutstarted);
            mPlayer.start();

            countDownTimer = new CountDownTimer(workoutLength * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);

                    currentRound = (workoutLength/60)-minutes;
                    roundTextView.setText("Round "+currentRound+" of "+(workoutLength/60));

                    if (seconds == 3) {
                        MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.countdownbegin);
                        mPlayer.start();
                    } else if((minutes == 0) && (seconds == restTime+3)) {
                       MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.countdownworkoutcomplete);
                        mPlayer.start();
                    } else if ((seconds == restTime+3)) {
                        MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.countdownrest);
                        mPlayer.start();
                    }

                }

                @Override
                public void onFinish() {


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

        endWorkoutButton.setBackgroundColor(Color.argb(100,255,255,255));


        workoutLength = getIntent().getIntExtra("workoutLength",600);
        System.out.print("Workout length is: "+workoutLength);
        minutes = workoutLength / 60;
        seconds = 0;
        System.out.print("processed workout length is: "+minutes+":"+seconds);
        restTime = getIntent().getIntExtra("restTime",20);
        workTime = 60-restTime;
        countdownLabel.setText((workoutLength/60)+":00");

        currentRound = (workoutLength/60)-minutes+1;

        roundTextView.setText("Round "+currentRound+" of "+(workoutLength/60));
        periodProgressBar.setMax(60);

        controlCountdown();

    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }
}
