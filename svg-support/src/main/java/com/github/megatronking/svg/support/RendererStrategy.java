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
 * Define three strategy about svg rendering.
 *
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
