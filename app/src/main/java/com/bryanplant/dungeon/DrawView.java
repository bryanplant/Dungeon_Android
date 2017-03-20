package com.bryanplant.dungeon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

public class DrawView extends View {
    Player player = new Player();

    Paint paint = new Paint();

    public DrawView(Context context) {
        super(context);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap p = BitmapFactory.decodeResource(getResources(), R.drawable.player);

        canvas.drawRect(50, 0, 150, 100, null);

    }
}
