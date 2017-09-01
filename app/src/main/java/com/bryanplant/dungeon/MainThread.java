package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Thread which contains the game loop
 * @author bryanplant
 */

public class MainThread extends Thread {

    private final static int 	MAX_FPS = 30;                    //max fps allowed
    private final static int	FRAME_PERIOD = 1000 / MAX_FPS;   //number of milliseconds per frame

    private SurfaceHolder surfaceHolder;    //surface holder to access physical surface
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

                        timeDiff = System.currentTimeMillis() - beginTime;  //calculate how long loop took
                        sleepTime = (int) (FRAME_PERIOD - timeDiff);        //calculate how long to sleep

                        if (sleepTime > 0) {
                            try {
                                Thread.sleep(sleepTime);     //thread sleep
                            } catch (InterruptedException e) {
                            }
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
                    Thread.sleep(100);
                } catch (InterruptedException e){}
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}