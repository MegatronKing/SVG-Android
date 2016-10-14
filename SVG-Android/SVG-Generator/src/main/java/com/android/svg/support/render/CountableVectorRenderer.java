package com.android.svg.support.render;

public abstract class CountableVectorRenderer<T> implements IVectorRenderer<T> {

    protected int mCount;

    @Override
    public void render(T t) {
        mCount++;
    }


}
