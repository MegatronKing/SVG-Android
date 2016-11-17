package com.android.svg.support.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.android.svg.support.SVGDrawable;

/**
 * A view helper.
 *
 * @author Megatron King
 * @since 2016/10/11 10:06
 */

public final class SVGHelper {

    public static void tintColor2Drawable(Drawable drawable, ColorStateList colorStateList) {
        if (drawable != null && drawable instanceof SVGDrawable) {
            drawable.mutate();
            ((SVGDrawable)drawable).setTintList(colorStateList);
        }
    }

    public static void width2Drawable(Drawable drawable, int width) {
        if (width < 0) {
            return;
        }
        if (drawable != null && drawable instanceof SVGDrawable) {
            drawable.mutate();
            ((SVGDrawable)drawable).setWidth(width);
        }
    }

    public static void height2Drawable(Drawable drawable, int height) {
        if (height < 0) {
            return;
        }
        if (drawable != null && drawable instanceof SVGDrawable) {
            drawable.mutate();
            ((SVGDrawable)drawable).setHeight(height);
        }
    }

    public static void alpha2Drawable(Drawable drawable, float alpha) {
        if (alpha < 0 || alpha > 1.0f) {
            return;
        }
        if (drawable != null && drawable instanceof SVGDrawable) {
            drawable.mutate();
            drawable.setAlpha((int) (alpha * 0xFF));
        }
    }

}
