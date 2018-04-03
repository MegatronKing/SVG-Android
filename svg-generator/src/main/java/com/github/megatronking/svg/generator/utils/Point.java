package com.github.megatronking.svg.generator.utils;

/**
 * Point holds two float coordinates.
 *
 * @author Sundy
 * @since 18/4/3 17:49
 */

public class Point {

    public float x;
    public float y;

    public Point() {
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

}
