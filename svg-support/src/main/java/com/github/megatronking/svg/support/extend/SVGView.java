package com.github.megatronking.svg.support.extend;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.github.megatronking.svg.support.R;
import com.github.megatronking.svg.support.SVGDrawable;

/**
 * Support alpha, tint color for svg images.<br>
 *
 * @author Megatron King
 * @since 2016/10/10 19:11
 */
public class SVGView extends View {

    private ColorStateList mSvgColor;
    private float mSvgAlpha;
    private int mSvgWidth;
    private int mSvgHeight;
    private float mSvgRotation;

    public SVGView(Context context) {
        this(context, null);
    }

    public SVGView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SVGView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SVGView);
        mSvgColor = a.getColorStateList(R.styleable.SVGView_svgColor);
        mSvgAlpha = a.getFloat(R.styleable.SVGView_svgAlpha, 1.0f);
        mSvgWidth = a.getDimensionPixelSize(R.styleable.SVGView_svgWidth, -1);
        mSvgHeight = a.getDimensionPixelSize(R.styleable.SVGView_svgHeight, -1);
        mSvgRotation = a.getFloat(R.styleable.SVGView_svgRotation, 0) % 360;
        a.recycle();
        resetBackgroundDrawable();
    }

    public void setSvgColor(ColorStateList svgColor) {
        this.mSvgColor = svgColor;
        resetBackgroundDrawable();
    }

    public void setSvgColor(int color) {
        setSvgColor(ColorStateList.valueOf(color));
    }

    public ColorStateList getSvgColor() {
        return mSvgColor;
    }

    public void setSvgWidth(int width) {
        this.mSvgWidth = width;
        resetBackgroundDrawable();
    }

    public int getSvgWidth() {
        return mSvgWidth;
    }

    public void setSvgHeight(int height) {
        this.mSvgHeight = height;
        resetBackgroundDrawable();
    }

    public int getSvgHeight() {
        return mSvgHeight;
    }

    public void setSvgSize(int width, int height) {
        this.mSvgWidth = width;
        this.mSvgHeight = height;
        resetBackgroundDrawable();
    }

    public void setSvgAlpha(float alpha) {
        this.mSvgAlpha = alpha;
        resetBackgroundDrawable();
    }

    public float getSvgAlpha() {
        return mSvgAlpha;
    }

    public void setSvgRotation(float rotation) {
        this.mSvgRotation = rotation;
        resetBackgroundDrawable();
    }

    public float getSvgRotation() {
        return mSvgRotation;
    }

    private void resetBackgroundDrawable() {
        resetDrawable(getBackground());
        invalidate();
    }

    private void resetDrawable(Drawable drawable) {
        if (drawable != null && drawable instanceof SVGDrawable) {
            drawable.mutate();
            ((SVGDrawable)drawable).setTintList(mSvgColor);
            if (mSvgAlpha > 0 && mSvgAlpha <= 1.0f) {
                ((SVGDrawable)drawable).setAlpha((int) (mSvgAlpha * 0xFF));
            }
            if (mSvgWidth > 0) {
                ((SVGDrawable)drawable).setWidth(mSvgWidth);
            }
            if (mSvgHeight > 0) {
                ((SVGDrawable)drawable).setHeight(mSvgHeight);
            }
            if (mSvgRotation != 0) {
                ((SVGDrawable)drawable).setRotation(mSvgRotation);
            }
        }
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        resetBackgroundDrawable();
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        resetBackgroundDrawable();
    }
}
