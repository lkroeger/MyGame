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

public class Ship extends GameObject {

    protected Bitmap bitmap;
    protected Vector2D offset;
    protected Paint paint;
    protected Vector2D forward;
    protected Vector2D position;
    public int width;
    public int height;


    public Ship(Context context, String bitmapName, float x, float y, int screenWidth, int screenHeight) {
        forward = new Vector2D(new Random().nextFloat(), new Random().nextFloat()).normalize();
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ship_transparent);
        bitmap = Bitmap.createScaledBitmap(bmp, screenWidth/3, screenWidth/3, false);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        position = new Vector2D(x, screenHeight - height);
        offset = new Vector2D(1, 1);

    }

    @Override
    public void onUpdate() {
        //do nothing --> will move upon user motion event Action_Move
    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, position.x - offset.x, position.y - offset.y, paint);
    }

}
