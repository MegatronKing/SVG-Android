package com.android.svg.support;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;

/**
 * Draw the svg image on the canvas of ImageView. The disadvantage of this approach is that
 * the alpha always be 1.0f and no cache used. The worst is that it did not work to the scaleType
 * of ImageView.
 *
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
