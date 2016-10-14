package com.android.svg.support.extend;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.android.svg.support.SVGDrawable;

/**
 * A view helper.
 *
 * @author Megatron King
 * @since 2016/10/11 10:06
 */

public final class SVGColorHelper {

    public static void tintColor2Drawable(Drawable drawable, ColorStateList colorStateList) {
        if (drawable != null && drawable instanceof SVGDrawable) {
            drawable.mutate();
            ((SVGDrawable)drawable).setTintList(colorStateList);
        }
    }

}
