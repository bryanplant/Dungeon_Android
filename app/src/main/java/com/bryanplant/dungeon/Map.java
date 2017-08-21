package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class Map {
    private Tile tile[][];
    private Random rand;
    private int mapWidth, mapHeight;
    private int tileSize;
    private Paint paint;

    public Map(InputStream is, int screenWidth, int screenHeight) throws IOException{
        rand = new Random();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String first = br.readLine();
        mapWidth = Integer.parseInt(first.substring(0, first.indexOf(' ')));
        mapHeight = Integer.parseInt(first.substring(first.indexOf(' ')+1, first.length()));
        tileSize = screenWidth/16;

        tile = new Tile[mapWidth][mapHeight];
        int type = 0;

        for(int j = 0; j < mapHeight; j++){
            String line = br.readLine();

            line = line.replaceAll("\\s", "");
            for(int i = 0; i < mapWidth; i++){
                switch(line.charAt(i)){
                    case '0':
                        type = 0;
                        break;
                    case '1':
                        type = 1;
                        break;
                    case '2':
                        type = 2;
                        break;
                }
                tile[i][j] = new Tile(i, j, tileSize, type);
            }
        }
        is.close();
        br.close();
    }

    public void draw(Canvas canvas){
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j < mapHeight; j++){
                tile[i][j].draw(canvas); //draw the tile
            }
        }

        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j < mapHeight; j++) {
                if(tile[i][j].getType() == 1) { //check if the tile is a wall
                    //draw line to left of wall
                    if(i != 0) {
                        if(tile[i - 1][j].getType() == 0) {
                            canvas.drawLine(i*tileSize, j*tileSize, i*tileSize, (j+1)*tileSize, paint);
                        }
                    }

                    //draw line above wall
                    if(j != 0) {
                        if(tile[i][j-1].getType() == 0){
                            canvas.drawLine(i*tileSize-5, j*tileSize, (i+1)*tileSize, j*tileSize, paint);
                        }
                    }

                    //draw line to right of wall
                    if(i != mapWidth-1){
                        if(tile[i+1][j].getType() == 0){
                            canvas.drawLine((i+1)*tileSize, j*tileSize-5, (i+1)*tileSize, (j+1)*tileSize, paint);
                        }
                    }

                    //draw line below wall
                    if(j != mapHeight-1){
                        if(tile[i][j+1].getType() == 0){
                            canvas.drawLine(i*tileSize-5, (j+1)*tileSize, (i+1)*tileSize+5, (j+1)*tileSize, paint);
                        }
                    }
                }
            }
        }

        //draw lines around outside of map
        /*paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        canvas.drawLine(0, 0, 0, mapHeight*tileSize, paint);
        canvas.drawLine(0, 0, mapWidth*tileSize, 0, paint);
        canvas.drawLine(0, mapHeight*tileSize, mapWidth*tileSize, mapHeight*tileSize, paint);
        canvas.drawLine(mapWidth*tileSize, 0, mapWidth*tileSize, mapHeight*tileSize, paint);*/
    }

    public int getMapWidth(){
        return mapWidth;
    }

    public int getMapHeight(){
        return mapHeight;
    }

    public int getTileSize(){
        return tileSize;
    }

    public Tile getTile(int x, int y){
        return tile[x][y];
    }
}
