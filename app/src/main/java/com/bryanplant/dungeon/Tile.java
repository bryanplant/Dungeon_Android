package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Contains information about a tile object
 * @author bryanplant
 */
class Tile {
    private Rect rect;          //Rectangle of tile
    private int type, color;    //The type and color of the tile
    private int size;

    /*
     * Initializes a Tile
     * @param x X coordinate of tile
     * @param y Y coordinate of tile
     * @param size The size of the tile in pixels
     * @param type The type of the tile (wall, floor etc.)
     */
    public Tile(int x, int y, int size, int type){
        this.type = type;
        this.size = size;
        rect = new Rect(x*size, y*size, x*size+size, y*size+size);

        switch(type){
            case 0:
                color = Color.argb(255, 60, 105, 112);
                break;
            case 1:
                color = Color.BLACK;
                break;
            case 2:
                color = Color.argb(255, 60, 105, 112);
                break;
            case 3:
                color = Color.argb(255, 60, 105, 112);
                break;
        }
    }

    /*
     *  Draw the tile to the canvas
     *  @param canvas The Canvas to be drawn to
     */
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(color);
        canvas.drawRect(rect, paint);
        if(type == 3){
            paint.setColor(Color.YELLOW);
            canvas.drawCircle(rect.centerX(), rect.centerY(), size/8, paint);
        }
    }

    //Returns the tile's rectangle
    public Rect getRect(){
        return rect;
    }

    //Returns the tile's type
    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }
}
