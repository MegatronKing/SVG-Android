package com.github.megatronking.svg.generator.svg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represent a SVG file's group element.
 *
 * @author Megatron King
 * @since 2016/11/23 16:36
 */

public abstract class SvgGroupNode extends SvgNode {

    public List<SvgNode> children = new ArrayList<>();

    @Override
    public void toPath() {
        // The group node has nothing to path
    }

    @Override
    public void applyStyles(Map<String, String> inheritStyles) {
        super.applyStyles(inheritStyles);
        // Apply all styles to its children.
        for (SvgNode svgNode : children) {
            svgNode.applyStyles(styleMaps);
        }
    }

}
