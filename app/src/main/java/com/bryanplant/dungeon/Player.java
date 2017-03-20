package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Bryan on 3/13/2017.
 */
public class Player {
    Paint paint = new Paint();

    public Player(){

    }

    public void draw(Canvas canvas){
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        canvas.drawRect(50, 50, 100, 100, paint);
    }
}
