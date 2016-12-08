package com.android.svg.support.svg.model;

/**
 * Represent the SVG file in an internal data structure as a tree.
 *
 * @author Megatron King
 * @since 2016/11/22 17:06
 */

public class Svg extends SvgGroupNode {

    public float w;
    public float h;

    public float[] viewBox;

    @Override
    public void transform(float a, float b, float c, float d, float e, float f) {
        // Ignore itself.
        for (SvgNode svgNode : children) {
            transform(svgNode);
        }
    }

    private void transform(SvgNode node) {
        if (matrix != null) {
            node.transform(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
        }
        if (viewBox != null) {
            node.transform(1, 0, 0, 1, -viewBox[0], -viewBox[1]);
        }
    }

    @Override
    public boolean isValid() {
        // The root node is always valid.
        return true;
    }
}
