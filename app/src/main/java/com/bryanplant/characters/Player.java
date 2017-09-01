package com.bryanplant.characters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.bryanplant.dungeon.Map;

/**
 * The Player class stores information about an player character
 * and handles everything required to animate the character and update
 * the character's position and actions according to user input
 * @author bryanplant
 */

public class Player{

    private Bitmap p;                               //contains player sprite
    private int x, y;                               //player x and y coordinates
    private int dir;                                //direction player is facing
    private int srcWidth, srcHeight;                //dimensions of player sprite on bitmap
    private int size;                               //dimensions of player sprite to be drawn to the screen
    private double aniT = 0;                        //animation timer
    private double aniInterval = .35;               //time between animation frames
    private double speed = 500;                     //speed at which player moves
    private Rect srcRect, dstRect, moveRect;        //sprite on bitmap, sprite on screen, where the sprite is moving to
    private boolean moving = false;                 //whether or not player is moving

    /*
     * Initializes Player
     * @param p Bitmap containing player sprite
     * @param x Starting x coordinate of Player
     * @param y Starting y coordinate of Player
     * @param size Width and height of sprite drawn to screen
     */
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

    /*
     * Receives input and controls character accordingly
     * @param targetX X location where character is to move to
     * @param targetY Y location where character is to move to
     */
    public void handleInput(int targetX, int targetY){
            moveRect.offsetTo(targetX-size/2, targetY-size/2);
            moving = true;
    }

    /*
     * Calls functions to update the player's location, actions, and animation
     * @param dt Amount of time since last update
     * @param map The Map object
     */
    public void update(double dt, Map map){
        move(dt, map);
        animate(dt);
    }

    /*
     * Moves player towards and target location while checking to ensure that
     * the player is not colliding with the environment
     * @param dt Amount of time since last update
     * @param Map The Map object
     */
    private void move(double dt, Map map){
        int dstX = moveRect.left;
        int dstY = moveRect.top;
        int nextX = x, nextY = y;
        Rect nextRect;  //next location of player sprite
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

    /*
     * Change position of srcRect depending on value of aniT
     * @param dt Amount of time since last update
     */
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

    /*
     * Draw player character and movement indicator to screen
     * @param canvas The Canvas that everything is drawn to
     */
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

    //return x position of player
    public int getX(){
        return x;
    }

    //return y position of player
    public int getY(){
        return y;
    }

    //return size of player
    public int getSize(){
        return size;
    }
}
