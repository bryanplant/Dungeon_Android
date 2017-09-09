package com.bryanplant.dungeon;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Bryan on 9/6/2017.
 */

public class EnemyC extends Enemy{
    public EnemyC(Bitmap e, int x, int y, int size) {
        super(e, x, y, size);
        color = Color.argb(255, 68, 207, 235);
    }

    public void update(double dt, Player player, EnemyA enemyA, Map map){
        setTarget(player, enemyA);
        super.update(dt, player, map);
    }
    private void setTarget(Player player, EnemyA enemyA){
        int tempTargetX = 0;
        int tempTargetY = 0;
        switch(player.getDir()){
            case 0:
                tempTargetX = player.getTileX()+2;
                tempTargetY = player.getTileY();
                break;
            case 1:
                tempTargetX = player.getTileX();
                tempTargetY = player.getTileY()+2;
                break;
            case 2:
                tempTargetX = player.getTileX()-2;
                tempTargetY = player.getTileY();
                break;
            case 3:
                tempTargetX = player.getTileX();
                tempTargetY = player.getTileY()-2;
                break;
        }
        targetTileX = (tempTargetX-enemyA.getTileX())*2 + player.getTileX();
        targetTileY = (tempTargetY-enemyA.getTileY())*2 + player.getTileY();
    }
}
