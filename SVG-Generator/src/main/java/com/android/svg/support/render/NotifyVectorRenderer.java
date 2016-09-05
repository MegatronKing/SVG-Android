package com.android.svg.support.render;

public abstract class NotifyVectorRenderer<T> extends CountableVectorRenderer<T> {

    private VectorRendererListener mVectorRendererListener;

    protected void notifyResult(String result) {
        if (mVectorRendererListener != null) {
            mVectorRendererListener.receiveResult(result);
        }
    }

    public void setVectorRendererListener(VectorRendererListener vectorRendererListener) {
        mVectorRendererListener = vectorRendererListener;
    }

    public static interface VectorRendererListener {

        void receiveResult(String result);

    }

}
