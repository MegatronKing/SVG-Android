package com.android.svg.support.extend;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.svg.support.R;

/**
 * Support rendering any color to ImageView with svg images.<br>
 *
 * @author Megatron King
 * @since 2016/10/10 19:11
 */
public class SVGColorImageView extends ImageView {

    private ColorStateList mImageColor;

    public SVGColorImageView(Context context) {
        this(context, null);
    }

    public SVGColorImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SVGColorImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        // src drawable
        SVGColorHelper.tintColor2Drawable(getDrawable(), mImageColor);
        // background drawable
        SVGColorHelper.tintColor2Drawable(getBackground(), mImageColor);
        super.draw(canvas);
    }
}
