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
 * The polygon element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/23 9:47
 */

public class Polygon extends SvgNode {

    public String points;

    @Override
    public void toPath() {
        if (points == null || points.length() == 0) {
            return;
        }
        PathBuilder builder = new PathBuilder();
        String[] split = points.split("[\\s,]+");
        float baseX = Float.parseFloat(split[0]);
        float baseY = Float.parseFloat(split[1]);
        builder.absoluteMoveTo(baseX, baseY);
        for (int j = 2; j < split.length; j += 2) {
            float x = Float.parseFloat(split[j]);
            float y = Float.parseFloat(split[j + 1]);
            builder.relativeLineTo(x - baseX, y - baseY);
            baseX = x;
            baseY = y;
        }
        builder.relativeClose();
        pathData = builder.toString();
    }

}
