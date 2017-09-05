package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Thread which contains the game loop
 * @author bryanplant
 */

class MainThread extends Thread {

    private static final String TAG = MainThread.class.getSimpleName();

    private final static int 	MAX_FPS = 30;                    //max fps allowed
    private final static long	FRAME_PERIOD = 1000 / MAX_FPS;   //number of milliseconds per frame

    private final SurfaceHolder surfaceHolder;    //surface holder to access physical surface
    private GameView gameView;              //handles input and draws game to screen

    private boolean running;                //if the game is running

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        Canvas canvas;  //canvas to be drawn to

        long beginTime = System.currentTimeMillis(); //starting time of loop
        long timeDiff;		//how long it took the loop to execute
        int sleepTime;		//how long to wait at end of loop
        double dt;          //how long since last update
        int frames = 0;
        double fpsStart = System.currentTimeMillis();

        while (!this.isInterrupted()) {
            if(running) {
                canvas = null;

                try {
                    canvas = this.surfaceHolder.lockCanvas(); //lock canvas to edit pixels
                    synchronized (surfaceHolder) {
                        dt = (double)(System.currentTimeMillis() - beginTime)/1000; //calculate time since last update
                        beginTime = System.currentTimeMillis(); //reset loop begin time

                        this.gameView.update(dt);       //update state of game
                        this.gameView.render(canvas);   //render to screen
                        frames++;

                        timeDiff = System.currentTimeMillis() - beginTime;  //calculate how long loop took
                        sleepTime = (int)(FRAME_PERIOD - timeDiff);      //calculate when to sleep till

                        if (sleepTime > 0) {
                            try {
                                sleep(sleepTime);     //thread sleep
                            } catch (InterruptedException e) {
                                //thread interrupted
                            }
                        }

                        if(System.currentTimeMillis() - fpsStart >= 1000){
                            gameView.setFpsAverage(frames/((System.currentTimeMillis() - fpsStart)/1000));
                            frames = 0;
                            fpsStart = System.currentTimeMillis();
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
            else{
                try {
                    sleep(100);
                } catch (InterruptedException e){
                    //thread interrupted
                }
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}