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

public class MainActivityOptions extends AppCompatActivity {

    private Tracker mTracker;

    SeekBar timeSlider;
    SeekBar workRestSlider;
    TextView countdownTextView;
    Button statusChangeButton;
    int minutes;
    int seconds;
    int restTime;
    int roundTime;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void tapEMOMButton(View view) {

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Start Workout")
                .build());

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
//        intent.putExtra("workoutLength", timeSlider.getProgress());
//        intent.putExtra("restTime", restTime);
//        intent.putExtra("roundTime", roundTime);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainoptions);

        EMOM application = (EMOM) getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.setScreenName("Image~" + "main");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }
}
