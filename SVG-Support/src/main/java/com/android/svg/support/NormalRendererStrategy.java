package com.android.svg.support;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;

/**
 * @author Megatron King
 * @since 2016/9/4 14:52
 */

public class NormalRendererStrategy extends RendererStrategy {

    public NormalRendererStrategy(SVGRenderer renderer) {
        super(renderer);
    }

    @Override
    public void draw(Canvas canvas, int width, int height, ColorFilter filter, Rect dst) {
        mRenderer.render(canvas, width, height, filter);
    }
}
