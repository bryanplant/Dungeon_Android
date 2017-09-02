package com.bryanplant.dungeon;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Activity to display the GameView
 * Accessed by clicking "Play" button on TitleActivity
 * @author bryanplant
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GameView gameView;  //handles input and draws to screen

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameView = new GameView(this);

        setContentView(gameView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE); //set orientation to landscape, can be rotated

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);      //keep the screen on
        Log.d(TAG, "View added");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "Paused");
        gameView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "Resuming");
        gameView.resume();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "Restarting");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }
}
