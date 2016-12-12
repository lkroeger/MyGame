package com.lindsaykroeger.mygame;

/**
 * Created by Lindsay on 11/28/2016.
 */

public class Vector2D {

    public float x;
    public float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D vector2D) {
        float x = this.x + vector2D.x;
        float y = this.y + vector2D.y;
        return new Vector2D(x, y);
    }

    public Vector2D subtract(Vector2D vector2D) {
        float x = this.x - vector2D.x;
        float y = this.y - vector2D.y;
        return new Vector2D(x, y);
    }

    public Vector2D multiply(float constant) {
        return new Vector2D(constant * x, constant * y);
    }

    public float getX(){

        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double dotProduct(Vector2D vector2D) {
        return vector2D.x * x + vector2D.y * y;
    }

    public float getLength() {
        return (float) Math.sqrt(dotProduct(this));
    }

    public Vector2D normalize() {
        float magnitude = getLength();
        if ( magnitude == 0 ) {
            magnitude = 1;
        }
        x = x / magnitude;
        y = y / magnitude;
        return this;
    }

}
