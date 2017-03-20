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
 * The line element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/22 19:34
 */

public class Line extends SvgNode {

    public float x1;
    public float y1;
    public float x2;
    public float y2;

    @Override
    public void toPath() {
        if (Float.isNaN(x1) || Float.isNaN(y1) || Float.isNaN(x2) || Float.isNaN(y2)) {
            return;
        }
        // "M x1, y1 L x2, y2"
        PathBuilder builder = new PathBuilder();
        builder.absoluteMoveTo(x1, y1);
        builder.absoluteLineTo(x2, y2);
        pathData =  builder.toString();
    }

}
