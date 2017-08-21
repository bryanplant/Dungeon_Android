package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class HUD {
    public HUD(){
    }

    public void draw(Canvas canvas, Camera camera){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(0+camera.getX(), 0+camera.getY(), 300+camera.getX(), 100+camera.getY(), paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(64);
        canvas.drawText("Score: 0", 20+camera.getX(), 70+camera.getY(), paint);
    }
}
