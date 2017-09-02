package com.bryanplant.dungeon;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Title Screen activity
 * Displays game options and starts corresponding activities
 * @author bryanplant
 */
public class TitleActivity extends AppCompatActivity {
    private static final String TAG = TitleActivity.class.getSimpleName();

    Button play;                //The button to start the game activity
    ImageView background;       //Background image

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        play = (Button)findViewById(R.id.PlayButton);
        background = (ImageView)findViewById(R.id.imageView);
        if (background != null) {      //make sure background image was found
            background.setImageResource(R.mipmap.titlescreen);
            background.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        else{
            Log.d(TAG, "Could not find title screen background image!");
        }

        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {   //if play button is pressed
                    Intent i = new Intent(TitleActivity.this, MainActivity.class);
                    startActivity(i);
            }
        });
    }
}
