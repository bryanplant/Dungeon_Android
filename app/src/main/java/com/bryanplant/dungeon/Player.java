package com.bryanplant.dungeon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.R;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Bryan on 3/13/2017.
 */
public class Player{
    private static final String TAG = GameView.class.getSimpleName();

    private Paint paint = new Paint();
    private Rect src, dst;
    private Bitmap p;
    private int x, y, dir, srcWidth, srcHeight, width = 200, height = 200;
    private float aniT = 0;
    private float aniInterval = .4f;

    public Player(Bitmap p, int x, int y){
        this.p = p;
        this.x = x;
        this.y = y;
        dir = 0;
        srcWidth = p.getWidth()/3;
        srcHeight = p.getHeight()/4;
        src = new Rect(0, 0, srcWidth, srcHeight);
        dst = new Rect(x, y, x+width, y+height);
    }

    public void handleInput(int x, int y){
        this.x = x-width/2;
        this.y = y-height/2;
    }

    public void update(float dt){
        dst.set(x, y, x+width, y+width);
        animate(dt);
    }

    public void animate(float dt){
        aniT += dt;

        if(aniT < aniInterval){
            src.offsetTo(0, srcHeight*dir);
        }
        else if(aniT >= aniInterval && aniT < aniInterval*2) {
            src.offsetTo(srcWidth, srcHeight*dir);
        }
        else if(aniT >= aniInterval*2 && aniT < aniInterval*3) {
            src.offsetTo(srcWidth*2, srcHeight*dir);
        }
        else if(aniT >= aniInterval*3){
            aniT = 0;
            if(dir < 3)
                dir++;
            else
                dir = 0;
        }
    }

    public void draw(Canvas canvas){
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        canvas.drawBitmap(p, src, dst, null);
    }
}
