package com.lindsaykroeger.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Lindsay on 11/28/2016.
 */

public class Bullet extends GameObject {

    protected Bitmap bitmap;

    protected Vector2D offset;
    protected Paint paint;
    protected Vector2D forward;
    protected Vector2D position;
    public int width;
    public int height;


    public Bullet(Context context, String bitmapName, float x, float y) {
        forward = new Vector2D(new Random().nextFloat(), new Random().nextFloat()).normalize();
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        bitmap = Bitmap.createScaledBitmap(bmp, 75, 75, false);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        position = new Vector2D(x, y);
        offset = new Vector2D(1, 1);

    }

    @Override
    public void onUpdate() {
        float speed = 250.0f; //pixels per second
        position.y -= speed * GameView.DELTA_TIME;

    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, position.x - offset.x, position.y - offset.y, paint);
    }

}
