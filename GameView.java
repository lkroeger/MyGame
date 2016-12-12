package com.lindsaykroeger.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Lindsay on 11/28/2016.
 */

public class GameView extends SurfaceView implements Runnable, View.OnTouchListener{

    protected volatile Boolean isPlaying;
    Thread gameThread = null;
    SurfaceHolder surfaceHolder;
    public static float DELTA_TIME;
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    static int SCREEN_WIDTH;
    static int SCREEN_HEIGHT;
    Context context;

    public SoundManager soundManager;

    Ship ship;
    BeeEnemy enemy;
    Bullet bullet;
    private List<Bullet> bullets = new ArrayList<Bullet>();
    private List<BeeEnemy> enemies = new ArrayList<BeeEnemy>();
    private List<BeeEnemy> destroyedEnemies = new ArrayList<>();
    int count = 0;
    int score = 0;
    boolean playing = true;
    StarField starField;
    private static final int MAX_CLICK_DURATION = 200;
    private long startClickTime;

    public GameView(Context context, int width, int height) {
        super(context);
        soundManager = new SoundManager();
        soundManager.loadSounds(context);
        this.context = context;
        surfaceHolder = getHolder();
        this.setOnTouchListener(this);
        setScreenWidth(width);
        setScreenHeight(height);
        int x = SCREEN_WIDTH/3;
        int y = SCREEN_HEIGHT - SCREEN_HEIGHT/3;
        ship = new Ship(context, "ship", x, y, ((GameActivity)context).width, ((GameActivity)context).height);
        gameObjects.add(ship);
        starField = new StarField(500, 100, 255, 100, 0);
        gameObjects.add(starField);
        starField = new StarField(750, 200, 0, 175, 255);
        gameObjects.add(starField);
        starField = new StarField(250, 150, 255, 255, 255);
        gameObjects.add(starField);
    }

    @Override
    public void run() {

        long previousTimeMillis;
        long currentTimeMillis;
        previousTimeMillis = System.currentTimeMillis();

        soundManager.playSound("galaga_theme_cut.mp3", 0);

        while(isPlaying) {
            currentTimeMillis = System.currentTimeMillis();
            DELTA_TIME = (currentTimeMillis - previousTimeMillis) / 1000.0f;
            update();
            draw();
            previousTimeMillis = currentTimeMillis;
        }
    }

    protected void update() {
        for(int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).onUpdate();
        }

        Random random = new Random();
        count++;

        if(playing = true){
            if(count == 100){
                enemy = new BeeEnemy(context, "beeEnemy", random.nextInt(SCREEN_WIDTH - 100), 0, random.nextInt(300) + ((GameActivity)context).enemySpeed, ((GameActivity)context).width, ((GameActivity)context).height);
                enemies.add(enemy);
                gameObjects.add(enemy);
                count = 0;
            }
        }

        collision();

    }

    protected void draw() {
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();

            canvas.drawRGB(0, 0, 0);

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(25.0f);
            paint.setTextSize(100.0f);
            canvas.drawText(String.valueOf(score), SCREEN_WIDTH/2, 100, paint);

            for(int i = 0; i < gameObjects.size(); i++) {
                gameObjects.get(i).onDraw(canvas);
            }

            if(isPlaying == false){
                paint.setStrokeWidth(25.0f);
                paint.setTextSize(SCREEN_WIDTH/12);
                canvas.drawText("Game Over!", SCREEN_WIDTH/3, SCREEN_HEIGHT/3, paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        isPlaying = false;
        try{
            gameThread.join();
        } catch(InterruptedException e){

        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void collision() {

        for(int k = 0; k < bullets.size(); k++) {
            if(bullets.get(k).position.y <= 0) {
                bullets.remove(k);
            }
        }

        for(int j = 0; j < enemies.size(); j++) {
            BeeEnemy tempEnemy = enemies.get(j);

            int enemyCenterX = (int)tempEnemy.position.x + (tempEnemy.width/2);

            if(tempEnemy.position.y >= SCREEN_HEIGHT - ship.height) {
                isPlaying = false;
                soundManager.playSound("death_violin.mp3", 0);
                ((GameActivity)context).endGame(score);
            }

            if(tempEnemy.position.y >= SCREEN_HEIGHT - ship.height){
                if(enemyCenterX <= ship.position.x + ship.width && enemyCenterX >= ship.position.x) {
                    isPlaying = false;
                }
                gameObjects.remove(tempEnemy);
                enemies.remove(tempEnemy);
            }

            for(int i = 0; i < bullets.size(); i++) {
                Bullet tempBullet = bullets.get(i);

                if(tempEnemy != null && tempBullet != null){

                    int bulletCenterX = (int)tempBullet.position.x + (tempBullet.width/2);
                    int bulletCenterY = (int)tempBullet.position.y + (tempBullet.height/2);

                    if((bulletCenterX <= tempEnemy.position.x + tempEnemy.width && bulletCenterX >= tempEnemy.position.x)
                            && (bulletCenterY <= tempEnemy.position.y + tempEnemy.height && bulletCenterY >= tempEnemy.position.y)) {

                        destroyedEnemies.add(tempEnemy);
                        if(destroyedEnemies.size() == 5000){
                            isPlaying = false;
                            soundManager.playSound("death_violin.mp3", 0);
                        }

                        gameObjects.remove(tempEnemy);
                        gameObjects.remove(tempBullet);
                        enemies.remove(tempEnemy);
                        bullets.remove(tempBullet);
                        score++;
                        soundManager.playSound("explosion_cut.mp3", 0);
                    }
                }

            }

        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startClickTime = Calendar.getInstance().getTimeInMillis();
                break;
            case MotionEvent.ACTION_UP:
                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if(clickDuration < MAX_CLICK_DURATION && bullets.size() < ((GameActivity)context).numBullets) {
                    //click event has occurred
                    bullet = new Bullet(context, "bullet", ship.position.x + ship.width/2, ship.position.y);
                    bullets.add(bullet);
                    gameObjects.add(bullet);
                }
            case MotionEvent.ACTION_MOVE:
                int shipx1 = (int)ship.position.x + ship.width;
                int shipx2 = (int)ship.position.x;
                if(event.getX() <= shipx1 && event.getX() >= shipx2) {
                    ship.position.x = event.getX() - ship.width/2;
                }
            default:
                break;
        }
        return true;
    }

    public void setScreenWidth(int width) {
        SCREEN_WIDTH = width;
    }

    public void setScreenHeight(int height) {
        SCREEN_HEIGHT = height;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }



}
