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
        // Apply all styles to its children.
        for (SvgNode svgNode : children) {
            svgNode.applyStyles(styleMaps, defineStyles);
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        SvgGroupNode newNode = (SvgGroupNode) super.clone();
        if (newNode != null) {
            newNode.children = new ArrayList<>();
            for (SvgNode child: children) {
                SvgNode cloneChild = (SvgNode) child.clone();
                if (cloneChild != null) {
                    newNode.children.add(cloneChild);
                }
            }
        }
        return newNode;
    }
}
