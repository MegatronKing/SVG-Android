package com.github.megatronking.svg.iconlibs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;

import com.github.megatronking.svg.support.SVGRenderer;

/**
 * AUTO-GENERATED FILE.  DO NOT MODIFY.
 * 
 * This class was automatically generated by the
 * SVG-Generator. It should not be modified by hand.
 */
public class ic_fiber_smart_record extends SVGRenderer {

    public ic_fiber_smart_record(Context context) {
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
        
        mPath.moveTo(9.0f, 12.0f);
        mPath.rMoveTo(-8.0f, 0.0f);
        mPath.cubicTo(1.0f, 9.879082f, 1.8434302f, 7.842861f, 3.3431458f, 6.343146f);
        mPath.cubicTo(4.842861f, 4.84343f, 6.8790817f, 4.0f, 9.0f, 4.0f);
        mPath.cubicTo(11.120918f, 4.0f, 13.157139f, 4.84343f, 14.656855f, 6.343146f);
        mPath.cubicTo(16.15657f, 7.842861f, 17.0f, 9.879082f, 17.0f, 12.0f);
        mPath.cubicTo(17.0f, 14.120918f, 16.15657f, 16.157139f, 14.656855f, 17.656855f);
        mPath.cubicTo(13.157139f, 19.15657f, 11.120918f, 20.0f, 9.0f, 20.0f);
        mPath.cubicTo(6.8790817f, 20.0f, 4.842861f, 19.15657f, 3.3431458f, 17.656855f);
        mPath.cubicTo(1.8434302f, 16.157139f, 1.0f, 14.120918f, 1.0f, 12.0f);
        
        mRenderPath.addPath(mPath, mFinalPathMatrix);
        if (mFillPaint == null) {
            mFillPaint = new Paint();
            mFillPaint.setStyle(Paint.Style.FILL);
            mFillPaint.setAntiAlias(true);
        }
        mFillPaint.setColor(applyAlpha(-16711423, 1.0f));
        mFillPaint.setColorFilter(filter);
        canvas.drawPath(mRenderPath, mFillPaint);
        mPath.reset();
        mRenderPath.reset();
        
        mFinalPathMatrix.setValues(new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f});
        mFinalPathMatrix.postScale(scaleX, scaleY);
        
        mPath.moveTo(17.0f, 4.26f);
        mPath.rLineTo(0f, 2.09f);
        mPath.rCubicTo(2.33f, 0.82f, 4.0f, 3.04f, 4.0f, 5.65f);
        mPath.rCubicTo(0.0f, 2.6099997f, -1.67f, 4.83f, -4.0f, 5.65f);
        mPath.rLineTo(0f, 2.09f);
        mPath.rCubicTo(3.45f, -0.89f, 6.0f, -4.01f, 6.0f, -7.74f);
        mPath.rCubicTo(0.0f, -3.7299995f, -2.55f, -6.85f, -6.0f, -7.74f);
        mPath.close();
        mPath.moveTo(17.0f, 4.26f);
        
        mRenderPath.addPath(mPath, mFinalPathMatrix);
        mFillPaint.setColor(applyAlpha(-16711423, 1.0f));
        mFillPaint.setColorFilter(filter);
        canvas.drawPath(mRenderPath, mFillPaint);

    }

}