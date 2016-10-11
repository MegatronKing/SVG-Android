package com.android.svg.support.extend;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.svg.support.R;

/**
 * Support rendering any color to TextView with svg images.<br/>
 *
 * @author Megatron King
 * @since 2016/10/10 19:11
 */
public class SVGColorTextView extends TextView {

    private ColorStateList mImageColor;

    public SVGColorTextView(Context context) {
        this(context, null);
    }

    public SVGColorTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SVGColorTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        // compound drawables
        Drawable[] drawables = getCompoundDrawables();
        for (Drawable drawable : drawables) {
            SVGColorHelper.tintColor2Drawable(drawable, mImageColor);
        }
        // background drawable
        SVGColorHelper.tintColor2Drawable(getBackground(), mImageColor);
        super.draw(canvas);
    }
}
