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

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;

/**
 * Like the {@link BitmapRendererStrategy}. The svg render result is cached in a {@link Picture},
 * But as same as the {@link NormalRendererStrategy}, there is no alpha effect. And if
 * hardwareAccelerated = true, the picture would not be displayed in ImageView.
 *
 * @author Megatron King
 * @since 2016/9/4 15:16
 */

public class PictureRendererStrategy extends CachedRendererStrategy {

    private Picture mCachedPicture;

    /**
     * Paint object used to draw cached pictures.
     */
    private Paint mPaint;

    public PictureRendererStrategy(SVGRenderer renderer) {
        super(renderer);
    }

    @Override
    public void draw(Canvas canvas, int width, int height, ColorFilter filter, Rect dst) {
        createCachedPictureIfNeeded(width, height);
        if (!canReuseCache()) {
            updateCachedPicture(width, height, filter);
            updateCacheStates();
        }
        drawCachedPictureWithAlpha(canvas, dst);
    }

    private void createCachedPictureIfNeeded(int width, int height) {
        if (mCachedPicture == null || !canReusePicture(width, height)) {
            mCachedPicture = new Picture();
            mCacheDirty = true;
        }
    }

    private boolean canReusePicture(int width, int height) {
        return width == mCachedPicture.getWidth() && height == mCachedPicture.getHeight();
    }

    private void updateCachedPicture(int width, int height, ColorFilter filter) {
        Canvas canvas = mCachedPicture.beginRecording(width, height);
        Paint paint = getPaint(filter);
        if (paint != null) {
            canvas.drawPaint(paint);
        }
        mRenderer.render(canvas, width, height, null);
        mCachedPicture.endRecording();
    }

    private void drawCachedPictureWithAlpha(Canvas canvas, Rect originalBounds) {
        // The Picture's size is the same as the bounds.
        canvas.drawPicture(mCachedPicture, originalBounds);
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
