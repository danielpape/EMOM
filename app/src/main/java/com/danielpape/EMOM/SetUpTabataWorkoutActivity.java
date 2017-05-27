package com.danielpape.EMOM;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Intent;
import android.app.Application;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class SetUpTabataWorkoutActivity extends AppCompatActivity {

    private Tracker mTracker;

    SeekBar timeSlider;
    TextView countdownTextView;
    Button statusChangeButton;
    int minutes;
    int seconds;
    int restTime;
    int roundTime;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void controlTimer(View view) {

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Start Tabata Workout")
                .build());

        Intent intent = new Intent(getBaseContext(), TabataActivity.class);
//        intent.putExtra("workoutLength", timeSlider.getProgress());
//        intent.putExtra("restTime", restTime);
//        intent.putExtra("roundTime", roundTime);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_tabata_workout);

        EMOM application = (EMOM) getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName("Image~" + "main");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        timeSlider = (SeekBar)findViewById(R.id.timeSlider);
        statusChangeButton = (Button)findViewById(R.id.statusChangeButton);

        final TextView totalTimeTextView = (TextView)findViewById(R.id.totalTimeTextView);

        timeSlider.setMax(1800);
        timeSlider.setProgress(600);
        restTime = 20;
        roundTime = 60;

        timeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                minutes = (int) i / roundTime;
                int second = i - minutes * 60;
                if(minutes == 0){
                    minutes = 1;
                }
                if(minutes == 1){
                    totalTimeTextView.setText(Integer.toString(minutes)+" min");
                }else {
                    totalTimeTextView.setText(Integer.toString(minutes) + " mins");
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






    }
}
