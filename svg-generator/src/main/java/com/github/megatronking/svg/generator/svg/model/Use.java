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

import com.github.megatronking.svg.generator.svg.utils.TransformUtils;

/**
 * The use element model in the svg xml.
 *
 * @author Megatron King
 * @since 2017/1/11 16:55
 */
public class Use extends G {

    public float x;
    public float y;

    // Not support this now
    public float width;
    public float height;

    public String href;

    @Override
    public void transform(float a, float b, float c, float d, float e, float f) {
        if (x != 0 || y != 0) {
            if (matrix == null) {
                matrix = new float[] {1, 0, 0, 1, x, y};
            } else {
                matrix = TransformUtils.preConcat(matrix, new float[] {1, 0, 0, 1, x, y});
            }
        }
        super.transform(a, b, c, d, e, f);
    }
}
