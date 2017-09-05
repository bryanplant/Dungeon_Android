package com.bryanplant.dungeon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * The Enemy class stores information about an enemy character
 * and handles everything required to animate the character and update
 * the character's position and actions
 * @author bryanplant
 */
public class Enemy {
    private static final String TAG = Enemy.class.getSimpleName();

    private int x, y;                   //x and y location of enemy
    int tileX, tileY;
    int targetTileX, targetTileY;
    int nextTileX, nextTileY;
    boolean newNextTile = true;
    private int dir;                    //direction the enemy is facing
    private int srcWidth, srcHeight;    //dimensions of enemy sprite on source png
    private int size;                   //width and height of sprite to be drawn to the screen
    private boolean moving;             //if the enemy is moving or not
    private Bitmap e;                   //stores enemy sprite
    private Rect srcRect, dstRect;  //sprite on bitmap, sprite on screen
    private double aniT = 0;            //animation timer
    int color;

    /*
     * Initializes Enemy
     * @param e Bitmap containing enemy sprite
     * @param x Starting x coordinate of Enemy
     * @param y Starting y coordinate of Enemy
     * @param size Width and height of sprite drawn to screen
     */
    public Enemy(Bitmap e, int x, int y, int size){
        this.e = e;
        this.x = x;
        this.y = y;
        this.size = size;
        dir = 0;
        srcWidth = e.getWidth()/3;
        srcHeight = e.getHeight()/4;
        srcRect = new Rect(0, 0, srcWidth, srcHeight);
        dstRect = new Rect(x, y, x+size, y+size);
        color = Color.RED;
    }

    /*
     * Updates players position and animation
     * @param dt Amount of time since last update
     * @param player The Player object
     * @param map The Map object
     */
    public void update(double dt, Player player, Map map){
        move(dt, player, map);
        animate(dt);
    }

    /*
     * Updates the position of the enemy
     * @param dt Amount of time since last update
     * @param player The Player object
     * @param map The Map object
     */
    private void move(double dt, Player player, Map map){
        double speed = 2.5*size;         //speed at which the enemy moves
        moving = true;

        tileX = dstRect.centerX()/map.getTileSize();
        tileY = dstRect.centerY()/map.getTileSize();

        int lastDir = dir;

        if(newNextTile){
            double bestDistance = 999;
            if(map.getTile(tileX, tileY-1).getType() != 1 && lastDir != 1){
                nextTileX = tileX;
                nextTileY = tileY-1;
                bestDistance = Math.sqrt(Math.pow(tileX-targetTileX, 2) + Math.pow((tileY-1)-targetTileY, 2));
                dir = 3;
            }
            if(map.getTile(tileX-1, tileY).getType() != 1 && lastDir != 0){
                double distance = Math.sqrt(Math.pow((tileX-1)-targetTileX, 2) + Math.pow(tileY-targetTileY, 2));
                if(distance < bestDistance){
                    nextTileX = tileX-1;
                    nextTileY = tileY;
                    bestDistance = distance;
                    dir = 2;
                }
            }
            if(map.getTile(tileX, tileY+1).getType() != 1 && lastDir != 3){
                double distance = Math.sqrt(Math.pow((tileX)-targetTileX, 2) + Math.pow((tileY+1)-targetTileY, 2));
                if(distance < bestDistance){
                    nextTileX = tileX;
                    nextTileY = tileY+1;
                    bestDistance = distance;
                    dir = 1;
                }
            }
            if(map.getTile(tileX+1, tileY).getType() != 1 && lastDir != 2){
                double distance = Math.sqrt(Math.pow((tileX+1)-targetTileX, 2) + Math.pow(tileY-targetTileY, 2));
                if(distance < bestDistance){
                    nextTileX = tileX+1;
                    nextTileY = tileY;
                    dir = 0;
                }
            }
            newNextTile = false;
        }

        int nextTileCenterX = (int)((nextTileX+.5)*map.getTileSize());
        int nextTileCenterY = (int)((nextTileY+.5)*map.getTileSize());

        if(dir == 2 || dir == 0) {
            if (Math.abs(dstRect.centerX() - nextTileCenterX) < speed * dt) {
                x = nextTileCenterX-size/2;
                newNextTile = true;
            } else if (dstRect.centerX() < nextTileCenterX) {
                x += speed * dt;
            } else if (dstRect.centerX() > nextTileCenterX) {
                x -= speed * dt;
            }
        }
        else {
            if (Math.abs(dstRect.centerY() - nextTileCenterY) < speed * dt) {
                y = nextTileCenterY-size/2;
                newNextTile = true;
            } else if (dstRect.centerY() < nextTileCenterY) {
                y += speed * dt;
            } else if (dstRect.centerY() > nextTileCenterY) {
                y -= speed * dt;
            }
        }


        dstRect.offsetTo(x, y);
    }

    /*
     * Change position of srcRect depending on value of aniT
     * @param dt Amount of time since last update
     */
    private void animate(double dt){
        double aniInterval = .35;   //time between animation frames

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
        else{
            aniT = 0;
            srcRect.offsetTo(0, srcHeight * dir);
        }
    }

    /*
     * Draws the enemy to the screen
     * @param canvas The Canvas that everything is drawn to
     */
    public void draw(Canvas canvas, Map map){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        canvas.drawCircle((float)(nextTileX+.5)*map.getTileSize(), (float)(nextTileY+.5)*map.getTileSize(), size/2, paint);
        canvas.drawLine(x + size/2, y + size/2, (float)(nextTileX+.5)*map.getTileSize(), (float)(nextTileY+.5)*map.getTileSize(), paint);

        canvas.drawCircle((float)(targetTileX+.5)*map.getTileSize(), (float)(targetTileY+.5)*map.getTileSize(), size/2, paint);
        canvas.drawLine(x + size/2, y + size/2, (float)(targetTileX+.5)*map.getTileSize(), (float)(targetTileY+.5)*map.getTileSize(), paint);

        canvas.drawBitmap(e, srcRect, dstRect, null);
    }
}
