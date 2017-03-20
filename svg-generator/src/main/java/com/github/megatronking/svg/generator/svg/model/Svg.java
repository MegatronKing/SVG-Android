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

import com.github.megatronking.svg.generator.utils.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void parseEnd() {
        // Handle define styles
        Map<String, Map<String, String>> defineStyles = new HashMap<>();
        for (Style style : findAllStylesFromSvg()) {
            defineStyles.putAll(style.toStyle());
        }
        // Handle use tags
        replaceUseTagWithHrefLink(children);

        // Apply styles from the root node of svg tree, until all leaf nodes was traversed.
        applyStyles(null, defineStyles);
        // Transform from the root node of svg tree, until all leaf nodes was traversed.
        transform(1.0f, 0, 0, 1.0f, 0, 0);
    }

    private List<Style> findAllStylesFromSvg() {
        List<Style> allStyles = new ArrayList<>();
        recursiveFindStyles(children, allStyles);
        return allStyles;
    }

    private void recursiveFindStyles(List<SvgNode> nodes, List<Style> styles) {
        if (nodes == null || nodes.isEmpty() || styles == null) {
            return;
        }
        for (SvgNode svgNode : nodes) {
            if (svgNode instanceof Style) {
                styles.add((Style) svgNode);
            } else if (svgNode instanceof SvgGroupNode) {
                recursiveFindStyles(((SvgGroupNode)svgNode).children, styles);
            }
        }
    }

    private void replaceUseTagWithHrefLink(List<SvgNode> nodes) {
        if (nodes == null) {
            return;
        }
        for (SvgNode svgNode : nodes) {
            if (svgNode instanceof Use) {
                if (!TextUtils.isEmpty(((Use) svgNode).href) && ((Use) svgNode).href.startsWith("#")) {
                    List<SvgNode> hrefNodes = findNodesById(((Use) svgNode).href.substring(1));
                    if (hrefNodes != null && !hrefNodes.isEmpty()) {
                        SvgNode clone = cloneNode(hrefNodes.get(0));
                        if (clone != null) {
                            if (clone instanceof Symbol) {
                                ((Symbol) clone).isInUse = true;
                            }
                            ((Use) svgNode).children.add(clone);
                        }
                    }
                }
            } else if (svgNode instanceof SvgGroupNode) {
                replaceUseTagWithHrefLink(((SvgGroupNode)svgNode).children);
            }
        }
    }

    private List<SvgNode> findNodesById(String id) {
        List<SvgNode> resultNodes = new ArrayList<>();
        recursiveFindNodesById(id, children, resultNodes);
        return resultNodes;
    }

    private void recursiveFindNodesById(String clazz, List<SvgNode> nodes, List<SvgNode> resultNodes) {
        if (nodes == null || nodes.isEmpty() || resultNodes == null) {
            return;
        }
        for (SvgNode svgNode : nodes) {
            if (!TextUtils.isEmpty(svgNode.id) && svgNode.id.equals(clazz)) {
                resultNodes.add(svgNode);
            } else if (svgNode instanceof SvgGroupNode) {
                recursiveFindNodesById(clazz, ((SvgGroupNode)svgNode).children, resultNodes);
            }
        }
    }

    private SvgNode cloneNode(SvgNode node) {
        try {
            return (SvgNode) node.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
