package com.bryanplant.dungeon;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = GameView.class.getSimpleName();
    private String avgFps;

    private MainThread thread;
    private Player player;

    public GameView(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player), 100, 100);

        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try {
                thread.join();
                retry = false;
            } catch(InterruptedException e){
                // try again shutting down the thread
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            player.handleInput((int)event.getX(), (int)event.getY());
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }

    public void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        player.draw(canvas);
        // display fps
        displayFps(canvas, avgFps);
    }

    public void update(){

    }

    private void displayFps(Canvas canvas, String fps) {
        if (canvas != null && fps != null) {
            Paint paint = new Paint();
            paint.setARGB(255, 255, 255, 255);
            paint.setTextSize(20);
            canvas.drawText(fps, this.getWidth() - 100, 50, paint);
        }
    }

    public void setAvgFps(String avgFps) {
        this.avgFps = avgFps;
    }
}
