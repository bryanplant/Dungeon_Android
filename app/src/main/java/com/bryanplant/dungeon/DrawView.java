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

    Bitmap p = BitmapFactory.decodeResource(getResources(), R.drawable.player);
    Rect src = new Rect(0, 0, 160, 160);
    Rect dst = new Rect(100, 100, 500, 500);


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(p, src, dst, null);

        paint.setColor(Color.GREEN);
        paint.setTextSize(24);
        canvas.drawRect(50, 0, 150, 100, paint);
        canvas.drawText(Integer.toString(p.getWidth()), 300, 50, paint);

    }
}
