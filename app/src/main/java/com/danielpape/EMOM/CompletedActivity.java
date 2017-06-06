 package com.danielpape.EMOM;

 import android.net.Uri;
 import android.support.v7.app.AppCompatActivity;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.content.Intent;


public class CompletedActivity extends AppCompatActivity {

    Intent shareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        Button twitterShareButton = (Button) findViewById(R.id.twitterShareButton);



    }

public void twitterButtonTapped(View view){
    String tweetUrl = "https://twitter.com/intent/tweet?text=test+message";
    Uri uri = Uri.parse(tweetUrl);
    startActivity(new Intent(Intent.ACTION_VIEW, uri));
}




}
