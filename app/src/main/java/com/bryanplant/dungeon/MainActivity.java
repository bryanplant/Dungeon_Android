package com.bryanplant.dungeon;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

//https://www.javacodegeeks.com/2011/07/android-game-development-moving-images.html

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GameView gameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set our MainGamePanel as the View
        gameView = new GameView(this);

        setContentView(gameView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
