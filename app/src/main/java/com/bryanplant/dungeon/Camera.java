package com.bryanplant.dungeon;

public class Camera {
    private int x = 0, y = 0;

    public void update(Player player, Map map, int screenWidth, int screenHeight){
        x = -((player.getX() + player.getSize() / 2) - screenWidth / 2);
        y = -((player.getY() + player.getSize() / 2) - screenHeight / 2);

        if (x > 0)
            x = 0;
        if (y > 0)
            y = 0;

        if (x < -(map.getMapWidth()*map.getTileSize() - screenWidth))
            x = -(map.getMapWidth()*map.getTileSize() - screenWidth);
        if (y < -(map.getMapHeight()*map.getTileSize() - screenHeight))
            y = -(map.getMapHeight()*map.getTileSize() - screenHeight);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
