package com.bryanplant.dungeon;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Bryan on 9/4/2017.
 */

public class EnemyA extends Enemy {
    public EnemyA(Bitmap e, int x, int y, int size){
        super(e, x, y, size);
        color = Color.RED;
    }

    @Override
    public void update(double dt, Player player, Map map){
        setTarget(player);
        super.update(dt, player, map);
    }
    private void setTarget(Player player){
        targetTileX = player.getTileX();
        targetTileY = player.getTileY();
    }
}
