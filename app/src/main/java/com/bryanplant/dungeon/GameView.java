package com.bryanplant.dungeon;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bryanplant.characters.Enemy;
import com.bryanplant.characters.Player;

/**
 * Contains objects to run game
 * Updates and renders these objects
 * @author bryanplant
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = GameView.class.getSimpleName();

    private MainThread thread;
    private Player player;
    private Enemy enemy;
    private Map map;
    private Camera camera;
    private HUD hud;

    public GameView(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        camera = new Camera();
        hud = new HUD();
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player), getResources().getDisplayMetrics().widthPixels/16, getResources().getDisplayMetrics().widthPixels/16, getResources().getDisplayMetrics().widthPixels/16);
        enemy = new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy), getResources().getDisplayMetrics().widthPixels/16, getResources().getDisplayMetrics().widthPixels/16, getResources().getDisplayMetrics().widthPixels/16);

        try {
            map = new Map(getResources().getAssets().open("map1.txt"), getResources().getDisplayMetrics().widthPixels);
        }
        catch(Exception e){
            Log.d(TAG, "Unable to load map!");
        }


        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(!thread.isAlive())
            thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            player.handleInput((int)event.getX() + camera.getX(), (int)event.getY() + camera.getY());
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }

    public void render(Canvas canvas) {
        canvas.translate(-camera.getX(), -camera.getY());
        canvas.drawColor(Color.BLACK);
        map.draw(canvas);
        player.draw(canvas);
        enemy.draw(canvas);
        hud.draw(canvas, camera);
    }

    public void update(double dt){
        player.update(dt, map);
        enemy.update(dt, player, map);
        camera.update(player, map, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
    }

    public void pause(){
        thread.setRunning(false);
    }

    public void resume(){
        thread.setRunning(true);
    }
}
