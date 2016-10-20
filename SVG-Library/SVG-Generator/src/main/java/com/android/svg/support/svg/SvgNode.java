
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

/**
 * Parent class for a SVG file's node, can be either group or leave element.
 */
abstract class SvgNode {
    protected String mName;
    // Keep a reference to the tree in order to dump the error log.
    private SvgTree mSvgTree;
    // Use document node to get the line number for error reporting.
    private Node mDocumentNode;

    public SvgNode(SvgTree svgTree, Node node, String name) {
        mName = name;
        mSvgTree = svgTree;
        mDocumentNode = node;
    }

    protected SvgTree getTree() {
        return mSvgTree;
    }

    public String getName() {
        return mName;
    }

    public Node getDocumentNode() {
        return mDocumentNode;
    }

    /**
     * dump the current node's debug info.
     */
    public abstract void dumpNode(String indent);

    /**
     * Write the Node content into the VectorDrawable's XML file.
     */
    public abstract void writeXML(OutputStreamWriter writer) throws IOException;

    /**
     * @return true the node is a group node.
     */
    public abstract boolean isGroupNode();

    /**
     * Transform the current Node with the transformation matrix.
     */
    public abstract void transform(float a, float b, float c, float d, float e, float f);
}