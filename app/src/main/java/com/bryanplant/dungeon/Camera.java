package com.bryanplant.dungeon;

/*
 * The Camera class receives information about the player and the map and
 * determines the x and y location of what part of the map should be drawn
 * to the screen.
 * @author Bryan Plant
 */

public class Camera {
    private int x = 0, y = 0; //x and y coordinates of camera

    /*
     * Updates x and y depending on where the player is on the map
     * @param player The Player object
     * @param map The Map object
     * @param screenWidth The width of the phone screen
     * @param screenHeight The height of the phone screen
     */
    public void update(Player player, Map map, int screenWidth, int screenHeight){
        x = (player.getX() + player.getSize() / 2) - screenWidth / 2;        //set x location of camera
        y = (player.getY() + player.getSize() / 2) - screenHeight / 2;       //set y location of camera

        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;

        if (x > map.getMapWidth()*map.getTileSize() - screenWidth)
            x = map.getMapWidth()*map.getTileSize() - screenWidth;
        if (y > map.getMapHeight()*map.getTileSize() - screenHeight)
            y = map.getMapHeight()*map.getTileSize() - screenHeight;
    }

    //returns x position of camera
    public int getX(){
        return x;
    }

    //returns y position of camera
    public int getY(){
        return y;
    }
}
