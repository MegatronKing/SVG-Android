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

    protected final Matrix mGroupStackedMatrix = new Matrix();
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
        } catch (CloneNotSupportedException e) {
            // it may not happen
        }
        return renderer;
    }

    protected float getMatrixScale(Matrix groupStackedMatrix) {
        // Given unit vectors A = (0, 1) and B = (1, 0).
        // After matrix mapping, we got A' and B'. Let theta = the angel b/t A' and B'.
        // Therefore, the final scale we want is min(|A'| * sin(theta), |B'| * sin(theta)),
        // which is (|A'| * |B'| * sin(theta)) / max (|A'|, |B'|);
        // If  max (|A'|, |B'|) = 0, that means either x or y has a scale of 0.
        //
        // For non-skew case, which is most of the cases, matrix scale is computing exactly the
        // scale on x and y axis, and take the minimal of these two.
        // For skew case, an unit square will mapped to a parallelogram. And this function will
        // return the minimal height of the 2 bases.
        float[] unitVectors = new float[]{0, 1, 1, 0};
        groupStackedMatrix.mapVectors(unitVectors);
        float scaleX = (float) Math.hypot(unitVectors[0], unitVectors[1]);
        float scaleY = (float) Math.hypot(unitVectors[2], unitVectors[3]);
        float crossProduct = cross(unitVectors[0], unitVectors[1], unitVectors[2],
                unitVectors[3]);
        float maxScale = Math.max(scaleX, scaleY);

        float matrixScale = 0;
        if (maxScale > 0) {
            matrixScale = Math.abs(crossProduct) / maxScale;
        }
        return matrixScale;
    }

    private float cross(float v1x, float v1y, float v2x, float v2y) {
        return v1x * v2y - v1y * v2x;
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
        if (mRendererStrategy == null) {
            mRendererStrategy = RendererStrategyFactory.create(this, canvas);
        }
        mRendererStrategy.draw(canvas, width, height, filter, dst);
    }

    protected abstract void render(Canvas canvas, int width, int height, ColorFilter filter);
}
