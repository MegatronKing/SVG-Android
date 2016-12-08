package com.android.svg.iconlibs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;

import com.android.svg.support.SVGRenderer;

/**
 * AUTO-GENERATED FILE.  DO NOT MODIFY.
 * 
 * This class was automatically generated by the
 * SVG-Generator. It should not be modified by hand.
 */
public class ic_camera extends SVGRenderer {

    public ic_camera(Context context) {
        super(context);
        mAlpha = 1.0f;
        mWidth = dip2px(24.0f);
        mHeight = dip2px(24.0f);
    }

    @Override
    public void render(Canvas canvas, int w, int h, ColorFilter filter) {
        
        final float scaleX = w / 24.0f;
        final float scaleY = h / 24.0f;
        
        mPath.reset();
        mRenderPath.reset();
        
        mFinalPathMatrix.setValues(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f});
        mFinalPathMatrix.postScale(scaleX, scaleY);
        
        mPath.moveTo(9.4f, 10.5f);
        mPath.rLineTo(4.77f, -8.26f);
        mPath.cubicTo(13.47f, 2.09f, 12.75f, 2.0f, 12.0f, 2.0f);
        mPath.rCubicTo(-2.4f, 0.0f, -4.6f, 0.85f, -6.32f, 2.25f);
        mPath.rLineTo(3.66f, 6.35f);
        mPath.rLineTo(0.06f, -0.1f);
        mPath.close();
        mPath.moveTo(9.4f, 10.5f);
        mPath.moveTo(21.54f, 9.0f);
        mPath.rCubicTo(-0.92f, -2.92f, -3.15f, -5.26f, -6.0f, -6.34f);
        mPath.lineTo(11.88f, 9.0f);
        mPath.rLineTo(9.66f, 0f);
        mPath.close();
        mPath.moveTo(21.54f, 9.0f);
        mPath.rMoveTo(0.26f, 1.0f);
        mPath.rLineTo(-7.49f, 0f);
        mPath.rLineTo(0.29f, 0.5f);
        mPath.rLineTo(4.76f, 8.25f);
        mPath.cubicTo(21.0f, 16.97f, 22.0f, 14.61f, 22.0f, 12.0f);
        mPath.rCubicTo(0.0f, -0.69f, -0.07f, -1.35f, -0.2f, -2.0f);
        mPath.close();
        mPath.moveTo(21.800001f, 10.0f);
        mPath.moveTo(8.54f, 12.0f);
        mPath.rLineTo(-3.9f, -6.75f);
        mPath.cubicTo(3.01f, 7.03f, 2.0f, 9.39f, 2.0f, 12.0f);
        mPath.rCubicTo(0.0f, 0.69f, 0.07f, 1.35f, 0.2f, 2.0f);
        mPath.rLineTo(7.49f, 0f);
        mPath.rLineTo(-1.15f, -2.0f);
        mPath.close();
        mPath.moveTo(8.54f, 12.0f);
        mPath.rMoveTo(-6.08f, 3.0f);
        mPath.rCubicTo(0.92f, 2.92f, 3.15f, 5.26f, 6.0f, 6.34f);
        mPath.lineTo(12.12f, 15.0f);
        mPath.lineTo(2.46f, 15.0f);
        mPath.close();
        mPath.moveTo(2.46f, 15.0f);
        mPath.rMoveTo(11.27f, 0.0f);
        mPath.rLineTo(-3.9f, 6.76f);
        mPath.rCubicTo(0.7f, 0.15f, 1.42f, 0.24f, 2.17f, 0.24f);
        mPath.rCubicTo(2.4f, 0.0f, 4.6f, -0.85f, 6.32f, -2.25f);
        mPath.rLineTo(-3.66f, -6.35f);
        mPath.rLineTo(-0.93f, 1.6f);
        mPath.close();
        mPath.moveTo(13.7300005f, 15.0f);
        
        mRenderPath.addPath(mPath, mFinalPathMatrix);
        if (mFillPaint == null) {
            mFillPaint = new Paint();
            mFillPaint.setStyle(Paint.Style.FILL);
            mFillPaint.setAntiAlias(true);
        }
        mFillPaint.setColor(applyAlpha(-16777216, 1.0f));
        mFillPaint.setColorFilter(filter);
        canvas.drawPath(mRenderPath, mFillPaint);

    }

}