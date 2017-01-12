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
