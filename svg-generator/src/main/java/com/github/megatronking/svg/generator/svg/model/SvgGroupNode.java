package com.github.megatronking.svg.generator.svg.model;

import java.util.ArrayList;
import java.util.HashMap;
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
    public void applyStyles(Map<String, String> inheritStyles, Map<String, Map<String, String>> defineStyles) {
        super.applyStyles(inheritStyles, defineStyles);
        if (defineStyles == null) {
            defineStyles = new HashMap<>();
        }
        for (SvgNode svgNode : children) {
            if (svgNode instanceof Style) {
                applyStyleFromNode((Style) svgNode, defineStyles);
            } else if (svgNode instanceof Defs) {
                for (SvgNode svgNodeInDefs : ((Defs) svgNode).children) {
                    if (svgNodeInDefs instanceof Style) {
                        applyStyleFromNode((Style) svgNodeInDefs, defineStyles);
                    }
                }
            }
        }
        // Apply all styles to its children.
        for (SvgNode svgNode : children) {
            svgNode.applyStyles(styleMaps, defineStyles);
        }
    }

    private void applyStyleFromNode(Style style, Map<String, Map<String, String>> defineStyles) {
        Map<String, Map<String, String>> defineStylesTemp = style.toStyle();
        defineStyles.putAll(defineStylesTemp);
    }

}
