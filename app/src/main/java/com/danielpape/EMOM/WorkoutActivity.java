package com.danielpape.EMOM;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class WorkoutActivity extends AppCompatActivity {

    TextView countdownLabel;
    TextView roundTextView;
    TextView workRestTextView;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    Integer workoutLength;
    Integer minutes;
    Integer seconds;
    Integer restTime;
    Integer workTime;
    Integer currentRound;

    public void endWorkout (View view){
        countDownTimer.cancel();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);

    }

    public void updateTimer(int secondsLeft) {
        minutes = (int)secondsLeft / 60;
        seconds = secondsLeft - minutes * 60;

        System.out.println(minutes+" and "+seconds);

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

            countDownTimer = new CountDownTimer(workoutLength * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);

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
        workRestTextView = (TextView)findViewById(R.id.workRestTextView);

        workoutLength = getIntent().getIntExtra("workoutLength",600);
        System.out.print("Workout length is: "+workoutLength);
        minutes = 10;
        seconds = 0;
        System.out.print("processed workout length is: "+minutes+":"+seconds);
        restTime = getIntent().getIntExtra("restTime",20);
        workTime = 60-restTime;
        countdownLabel.setText((workoutLength/60)+":00");

        currentRound = (workoutLength/60)-minutes+1;

        workRestTextView.setText(restTime+" seconds rest / "+workTime+" seconds work");
        roundTextView.setText("Round "+currentRound+" of "+(workoutLength/60));

        controlCountdown();

    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }
}
