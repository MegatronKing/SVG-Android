package com.android.svg.support.extend;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;

import com.android.svg.support.R;
import com.android.svg.support.SVGDrawable;

/**
 * Support width, height, alpha, tint color for svg images.<br>
 *
 * @author Megatron King
 * @since 2016/10/10 19:11
 */
public class SVGButton extends Button {

    private CompoundSVGParameter[] mCompoundSVGParameters;

    public SVGButton(Context context) {
        this(context, null);
    }

    public SVGButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initButton(context, attrs);
    }

    public SVGButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initButton(context, attrs);
    }

    private void initButton(Context context, AttributeSet attrs) {
        mCompoundSVGParameters = new CompoundSVGParameter[] {
                new CompoundSVGParameter(),
                new CompoundSVGParameter(),
                new CompoundSVGParameter(),
                new CompoundSVGParameter()
        };

        TypedArray svgViewTA = context.obtainStyledAttributes(attrs, R.styleable.SVGView);
        ColorStateList svgColor = svgViewTA.getColorStateList(R.styleable.SVGView_svgColor);
        float svgAlpha = svgViewTA.getFloat(R.styleable.SVGView_svgAlpha, 1.0f);
        int svgWidth = svgViewTA.getDimensionPixelSize(R.styleable.SVGView_svgWidth, -1);
        int svgHeight = svgViewTA.getDimensionPixelSize(R.styleable.SVGView_svgHeight, -1);
        float svgRotation = svgViewTA.getFloat(R.styleable.SVGView_svgRotation, 0);
        svgViewTA.recycle();

        // compounds
        TypedArray svgCompoundViewTa = context.obtainStyledAttributes(attrs, R.styleable.SVGCompoundView);
        mCompoundSVGParameters[0].svgColor = svgCompoundViewTa.hasValue(R.styleable.SVGCompoundView_leftSvgColor) ?
                svgCompoundViewTa.getColorStateList(R.styleable.SVGCompoundView_leftSvgColor) : svgColor;
        mCompoundSVGParameters[1].svgColor = svgCompoundViewTa.hasValue(R.styleable.SVGCompoundView_topSvgColor) ?
                svgCompoundViewTa.getColorStateList(R.styleable.SVGCompoundView_topSvgColor) : svgColor;
        mCompoundSVGParameters[2].svgColor = svgCompoundViewTa.hasValue(R.styleable.SVGCompoundView_rightSvgColor) ?
                svgCompoundViewTa.getColorStateList(R.styleable.SVGCompoundView_rightSvgColor) : svgColor;
        mCompoundSVGParameters[3].svgColor = svgCompoundViewTa.hasValue(R.styleable.SVGCompoundView_bottomSvgColor) ?
                svgCompoundViewTa.getColorStateList(R.styleable.SVGCompoundView_bottomSvgColor) : svgColor;
        mCompoundSVGParameters[0].svgAlpha = svgCompoundViewTa.getFloat(R.styleable.SVGCompoundView_leftSvgAlpha, svgAlpha);
        mCompoundSVGParameters[1].svgAlpha = svgCompoundViewTa.getFloat(R.styleable.SVGCompoundView_topSvgAlpha, svgAlpha);
        mCompoundSVGParameters[2].svgAlpha = svgCompoundViewTa.getFloat(R.styleable.SVGCompoundView_rightSvgAlpha, svgAlpha);
        mCompoundSVGParameters[3].svgAlpha = svgCompoundViewTa.getFloat(R.styleable.SVGCompoundView_bottomSvgAlpha, svgAlpha);
        mCompoundSVGParameters[0].svgWidth = svgCompoundViewTa.getDimensionPixelSize(R.styleable.SVGCompoundView_leftSvgWidth, svgWidth);
        mCompoundSVGParameters[1].svgWidth = svgCompoundViewTa.getDimensionPixelSize(R.styleable.SVGCompoundView_topSvgWidth, svgWidth);
        mCompoundSVGParameters[2].svgWidth = svgCompoundViewTa.getDimensionPixelSize(R.styleable.SVGCompoundView_rightSvgWidth, svgWidth);
        mCompoundSVGParameters[3].svgWidth = svgCompoundViewTa.getDimensionPixelSize(R.styleable.SVGCompoundView_bottomSvgWidth, svgWidth);
        mCompoundSVGParameters[0].svgHeight = svgCompoundViewTa.getDimensionPixelSize(R.styleable.SVGCompoundView_leftSvgHeight, svgHeight);
        mCompoundSVGParameters[1].svgHeight = svgCompoundViewTa.getDimensionPixelSize(R.styleable.SVGCompoundView_topSvgHeight, svgHeight);
        mCompoundSVGParameters[2].svgHeight = svgCompoundViewTa.getDimensionPixelSize(R.styleable.SVGCompoundView_rightSvgHeight, svgHeight);
        mCompoundSVGParameters[3].svgHeight = svgCompoundViewTa.getDimensionPixelSize(R.styleable.SVGCompoundView_bottomSvgHeight, svgHeight);
        mCompoundSVGParameters[0].svgRotation = svgCompoundViewTa.getFloat(R.styleable.SVGCompoundView_leftSvgRotation, svgRotation) % 360;
        mCompoundSVGParameters[1].svgRotation = svgCompoundViewTa.getFloat(R.styleable.SVGCompoundView_topSvgRotation, svgRotation) % 360;
        mCompoundSVGParameters[2].svgRotation = svgCompoundViewTa.getFloat(R.styleable.SVGCompoundView_rightSvgRotation, svgRotation) % 360;
        mCompoundSVGParameters[3].svgRotation = svgCompoundViewTa.getFloat(R.styleable.SVGCompoundView_bottomSvgRotation, svgRotation) % 360;
        svgCompoundViewTa.recycle();

        resetCompoundDrawables();
    }

    public void setSvgColor(ColorStateList svgColor) {
        mCompoundSVGParameters[0].svgColor = svgColor;
        mCompoundSVGParameters[1].svgColor = svgColor;
        mCompoundSVGParameters[2].svgColor = svgColor;
        mCompoundSVGParameters[3].svgColor = svgColor;
        resetCompoundDrawables();
    }

    public void setLeftSvgColor(ColorStateList svgColor) {
        mCompoundSVGParameters[0].svgColor = svgColor;
        resetCompoundDrawables();
    }

    public ColorStateList getLeftSvgColor() {
        return mCompoundSVGParameters[0].svgColor;
    }

    public void setTopSvgColor(ColorStateList svgColor) {
        mCompoundSVGParameters[1].svgColor = svgColor;
        resetCompoundDrawables();
    }

    public ColorStateList getTopSvgColor() {
        return mCompoundSVGParameters[1].svgColor;
    }

    public void setRightSvgColor(ColorStateList svgColor) {
        mCompoundSVGParameters[2].svgColor = svgColor;
        resetCompoundDrawables();
    }

    public ColorStateList getRightSvgColor() {
        return mCompoundSVGParameters[2].svgColor;
    }

    public void setBottomSvgColor(ColorStateList svgColor) {
        mCompoundSVGParameters[3].svgColor = svgColor;
        resetCompoundDrawables();
    }

    public ColorStateList getBottomSvgColor() {
        return mCompoundSVGParameters[3].svgColor;
    }

    public void setSvgColor(int color) {
        setSvgColor(ColorStateList.valueOf(color));
    }

    public void setLeftSvgColor(int color) {
        setLeftSvgColor(ColorStateList.valueOf(color));
    }

    public void setTopSvgColor(int color) {
        setTopSvgColor(ColorStateList.valueOf(color));
    }

    public void setRightSvgColor(int color) {
        setRightSvgColor(ColorStateList.valueOf(color));
    }

    public void setBottomSvgColor(int color) {
        setBottomSvgColor(ColorStateList.valueOf(color));
    }

    public void setSvgWidth(int width) {
        mCompoundSVGParameters[0].svgWidth = width;
        mCompoundSVGParameters[1].svgWidth = width;
        mCompoundSVGParameters[2].svgWidth = width;
        mCompoundSVGParameters[3].svgWidth = width;
        resetCompoundDrawables();
    }

    public void setLeftSvgWidth(int width) {
        mCompoundSVGParameters[0].svgWidth = width;
        resetCompoundDrawables();
    }

    public int getLeftSvgWidth() {
        return mCompoundSVGParameters[0].svgWidth;
    }

    public void setTopSvgWidth(int width) {
        mCompoundSVGParameters[1].svgWidth = width;
        resetCompoundDrawables();
    }

    public int getTopSvgWidth() {
        return mCompoundSVGParameters[1].svgWidth;
    }

    public void setRightSvgWidth(int width) {
        mCompoundSVGParameters[2].svgWidth = width;
        resetCompoundDrawables();
    }

    public int getRightSvgWidth() {
        return mCompoundSVGParameters[2].svgWidth;
    }

    public void setBottomSvgWidth(int width) {
        mCompoundSVGParameters[3].svgWidth = width;
        resetCompoundDrawables();
    }

    public int getBottomSvgWidth() {
        return mCompoundSVGParameters[3].svgWidth;
    }

    public void setSvgHeight(int height) {
        mCompoundSVGParameters[0].svgHeight = height;
        mCompoundSVGParameters[1].svgHeight = height;
        mCompoundSVGParameters[2].svgHeight = height;
        mCompoundSVGParameters[3].svgHeight = height;
        resetCompoundDrawables();
    }

    public void setLeftSvgHeight(int height) {
        mCompoundSVGParameters[0].svgHeight = height;
        resetCompoundDrawables();
    }

    public int getLeftSvgHeight() {
        return mCompoundSVGParameters[0].svgHeight;
    }

    public void setTopSvgHeight(int height) {
        mCompoundSVGParameters[1].svgHeight = height;
        resetCompoundDrawables();
    }

    public int getTopSvgHeight() {
        return mCompoundSVGParameters[1].svgHeight;
    }

    public void setRightSvgHeight(int height) {
        mCompoundSVGParameters[2].svgHeight = height;
        resetCompoundDrawables();
    }

    public int getRightSvgHeight() {
        return mCompoundSVGParameters[2].svgHeight;
    }

    public void setBottomSvgHeight(int height) {
        mCompoundSVGParameters[3].svgHeight = height;
        resetCompoundDrawables();
    }

    public int getBottomSvgHeight() {
        return mCompoundSVGParameters[3].svgHeight;
    }

    public void setSvgSize(int width, int height) {
        mCompoundSVGParameters[0].svgWidth = width;
        mCompoundSVGParameters[1].svgWidth = width;
        mCompoundSVGParameters[2].svgWidth = width;
        mCompoundSVGParameters[3].svgWidth = width;
        mCompoundSVGParameters[0].svgHeight = height;
        mCompoundSVGParameters[1].svgHeight = height;
        mCompoundSVGParameters[2].svgHeight = height;
        mCompoundSVGParameters[3].svgHeight = height;
        resetCompoundDrawables();
    }

    public void setLeftSvgSize(int width, int height) {
        mCompoundSVGParameters[0].svgWidth = width;
        mCompoundSVGParameters[0].svgHeight = height;
        resetCompoundDrawables();
    }

    public void setTopSvgSize(int width, int height) {
        mCompoundSVGParameters[1].svgWidth = width;
        mCompoundSVGParameters[1].svgHeight = height;
        resetCompoundDrawables();
    }

    public void setRightSvgSize(int width, int height) {
        mCompoundSVGParameters[2].svgWidth = width;
        mCompoundSVGParameters[2].svgHeight = height;
        resetCompoundDrawables();
    }

    public void setBottomSvgSize(int width, int height) {
        mCompoundSVGParameters[3].svgWidth = width;
        mCompoundSVGParameters[3].svgHeight = height;
        resetCompoundDrawables();
    }

    public void setSvgAlpha(float alpha) {
        mCompoundSVGParameters[0].svgAlpha = alpha;
        mCompoundSVGParameters[1].svgAlpha = alpha;
        mCompoundSVGParameters[2].svgAlpha = alpha;
        mCompoundSVGParameters[3].svgAlpha = alpha;
        resetCompoundDrawables();
    }

    public void setLeftSvgAlpha(float alpha) {
        mCompoundSVGParameters[0].svgAlpha = alpha;
        resetCompoundDrawables();
    }

    public float getLeftSvgAlpha() {
        return mCompoundSVGParameters[0].svgAlpha;
    }

    public void setTopSvgAlpha(float alpha) {
        mCompoundSVGParameters[1].svgAlpha = alpha;
        resetCompoundDrawables();
    }

    public float getTopSvgAlpha() {
        return mCompoundSVGParameters[1].svgAlpha;
    }

    public void setRightSvgAlpha(float alpha) {
        mCompoundSVGParameters[2].svgAlpha = alpha;
        resetCompoundDrawables();
    }

    public float getRightSvgAlpha() {
        return mCompoundSVGParameters[2].svgAlpha;
    }

    public void setBottomSvgAlpha(float alpha) {
        mCompoundSVGParameters[3].svgAlpha = alpha;
        resetCompoundDrawables();
    }

    public float getBottomSvgAlpha() {
        return mCompoundSVGParameters[3].svgAlpha;
    }

    public void setSvgRotation(float rotation) {
        mCompoundSVGParameters[0].svgRotation = rotation;
        mCompoundSVGParameters[1].svgRotation = rotation;
        mCompoundSVGParameters[2].svgRotation = rotation;
        mCompoundSVGParameters[3].svgRotation = rotation;
        resetCompoundDrawables();
    }

    public void setLeftSvgRotation(float rotation) {
        mCompoundSVGParameters[0].svgRotation = rotation;
        resetCompoundDrawables();
    }

    public float getLeftSvgRotation() {
        return mCompoundSVGParameters[0].svgRotation;
    }

    public void setTopSvgRotation(float rotation) {
        mCompoundSVGParameters[1].svgRotation = rotation;
        resetCompoundDrawables();
    }

    public float getTopSvgRotation() {
        return mCompoundSVGParameters[1].svgRotation;
    }

    public void setRightSvgRotation(float rotation) {
        mCompoundSVGParameters[2].svgRotation = rotation;
        resetCompoundDrawables();
    }

    public float getRightSvgRotation() {
        return mCompoundSVGParameters[2].svgRotation;
    }

    public void setBottomSvgRotation(float rotation) {
        mCompoundSVGParameters[3].svgRotation = rotation;
        resetCompoundDrawables();
    }

    public float getBottomSvgRotation() {
        return mCompoundSVGParameters[3].svgRotation;
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        resetCompoundDrawables();
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        resetCompoundDrawables();
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        resetCompoundDrawables();
    }

    private void resetCompoundDrawables() {
        if (mCompoundSVGParameters == null) {
            return;
        }
        Drawable[] drawables = getCompoundDrawables();
        for (int i = 0; i < drawables.length; i++) {
            resetDrawable(drawables[i], mCompoundSVGParameters[i]);
        }
        super.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    private void resetDrawable(Drawable drawable, CompoundSVGParameter svgParameter) {
        if (drawable != null && drawable instanceof SVGDrawable) {
            drawable.mutate();
            ((SVGDrawable)drawable).setTintList(svgParameter.svgColor);
            if (svgParameter.svgAlpha > 0 && svgParameter.svgAlpha <= 1.0f) {
                ((SVGDrawable)drawable).setAlpha((int) (svgParameter.svgAlpha * 0xFF));
            }
            if (svgParameter.svgWidth > 0) {
                ((SVGDrawable)drawable).setWidth(svgParameter.svgWidth);
            }
            if (svgParameter.svgHeight > 0) {
                ((SVGDrawable)drawable).setHeight(svgParameter.svgHeight);
            }
            if (svgParameter.svgRotation != 0) {
                ((SVGDrawable)drawable).setRotation(svgParameter.svgRotation);
            }
        }
    }
}