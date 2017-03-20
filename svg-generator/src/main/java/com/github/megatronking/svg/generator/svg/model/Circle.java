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
 * The circle element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/22 19:28
 */

public class Circle extends SvgNode {

    public float cx;
    public float cy;
    public float r;

    @Override
    public void toPath() {
        if (Float.isNaN(cx) || Float.isNaN(cx) || r <= 0) {
            return;
        }
        // "M cx cy m -r, 0 a r,r 0 1,1 (r * 2),0 a r,r 0 1,1 -(r * 2),0"
        PathBuilder builder = new PathBuilder();
        builder.absoluteMoveTo(cx, cy);
        builder.relativeMoveTo(-r, 0);
        builder.relativeArcTo(r, r, false, true, true, 2 * r, 0);
        builder.relativeArcTo(r, r, false, true, true, -2 * r, 0);
        pathData =  builder.toString();
    }

}
