package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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
     */
    public void draw(Canvas canvas, Camera camera){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(camera.getX(), camera.getY(), 300+camera.getX(), 100+camera.getY(), paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(64);
        canvas.drawText("Score: 0", 20+camera.getX(), 70+camera.getY(), paint);
    }
}
