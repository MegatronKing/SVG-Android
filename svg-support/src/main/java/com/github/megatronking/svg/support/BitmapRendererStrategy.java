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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Before draw svg to canvas, we must draw to bitmap firstly.
 * This will cost some memories and time, but only by this way, we could
 * handle the alpha filter. And the drawn bitmap would be cached for the next render.
 *
 * @author Megatron King
 * @since 2016/9/4 14:54
 */

public class BitmapRendererStrategy extends CachedRendererStrategy {

    // Cap the bitmap size, such that it won't hurt the performance too much
    // and it won't crash due to a very large scale.
    // The drawable will look blurry above this size.
    private static final int MAX_CACHED_BITMAP_SIZE = 2048;

    private Bitmap mCachedBitmap;

    /**
     * Paint object used to draw cached bitmaps.
     */
    private Paint mPaint;

    public BitmapRendererStrategy(SVGRenderer renderer) {
        super(renderer);
    }

    @Override
    public void draw(Canvas canvas, int width, int height, ColorFilter filter, Rect dst) {
        width = Math.min(MAX_CACHED_BITMAP_SIZE, width);
        height = Math.min(MAX_CACHED_BITMAP_SIZE, height);
        createCachedBitmapIfNeeded(width, height);
        if (!canReuseCache()) {
            updateCachedBitmap(width, height);
            updateCacheStates();
        }
        drawCachedBitmapWithAlpha(canvas, filter, dst);
    }

    private void createCachedBitmapIfNeeded(int width, int height) {
        if (mCachedBitmap == null || !canReuseBitmap(width, height)) {
            mCachedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCacheDirty = true;
        }
    }

    private boolean canReuseBitmap(int width, int height) {
        return width == mCachedBitmap.getWidth() && height == mCachedBitmap.getHeight();
    }

    private void updateCachedBitmap(int width, int height) {
        mCachedBitmap.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(mCachedBitmap);
        mRenderer.render(canvas, width, height, null);
    }

    private void drawCachedBitmapWithAlpha(Canvas canvas, ColorFilter filter, Rect originalBounds) {
        // The bitmap's size is the same as the bounds.
        final Paint p = getPaint(filter);
        canvas.drawBitmap(mCachedBitmap, null, originalBounds, p);
    }

    private Paint getPaint(ColorFilter filter) {
        if (!hasTranslucentRoot() && filter == null) {
            return null;
        }

        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setFilterBitmap(true);
        }
        mPaint.setAlpha((int) (mRenderer.mAlpha * 0xFF));
        mPaint.setColorFilter(filter);
        return mPaint;
    }
}
