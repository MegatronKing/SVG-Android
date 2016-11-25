package com.android.svg.support.svgnew.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The group element model in the svg xml.
 *
 * @author Megatron King
 * @since 2016/11/23 10:30
 */

public class G extends SvgGroupNode {

    // --- not support skew ---
//    public float skewX;
//    public float skewY;

    public List<SvgNode> children = new ArrayList<>();

    @Override
    public void toPath() {
    }

    @Override
    public void transform(float a, float b, float c, float d, float e, float f) {
        for (SvgNode svgNode : children) {
            // Transform self first, then transform parent.
            if (matrix != null) {
                svgNode.transform(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
            }
            svgNode.transform(a, b, c, d, e, f);
        }
    }

    @Override
    public void applyStyles(Map<String, String> inheritStyles) {
        super.applyStyles(inheritStyles);
        // Apply all styles to its children.
        for (SvgNode svgNode : children) {
            svgNode.applyStyles(styleMaps);
        }
    }

    @Override
    public boolean isValid() {
        boolean isValid = false;
        for (SvgNode svgNode : children) {
            if (svgNode.isValid()) {
                isValid = true;
            }
        }
        return isValid;
    }
}
