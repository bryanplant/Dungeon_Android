package com.bryanplant.dungeon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.R;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Bryan on 3/13/2017.
 */
public class Player{
    private static final String TAG = GameView.class.getSimpleName();

    private Paint paint = new Paint();
    private Rect srcRect, dstRect;
    private Bitmap p;
    private int x, y, dir, srcWidth, srcHeight, width = 200, height = 200;
    private double aniT = 0;
    private double aniInterval = .35;
    private double speed = 500;
    private int dstX, dstY;
    private boolean moving = false;

    public Player(Bitmap p, int x, int y){
        this.p = p;
        this.x = x;
        this.y = y;
        dstX = x;
        dstY = y;
        dir = 0;
        srcWidth = p.getWidth()/3;
        srcHeight = p.getHeight()/4;
        srcRect = new Rect(0, 0, srcWidth, srcHeight);
        dstRect = new Rect(x, y, x+width, y+height);
    }

    public void handleInput(int x, int y){
        dstX = x-width/2;
        dstY = y-height/2;
    }

    public void update(double dt){
        move(dt);
        animate(dt);
    }

    public void move(double dt){
        if(x != dstX || y != dstY) {
            double length = Math.sqrt(Math.pow(dstX - x, 2) + Math.pow(dstY - y, 2));
            double vectorX = (dstX - x) / length;
            double vectorY = (dstY - y) / length;
            if (Math.abs(dstX - x) <= Math.abs(speed * vectorX * dt))
                x = dstX;
            else
                x += speed * vectorX * dt;
            if (Math.abs(dstY - y) <= Math.abs(speed * vectorY * dt))
                y = dstY;
            else
                y += speed * vectorY * dt;
            dstRect.set(x, y, x + width, y + width);

            double angle = Math.atan2(vectorY, vectorX)*180/Math.PI;
            if(angle > -45 && angle <=45)
                dir = 0;
            else if(angle > 45 && angle <= 135)
                dir = 1;
            else if(angle > 135 || angle < -135)
                dir = 2;
            else
                dir = 3;
            moving = true;
        }
        else
            moving = false;
    }

    public void animate(double dt){
        if(moving) {
            aniT += dt;

            if (aniT < aniInterval) {
                srcRect.offsetTo(srcWidth, srcHeight * dir);
            } else if (aniT >= aniInterval && aniT < aniInterval * 2) {
                srcRect.offsetTo(srcWidth * 2, srcHeight * dir);
            } else if (aniT >= aniInterval * 2 && aniT < aniInterval * 3) {
                aniT = 0;
            }
        }
        else {
            aniT = 0;
            srcRect.offsetTo(0, srcHeight * dir);
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(p, srcRect, dstRect, null);
    }
}
