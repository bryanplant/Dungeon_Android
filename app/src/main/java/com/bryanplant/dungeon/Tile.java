package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Tile {
    private Rect rect;
    private int x, y, width, height;
    private int type, color;

    public Tile(int x, int y, int width, int height, int type){
        this.x = x;
        this.y = y;
        this.type = type;
        this.width = width;
        this.height = height;
        rect = new Rect(x*width, y*height, x*width+width, y*height+height);

        switch(type){
            case 0:
                color = Color.BLUE;
                break;
            case 1:
                color = Color.CYAN;
                break;
            case 2:
                color = Color.GREEN;
                break;
        }
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(color);
        canvas.drawRect(rect, paint);
    }

    public Rect getRect(){
        return rect;
    }

    public int getType(){
        return type;
    }

}
