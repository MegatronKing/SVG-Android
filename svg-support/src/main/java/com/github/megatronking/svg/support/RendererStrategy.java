package com.github.megatronking.svg.support;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;

/**
 * Define three strategy about svg rendering.
 * @see NormalRendererStrategy
 * @see BitmapRendererStrategy
 * @see PictureRendererStrategy
 *
 * @author Megatron King
 * @since 2016/9/4 14:46
 */

public abstract class RendererStrategy {

    protected SVGRenderer mRenderer;

    public RendererStrategy(SVGRenderer renderer) {
        this.mRenderer = renderer;
    }

    public abstract void draw(Canvas canvas, int width, int height, ColorFilter filter, Rect dst);
}
