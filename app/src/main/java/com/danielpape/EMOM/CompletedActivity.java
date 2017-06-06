 package com.danielpape.EMOM;

 import android.net.Uri;
 import android.support.v4.app.FragmentManager;
 import android.support.v7.app.AppCompatActivity;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.content.Intent;

 import com.facebook.share.model.ShareOpenGraphAction;
 import com.facebook.share.model.ShareOpenGraphContent;
 import com.facebook.share.model.ShareOpenGraphObject;
 import com.facebook.share.widget.ShareDialog;

 import static com.danielpape.EMOM.R.drawable.cardtabatabggradient;


 public class CompletedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

    }

public void shareButtonTapped(View view){
    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
    sharingIntent.setType("text/plain");
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "I just completed a workout with Simple Timer for EMOM & HIIT. Check it out at http://" +
            "simpletimer.com");
    startActivity(Intent.createChooser(sharingIntent, "Share image using"));
}

     public void onBackPressed() {
         Intent intent = new Intent(getBaseContext(), MainActivityOptions.class);
         startActivity(intent);
     }


}
