package com.bryanplant.dungeon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.R;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Bryan on 3/13/2017.
 */
public class Player{
   private Paint paint = new Paint();
    private Rect src, dst;
    private Bitmap p;
    private int x, y, src_width, src_height, width = 100, height = 100;

    public Player(Bitmap p, int x, int y){
        this.p = p;
        this.x = x;
        this.y = y;
        src_width = p.getWidth()/3;
        src_height = p.getHeight()/4;
        src = new Rect(0, 0, src_width, src_height);
        dst = new Rect(x, y, x+width, y+height);
    }

    public void handleInput(int x, int y){
        dst.set(x-width/2, y-height/2, x+width/2, y+height/2);
    }

    public void draw(Canvas canvas){
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        canvas.drawBitmap(p, src, dst, null);
    }
}
