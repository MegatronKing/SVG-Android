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
 * The group element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/23 10:30
 */

public class G extends SvgGroupNode {

    @Override
    public void transform(float a, float b, float c, float d, float e, float f) {
        for (SvgNode svgNode : children) {
            if (matrix == null) {
                svgNode.transform(a, b, c, d, e, f);
            } else {
                matrix = TransformUtils.preConcat(matrix, new float[]{a, b, c, d, e ,f});
                svgNode.transform(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
            }
        }
    }

}
