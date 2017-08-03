package com.bryanplant.dungeon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.Buffer;
import java.util.Random;

/**
 * Created by Bryan on 6/16/2017.
 */

public class Map {
    Tile tile[][];
    Random rand;
    int screenWidth, screenHeight;
    int mapWidth, mapHeight;

    public Map(InputStream is, int screenWidth, int screenHeight) throws IOException{
        rand = new Random();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String first = br.readLine();
        mapWidth = Integer.parseInt(first.substring(0, first.indexOf(' ')));
        mapHeight = Integer.parseInt(first.substring(first.indexOf(' ')+1, first.length()));

        tile = new Tile[mapWidth][mapHeight];
        int color = 0;

        for(int j = 0; j < mapHeight; j++){
            String line = br.readLine();

            line = line.replaceAll("\\s", "");
            for(int i = 0; i < mapWidth; i++){
                switch(line.charAt(i)){
                    case '0':
                        color = Color.BLUE;
                        break;
                    case '1':
                        color = Color.CYAN;
                        break;
                    case '2':
                        color = Color.GREEN;
                        break;
                }
                tile[i][j] = new Tile(i, j, screenWidth/16, screenWidth/16, color);
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
}
