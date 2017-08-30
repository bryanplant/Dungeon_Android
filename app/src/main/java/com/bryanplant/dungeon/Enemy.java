package com.bryanplant.dungeon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Enemy {

    private int x, y;
    private int dir, srcWidth, srcHeight, size;
    private boolean moving;
    private double aniT = 0;
    private double aniInterval = .35;
    private double speed = 300;
    private Bitmap e;
    private Rect srcRect, dstRect, moveRect;

    public Enemy(Bitmap e, int x, int y, int size){
        this.e = e;
        this.x = x;
        this.y = y;
        this.size = size;
        moveRect = new Rect(x, y, x+size, y+size);
        dir = 0;
        srcWidth = e.getWidth()/3;
        srcHeight = e.getHeight()/4;
        srcRect = new Rect(0, 0, srcWidth, srcHeight);
        dstRect = new Rect(x, y, x+size, y+size);
    }

    public void update(double dt, Player player, Map map){
        move(dt, player, map);
        animate(dt);
    }

    public void move(double dt, Player player, Map map){
        int dstX = player.getX();
        int dstY = player.getY();
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
    }

    private void animate(double dt){
        aniT += dt;

        if (aniT < aniInterval) {
            srcRect.offsetTo(srcWidth, srcHeight * dir);
        } else if (aniT >= aniInterval && aniT < aniInterval * 2) {
            srcRect.offsetTo(srcWidth * 2, srcHeight * dir);
        } else if (aniT >= aniInterval * 2 && aniT < aniInterval * 3) {
            aniT = 0;
        }
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        canvas.drawBitmap(e, srcRect, dstRect, null);
    }
}
