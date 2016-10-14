package com.android.svg.support.extend;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.android.svg.support.R;
import com.android.svg.support.SVGDrawable;

/**
 * Support width, height, alpha, tint color for svg images.<br>
 *
 * @author Megatron King
 * @since 2016/10/10 19:11
 */
public class SVGImageButton extends ImageButton {

    private ColorStateList mSvgColor;
    private float mSvgAlpha;
    private int mSvgWidth;
    private int mSvgHeight;

    public SVGImageButton(Context context) {
        this(context, null);
    }

    public SVGImageButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageButton(context, attrs);
    }

    public SVGImageButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageButton(context, attrs);
    }

    private void initImageButton(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SVGView);
        mSvgColor = a.getColorStateList(R.styleable.SVGView_svgColor);
        mSvgAlpha = a.getFloat(R.styleable.SVGView_svgAlpha, 1.0f);
        mSvgWidth = a.getDimensionPixelSize(R.styleable.SVGView_svgWidth, -1);
        mSvgHeight = a.getDimensionPixelSize(R.styleable.SVGView_svgHeight, -1);
        a.recycle();
        resetBackground();
        resetResourceDrawable();
    }

    public void setSvgColor(ColorStateList svgColor) {
        this.mSvgColor = svgColor;
        resetBackground();
        resetResourceDrawable();
    }

    public void setSvgColor(int color) {
        setSvgColor(ColorStateList.valueOf(color));
    }

    public void setSvgWidth(int width) {
        this.mSvgWidth = width;
        resetBackground();
        resetResourceDrawable();
    }

    public void setSvgHeight(int height) {
        this.mSvgHeight = height;
        resetBackground();
        resetResourceDrawable();
    }

    public void setSvgSize(int width, int height) {
        this.mSvgWidth = width;
        this.mSvgHeight = height;
        resetBackground();
        resetResourceDrawable();
    }

    public void setSvgAlpha(float alpha) {
        this.mSvgAlpha = alpha;
        resetBackground();
        resetResourceDrawable();
    }

    private void resetBackground() {
        Drawable drawable = getBackground();
        resetDrawable(drawable);
        super.setBackgroundDrawable(drawable);
    }

    private void resetResourceDrawable() {
        Drawable drawable = getDrawable();
        resetDrawable(drawable);
        super.setImageDrawable(drawable);
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
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        resetBackground();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        resetDrawable(drawable);
    }
}
