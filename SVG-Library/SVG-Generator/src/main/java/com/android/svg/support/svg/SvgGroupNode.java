/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.svg.support.svg;

import org.w3c.dom.Node;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represent a SVG file's group element.
 */
class SvgGroupNode extends SvgNode {
    private static Logger logger = Logger.getLogger(SvgGroupNode.class.getSimpleName());
    private static final String INDENT_LEVEL = "    ";
    private ArrayList<SvgNode> mChildren = new ArrayList<SvgNode>();

    public SvgGroupNode(SvgTree svgTree, Node docNode, String name) {
        super(svgTree, docNode, name);
    }

    public void addChild(SvgNode child) {
        mChildren.add(child);
    }

    @Override
    public void dumpNode(String indent) {
        // Print the current group.
        logger.log(Level.FINE, indent + "current group is :" + getName());
        // Then print all the children.
        for (SvgNode node : mChildren) {
            node.dumpNode(indent + INDENT_LEVEL);
        }
    }

    @Override
    public boolean isGroupNode() {
        return true;
    }

    @Override
    public void transform(float a, float b, float c, float d, float e, float f) {
        for (SvgNode p : mChildren) {
            p.transform(a, b, c, d, e, f);
        }
    }

    @Override
    public void writeXML(OutputStreamWriter writer) throws IOException {
        for (SvgNode node : mChildren) {
            node.writeXML(writer);
        }
    }
}