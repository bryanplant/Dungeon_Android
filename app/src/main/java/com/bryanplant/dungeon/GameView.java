package com.bryanplant.dungeon;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Contains objects to run game
 * Updates and renders these objects
 * @author bryanplant
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = GameView.class.getSimpleName();

    private MainThread thread;
    private Player player;
    private EnemyA enemyA;
    private EnemyB enemyB;
    private EnemyC enemyC;
    private Map map;
    private Camera camera;
    private HUD hud;

    private double fpsAverage;

    public GameView(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        camera = new Camera();
        hud = new HUD();

        try {
            map = new Map(getResources().getAssets().open("map1.txt"), getResources().getDisplayMetrics().widthPixels);
        }
        catch(Exception e){
            Log.d(TAG, "Unable to load map!");
        }

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inScaled = false;

        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player, o), map.getPlayerStartX(), map.getPlayerStartY(), getResources().getDisplayMetrics().widthPixels/16);
        enemyA = new EnemyA(BitmapFactory.decodeResource(getResources(), R.drawable.enemy, o), getResources().getDisplayMetrics().widthPixels/8, getResources().getDisplayMetrics().widthPixels/8, getResources().getDisplayMetrics().widthPixels/16);
        enemyB = new EnemyB(BitmapFactory.decodeResource(getResources(), R.drawable.enemy, o), getResources().getDisplayMetrics().widthPixels/8, getResources().getDisplayMetrics().widthPixels/8, getResources().getDisplayMetrics().widthPixels/16);
        enemyC = new EnemyC(BitmapFactory.decodeResource(getResources(), R.drawable.enemy, o), getResources().getDisplayMetrics().widthPixels/8, getResources().getDisplayMetrics().widthPixels/8, getResources().getDisplayMetrics().widthPixels/16);


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
        enemyA.draw(canvas, map);
        enemyB.draw(canvas, map);
        enemyC.draw(canvas, map);
        hud.draw(canvas, camera, player, fpsAverage, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
    }

    public void update(double dt){
        player.update(dt, map);
        enemyA.update(dt, player, map);
        enemyB.update(dt, player, map);
        enemyC.update(dt, player, enemyA, map);
        camera.update(player, map, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
    }

    public void pause(){
        thread.setRunning(false);
    }

    public void resume(){
        thread.setRunning(true);
    }

    public void setFpsAverage(double fps){
        fpsAverage = fps;
    }
}
