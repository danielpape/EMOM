package com.danielpape.EMOM;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Intent;


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

        Intent intent = new Intent(getBaseContext(), WorkoutActivity.class);
        intent.putExtra("workoutLength", timeSlider.getProgress());
        intent.putExtra("restTime", restTime);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSlider = (SeekBar)findViewById(R.id.timeSlider);
        workRestSlider = (SeekBar)findViewById(R.id.workRestSlider);
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
