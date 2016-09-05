package com.android.svg.support;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;

/**
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
