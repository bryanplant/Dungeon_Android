package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.text.DecimalFormat;

/**
 * Displays elements on screen which move separately from environment
 * Such as score and ui buttons
 * @author bryanplant
 */

public class HUD {
    public HUD(){
    }

    /*
     * Uses camera coordinates to correctly draw certain elements to canvas
     * @param canvas Canvas which everything is drawn to
     * @param camera Camera object
     * @param fps Current frames per second of game
     * @param screenWidth Width of device screen in pixels
     * @param screenHeight Height of device in pixels
     */
    public void draw(Canvas canvas, Camera camera, double fps, int screenWidth, int screenHeight){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(camera.getX(), camera.getY(), 300+camera.getX(), 100+camera.getY(), paint);
        canvas.drawRect(camera.getX() + screenWidth - 300, camera.getY(), camera.getX() + screenWidth, camera.getY()+100, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(64);
        canvas.drawText("Score: 0", camera.getX()+20, camera.getY() + 70, paint);
        DecimalFormat df = new DecimalFormat(".#");
        canvas.drawText("FPS: " + df.format(fps), camera.getX() + screenWidth-275, camera.getY() + 70, paint);
    }
}
