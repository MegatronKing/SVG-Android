package com.android.svg.support.extend;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.android.svg.support.R;

/**
 * Support rendering any color to View with svg images.<br>
 *
 * @author Megatron King
 * @since 2016/10/10 19:11
 */
public class SVGColorView extends View {

    private ColorStateList mImageColor;

    public SVGColorView(Context context) {
        this(context, null);
    }

    public SVGColorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SVGColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SVGColorView);
        mImageColor = a.getColorStateList(R.styleable.SVGColorView_imageColor);
        a.recycle();
    }

    @Override
    public void draw(Canvas canvas) {
        // background drawable
        SVGColorHelper.tintColor2Drawable(getBackground(), mImageColor);
        super.draw(canvas);
    }

    public void setImageColor(ColorStateList imageColor) {
        this.mImageColor = imageColor;
        invalidate();
    }

    public void setImageColor(int color) {
        setImageColor(ColorStateList.valueOf(color));
    }
}
