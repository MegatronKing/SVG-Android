package com.android.svg.support.extend;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.android.svg.support.R;

/**
 * Support rendering any color to TextView with svg images.<br>
 *
 * @author Megatron King
 * @since 2016/10/10 19:11
 */
public class SVGColorImageButton extends ImageButton {

    private ColorStateList mImageColor;

    public SVGColorImageButton(Context context) {
        this(context, null);
    }

    public SVGColorImageButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageButton(context, attrs);
    }

    public SVGColorImageButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageButton(context, attrs);
    }

    private void initImageButton(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SVGColorView);
        mImageColor = a.getColorStateList(R.styleable.SVGColorView_imageColor);
        a.recycle();
    }

    public void setImageColor(ColorStateList imageColor) {
        this.mImageColor = imageColor;
        invalidate();
    }

    public void setImageColor(int color) {
        setImageColor(ColorStateList.valueOf(color));
    }

    @Override
    public void draw(Canvas canvas) {
        // src drawable
        SVGColorHelper.tintColor2Drawable(getDrawable(), mImageColor);
        // background drawable
        SVGColorHelper.tintColor2Drawable(getBackground(), mImageColor);
        super.draw(canvas);
    }
}
