package com.bryanplant.dungeon;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Bryan on 9/4/2017.
 */

public class EnemyB extends Enemy {
    public EnemyB(Bitmap e, int x, int y, int size) {
        super(e, x, y, size);
        color = Color.argb(255, 112, 78, 112);
    }

    @Override
    public void update(double dt, Player player, Map map){
        setTarget(player);
        super.update(dt, player, map);
    }
    private void setTarget(Player player){
        switch(player.getDir()){
            case 0:
                targetTileX = player.getTileX()+4;
                targetTileY = player.getTileY();
                break;
            case 1:
                targetTileX = player.getTileX();
                targetTileY = player.getTileY()+4;
                break;
            case 2:
                targetTileX = player.getTileX()-4;
                targetTileY = player.getTileY();
                break;
            case 3:
                targetTileX = player.getTileX();
                targetTileY = player.getTileY()-4;
                break;
        }
    }
}
