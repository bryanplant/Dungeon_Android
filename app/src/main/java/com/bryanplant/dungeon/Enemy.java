package com.bryanplant.dungeon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * The Enemy class stores information about an enemy character
 * and handles everything required to animate the character and update
 * the character's position and actions
 * @author bryanplant
 */
public class Enemy {

    private int x, y;                   //x and y location of enemy
    private int dir;                    //direction the enemy is facing
    private int srcWidth, srcHeight;    //dimensions of enemy sprite on source png
    private int size;                   //width and height of sprite to be drawn to the screen
    private boolean moving;             //if the enemy is moving or not
    private Bitmap e;                   //stores enemy sprite
    private Rect srcRect, dstRect, moveRect;  //sprite on bitmap, sprite on screen, where the sprite is moving to
    private double aniT = 0;            //animation timer

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
        moveRect = new Rect(x, y, x+size, y+size);
        dir = 0;
        srcWidth = e.getWidth()/3;
        srcHeight = e.getHeight()/4;
        srcRect = new Rect(0, 0, srcWidth, srcHeight);
        dstRect = new Rect(x, y, x+size, y+size);
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
        int dstX = player.getX();
        int dstY = player.getY();
        int nextX = x, nextY = y;
        Rect nextRect;
        double speed = 2*size;         //speed at which the enemy moves

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
    public void draw(Canvas canvas){
        canvas.drawBitmap(e, srcRect, dstRect, null);
    }
}
