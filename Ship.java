package com.lindsaykroeger.mygame;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.io.InputStream;
import java.util.Random;

/**
 * Created by Lindsay on 11/28/2016.
 */

public class Ship extends GameObject {

    protected Bitmap bitmap;

    protected Vector2D offset;
    protected Paint paint;
    protected Vector2D forward;
    protected Vector2D position;
    public int width;
    public int height;


    public Ship(Context context, String bitmapName, float x, float y) {
        forward = new Vector2D(new Random().nextFloat(), new Random().nextFloat()).normalize();
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ship_transparent);
        bitmap = Bitmap.createScaledBitmap(bmp, 500, 500, false);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        position = new Vector2D(x, y);
        offset = new Vector2D(1, 1);

    }

    @Override
    public void onUpdate() {
//        float speed = 25.0f; //pixels per second
//        position.y -= speed * GameView.DELTA_TIME;

    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, position.x - offset.x, position.y - offset.y, paint);
    }

}
