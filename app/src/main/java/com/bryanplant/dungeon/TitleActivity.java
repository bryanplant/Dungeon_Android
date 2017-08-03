package com.bryanplant.dungeon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TitleActivity extends AppCompatActivity {
    private static final String TAG = TitleActivity.class.getSimpleName();

    Button play;
    ImageView background;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        play = (Button)findViewById(R.id.PlayButton);
        background = (ImageView)findViewById(R.id.imageView);
        background.setImageResource(R.mipmap.titlescreen);
        background.setScaleType(ImageView.ScaleType.FIT_XY);

        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent i = new Intent(TitleActivity.this, MainActivity.class);
                    startActivity(i);
            }
        });
    }
}
