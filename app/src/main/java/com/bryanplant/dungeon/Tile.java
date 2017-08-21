package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Tile {
    private Rect rect;
    private int x, y, size;
    private int type, color;

    public Tile(int x, int y, int size, int type){
        this.x = x;
        this.y = y;
        this.type = type;
        this.size = size;
        rect = new Rect(x*size, y*size, x*size+size, y*size+size);

        switch(type){
            case 0:
                color = Color.argb(255, 60, 105, 112);
                break;
            case 1:
                color = Color.BLACK;
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

    public int getSize(){
        return size;
    }
}
