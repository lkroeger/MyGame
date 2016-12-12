package com.lindsaykroeger.mygame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Lindsay on 11/28/2016.
 */

public class StarField extends GameObject {

    protected Vector2D[] points;
    protected int numberOfPoints;
    protected Paint paint;
    protected float currentAlpha = 0;
    protected float twinkleSpeed = 20.0f;
    protected int direction = 1;
    protected int red;
    protected int green;
    protected int blue;
    int[] directions;
    float[] currentAlphas;

    public StarField(int numberOfPoints, float twinkleSpeed, int red, int green, int blue) {
        this.twinkleSpeed = twinkleSpeed;
        this.numberOfPoints = numberOfPoints;
        this.red = red;
        this.green = green;
        this.blue = blue;
        paint = new Paint();
        paint.setColor(Color.argb((int) currentAlpha, red, green, blue));
        points = new Vector2D[numberOfPoints];
        Random random = new Random();
        for (int i = 0; i < numberOfPoints; i++) {
            points[i] = new Vector2D(random.nextInt(GameView.getScreenWidth()),
                    random.nextInt(GameView.getScreenHeight()));
        }

        directions = new int[numberOfPoints];
        for(int i = 0; i < numberOfPoints; i++) {
            directions[i] = 1;
        }

        currentAlphas = new float[numberOfPoints];
        for(int i = 0; i < numberOfPoints; i++) {
            currentAlphas[i] = 0;
        }
    }

    @Override
    public void onUpdate() {
        calculateAlpha();
    }

    public void calculateAlpha(){
        if(direction > 0) {
            currentAlpha = (currentAlpha + twinkleSpeed * GameView.DELTA_TIME * direction);
            if(currentAlpha > 255){
                currentAlpha = 255;
                direction *= -1;
            }
        }
        else{
            currentAlpha = (currentAlpha + twinkleSpeed * GameView.DELTA_TIME * direction);
            if(currentAlpha < 0){
                currentAlpha = 0;
                direction *= -1;
            }
        }
    }

    public void calculateAlphas(){

        for(int i = 0; i < numberOfPoints; i++){
            if (directions[i] > 0) {
                currentAlphas[i] = (currentAlphas[i] + twinkleSpeed * GameView.DELTA_TIME * directions[i]);
                if (currentAlphas[i] >= 255) {
                    currentAlphas[i] = 255;
                    directions[i] *= -1;
                }
            } else {
                currentAlphas[i] = (currentAlphas[i] + twinkleSpeed * GameView.DELTA_TIME * directions[i]);
                if (currentAlphas[i] <= 0) {
                    currentAlphas[i] = 0;
                    directions[i] *= -1;
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        paint.setColor(Color.argb((int)currentAlpha, red, green, blue));
        for(int i = 0 ; i < numberOfPoints; i++){
            canvas.drawPoint(
                    points[i].getX(), points[i].getY(), paint);
        }
    }

//    public void setScreenWidth(int width) {
//        SCREEN_WIDTH = width;
//    }
//
//    public void setScreenHeight(int height) {
//        SCREEN_HEIGHT = height;
//    }
}
