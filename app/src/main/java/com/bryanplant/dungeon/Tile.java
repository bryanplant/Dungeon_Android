package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Bryan on 6/16/2017.
 */

public class Tile {
    private int x, y, width, height;
    private  int color;

    public Tile(int x, int y, int width, int height, int color){
        this.x = x;
        this.y = y;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(color);

        canvas.drawRect(x*width, y*height, x*width+width, y*height+height, paint);
    }

}
