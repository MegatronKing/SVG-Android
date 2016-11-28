package com.android.svg.support;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;

/**
 * The renderer of {@link SVGDrawable}.
 *
 * @author Megatron King
 * @since 2016/9/1 21:48
 */

public abstract class SVGRenderer implements Cloneable {

    private Context mContext;

    // drawable configs
    protected float mAlpha;
    protected int mWidth;
    protected int mHeight;

    // matrix
    protected float mRotation = 0;
    protected float mPivotX = 0.5f;
    protected float mPivotY = 0.5f;
    protected float mScaleX = 1;
    protected float mScaleY = 1;
    protected float mTranslationX = 0;
    protected float mTranslationY = 0;

    protected final Matrix mFinalPathMatrix = new Matrix();

    protected final Path mPath;
    protected final Path mRenderPath;

    protected Paint mStrokePaint;
    protected Paint mFillPaint;

    protected PathMeasure mPathMeasure;

    private RendererStrategy mRendererStrategy;

    public SVGRenderer(Context context) {
        this.mRenderPath = new Path();
        this.mPath = new Path();
        this.mContext = context;
    }

    @Override
    public SVGRenderer clone() {
        SVGRenderer renderer = null;
        try {
            renderer = (SVGRenderer) super.clone();
            // there is a reference in strategy, so we set it null
            // and the renderer will create a new strategy when drawing.
            renderer.mRendererStrategy = null;
        } catch (CloneNotSupportedException e) {
            // it may not happen
        }
        return renderer;
    }

    protected int applyAlpha(int color, float alpha) {
        int alphaBytes = Color.alpha(color);
        color &= 0x00FFFFFF;
        color |= ((int) (alphaBytes * alpha)) << 24;
        return color;
    }

    protected int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void draw(Canvas canvas, int width, int height, ColorFilter filter, Rect dst) {
        canvas.rotate(mRotation, mWidth * mPivotX, mHeight * mPivotY);
        canvas.translate(mTranslationX, mTranslationY);
        canvas.scale(mScaleX, mScaleY, mPivotX * mWidth, mHeight * mPivotY);
        if (mRendererStrategy == null) {
            mRendererStrategy = RendererStrategyFactory.create(this, canvas);
        }
        mRendererStrategy.draw(canvas, width, height, filter, dst);
    }

    public abstract void render(Canvas canvas, int width, int height, ColorFilter filter);
}
