package com.danielpape.EMOM;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;

public class CompletedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            Button twitterShareButton = (Button) findViewById(R.id.twitterShareButton);



            }
        });

        public void twitterButtonTapped(View view){
            String tweetUrl = "https://twitter.com/intent/tweet?text=" + shareText;
            Uri uri = Uri.parse(tweetUrl);
            shareIntent = new Intent(Intent.ACTION_VIEW, uri);
            return shareIntent;
        }

}
