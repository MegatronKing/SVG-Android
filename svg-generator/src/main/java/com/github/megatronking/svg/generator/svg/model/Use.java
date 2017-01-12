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
