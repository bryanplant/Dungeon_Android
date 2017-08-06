package com.bryanplant.dungeon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Player{
    private static final String TAG = GameView.class.getSimpleName();

    private Paint paint = new Paint();
    private Rect srcRect, dstRect;
    private Bitmap p;
    private int x, y;
    private int dir, srcWidth, srcHeight, size;
    private double aniT = 0;
    private double aniInterval = .35;
    private double speed = 500;
    private Rect moveRect;
    private boolean moving = false;

    public Player(Bitmap p, int x, int y, int size){
        this.p = p;
        this.x = x;
        this.y = y;
        this.size = size;
        moveRect = new Rect(x, y, x+size, y+size);
        dir = 0;
        srcWidth = p.getWidth()/3;
        srcHeight = p.getHeight()/4;
        srcRect = new Rect(0, 0, srcWidth, srcHeight);
        dstRect = new Rect(x, y, x+size, y+size);
    }

    public void handleInput(int targetX, int targetY, Map map){
            moveRect.offsetTo(targetX-size/2, targetY-size/2);
            moving = true;
    }

    public void update(double dt, Map map){
        move(dt, map);
        animate(dt);
    }

    private void move(double dt, Map map){
        int dstX = moveRect.left;
        int dstY = moveRect.top;
        int nextX = x, nextY = y;
        Rect nextRect;
        moving = true;

        if(x != dstX || y != dstY) {
            double length = Math.sqrt(Math.pow(dstX - x, 2) + Math.pow(dstY - y, 2));
            double vectorX = (dstX - x) / length;
            double vectorY = (dstY - y) / length;
            if (Math.abs(dstX - x) <= Math.abs(speed * vectorX * dt))
                nextX = dstX;
            else
                nextX += speed * vectorX * dt;
            if (Math.abs(dstY - y) <= Math.abs(speed * vectorY * dt))
                nextY = dstY;
            else
                nextY += speed * vectorY * dt;

            nextRect = new Rect(nextX, nextY, nextX+size, nextY+size);
            for (int i = 0; i < map.getMapWidth(); i++) {
                for (int j = 0; j < map.getMapHeight(); j++) {
                    if (map.getTile(i, j).getRect().intersects(nextRect.left, nextRect.top, nextRect.right, nextRect.bottom)) {
                        if (map.getTile(i, j).getType() == 1) {
                            moving = false;
                            moveRect.offsetTo(x, y);
                        }
                    }
                }
            }
            if(moving) {
                x = nextX;
                y = nextY;
                dstRect.set(x, y, x + size, y + size);
            }

            double angle = Math.atan2(vectorY, vectorX) * 180 / Math.PI;
            if (angle > -45 && angle <= 45)
                dir = 0;
            else if (angle > 45 && angle <= 135)
                dir = 1;
            else if (angle > 135 || angle < -135)
                dir = 2;
            else
                dir = 3;
        }

        else
            moving = false;
    }

    private void animate(double dt){
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
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        if(moving) {
            canvas.drawCircle(moveRect.centerX(), moveRect.centerY(), size/2, paint);
            canvas.drawLine(x + size/2, y + size/2, moveRect.centerX(), moveRect.centerY(), paint);
        }

        canvas.drawBitmap(p, srcRect, dstRect, null);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getSize(){
        return size;
    }
}
