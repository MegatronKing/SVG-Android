package com.android.svg.support;

import android.graphics.Canvas;

/**
 * A factory of {@link RendererStrategy}.
 *
 * @author Megatron King
 * @since 2016/9/4 15:16
 */

public class RendererStrategyFactory {

    public static RendererStrategy create(SVGRenderer renderer, Canvas canvas) {
        // We cannot apply alpha in picture, in this condition the bitmap is the only choice.
        if (canvas.isHardwareAccelerated() || renderer.mAlpha != 1.0f) {
            return new BitmapRendererStrategy(renderer);
        }
        if (!canvas.isHardwareAccelerated()) {
            return new PictureRendererStrategy(renderer);
        }
        return new NormalRendererStrategy(renderer);
    }

}
