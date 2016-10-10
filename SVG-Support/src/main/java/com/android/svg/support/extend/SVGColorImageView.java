package com.android.svg.support.extend;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.android.svg.support.R;
import com.android.svg.support.SVGDrawable;

/**
 * We can set any render color to a svg image with this SVGColorImageView.<br/>
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
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SVGColorImageView);
        mImageColor = a.getColorStateList(R.styleable.SVGColorImageView_imageColor);
        a.recycle();
    }

    @Override
    public void draw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null && drawable instanceof SVGDrawable) {
            drawable.mutate();
            ((SVGDrawable)drawable).setTintList(mImageColor);
        }
        super.draw(canvas);
    }
}
