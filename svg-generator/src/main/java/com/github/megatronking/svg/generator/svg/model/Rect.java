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
package com.github.megatronking.svg.generator.svg.model;

import com.github.megatronking.svg.generator.svg.utils.PathBuilder;

/**
 * The rect element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/22 19:01
 */

public class Rect extends SvgNode {

    public float x;
    public float y;
    public float width;
    public float height;

    @Override
    public void toPath() {
        if (Float.isNaN(x) || Float.isNaN(y)) {
            return;
        }
        // "M x, y h width v height h -width z"
        PathBuilder builder = new PathBuilder();
        builder.absoluteMoveTo(x, y);
        builder.relativeHorizontalTo(width);
        builder.relativeVerticalTo(height);
        builder.relativeHorizontalTo(-width);
        builder.relativeClose();
        pathData = builder.toString();
    }
}
