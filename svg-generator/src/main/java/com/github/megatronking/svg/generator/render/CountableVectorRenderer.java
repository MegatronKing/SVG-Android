package com.github.megatronking.svg.generator.render;

public abstract class CountableVectorRenderer<T> implements IVectorRenderer<T> {

    protected int mCount;

    @Override
    public void render(T t) {
        mCount++;
    }


}
