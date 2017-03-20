/*
 * Copyright (C) 2017, Megatron King
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.megatronking.svg.support;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.LongSparseArray;
import android.util.TypedValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A helper class for some svg operations.
 *
 * @author Megatron King
 * @since 2016/9/4 14:52
 */
public class SVGHelper {

    public static LongSparseArray<Drawable.ConstantState> hackPreloadDrawables(Resources res) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return hackPreloadDrawablesV15(res);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return hackPreloadDrawablesV18(res);
        } else {
            return hackPreloadDrawablesV19(res);
        }
    }

    public static long resKey(Context context, int resId) {
        TypedValue value = new TypedValue();
        context.getResources().getValue(resId, value, true);
        return (((long)value.assetCookie) << 32) | value.data;
    }

    private static LongSparseArray<Drawable.ConstantState> hackPreloadDrawablesV15(Resources res) {
        try {
            Field field = Resources.class.getDeclaredField("sPreloadedDrawables");
            field.setAccessible(true);
            return (LongSparseArray<Drawable.ConstantState>) field.get(res);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        }
        return null;
    }

    private static LongSparseArray<Drawable.ConstantState> hackPreloadDrawablesV18(Resources res) {
        try {
            Field field = Resources.class.getDeclaredField("sPreloadedDrawables");
            field.setAccessible(true);
            return ((LongSparseArray<Drawable.ConstantState>[]) field.get(res))[0];
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        }
        return null;
    }

    private static LongSparseArray<Drawable.ConstantState> hackPreloadDrawablesV19(Resources res) {
        try {
            Method method = Resources.class.getDeclaredMethod("getPreloadedDrawables");
            method.setAccessible(true);
            return (LongSparseArray<Drawable.ConstantState>) method.invoke(res);
        } catch (NoSuchMethodException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } catch (IllegalArgumentException e) {
            // ignore
        } catch (InvocationTargetException e) {
            // ignore
        }
        return null;
    }

    /**
     * Convert a svg renderer to a Bitmap.
     *
     * @param renderer The SVGRenderer.
     * @return A Bitmap.
     */
    public static Bitmap createBitmap(SVGRenderer renderer) {
        return createBitmap(renderer, null);
    }

    /**
     * Convert a svg renderer to a Bitmap.
     *
     * @param renderer The SVGRenderer.
     * @param filter Assign the filter of Bitmap, can be null.
     * @return A Bitmap.
     */
    public static Bitmap createBitmap(SVGRenderer renderer, ColorFilter filter) {
        return createBitmap(renderer, renderer.mWidth, renderer.mHeight, filter);
    }

    /**
     * Convert a svg renderer to a Bitmap.
     *
     * @param renderer The SVGRenderer.
     * @param width Assign the width of Bitmap.
     * @param height Assign the height of Bitmap.
     * @return A Bitmap.
     */
    public static Bitmap createBitmap(SVGRenderer renderer, int width, int height) {
        return createBitmap(renderer, width, height, null);
    }

    /**
     * Convert a svg renderer to a Bitmap.
     *
     * @param renderer The SVGRenderer.
     * @param width Assign the width of Bitmap.
     * @param height Assign the height of Bitmap.
     * @param filter Assign the filter of Bitmap, can be null.
     * @return A Bitmap.
     */
    public static Bitmap createBitmap(SVGRenderer renderer, int width, int height, ColorFilter filter) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        renderer.render(canvas, width, height, filter);
        return bitmap;
    }
}
