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
