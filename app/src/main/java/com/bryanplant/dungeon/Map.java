package com.bryanplant.dungeon;

import android.graphics.Canvas;

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
                tile[i][j] = new Tile(i, j, tileSize, tileSize, type);
            }
        }
        is.close();
        br.close();
    }

    public void draw(Canvas canvas){
        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j < mapHeight; j++){
                tile[i][j].draw(canvas);
            }
        }
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
