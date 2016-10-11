package com.android.svg.support.extend;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.android.svg.support.R;

/**
 * Support rendering any color to TextView with svg images.<br/>
 *
 * @author Megatron King
 * @since 2016/10/10 19:11
 */
public class SVGColorEditText extends EditText {

    private ColorStateList mImageColor;

    public SVGColorEditText(Context context) {
        this(context, null);
    }

    public SVGColorEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initEditText(context, attrs);
    }

    public SVGColorEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEditText(context, attrs);
    }

    private void initEditText(Context context, AttributeSet attrs) {
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
