/*
 * Copyright (C) 2008 The Android Open Source Project
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
package com.github.megatronking.svg.applet.graphics;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Used to represent the whole VectorDrawable XML file's tree.
 */
class VdTree {

    private static final String SHAPE_VECTOR = "vector";
    private static final String SHAPE_PATH = "path";
    private static final String SHAPE_GROUP = "group";

    private VdGroup mRootGroup = new VdGroup();

    private float mBaseWidth = 1;
    private float mBaseHeight = 1;
    private float mPortWidth = 1;
    private float mPortHeight = 1;
    private float mRootAlpha = 1;

    /*package*/ float getBaseWidth(){
        return mBaseWidth;
    }

    /*package*/ float getBaseHeight(){
        return mBaseHeight;
    }

    private void drawTree(Graphics2D g, int w, int h) {
        float scaleX = w / mPortWidth;
        float scaleY = h / mPortHeight;

        AffineTransform rootMatrix = new AffineTransform(); // identity

        mRootGroup.draw(g, rootMatrix, scaleX, scaleY);
    }

    /**
     * Draw the VdTree into an image.
     * If the root alpha is less than 1.0, then draw into a temporary image,
     * then draw into the result image applying alpha blending.
     */
    void drawIntoImage(BufferedImage image) {
        Graphics2D gFinal = (Graphics2D) image.getGraphics();
        int width = image.getWidth();
        int height = image.getHeight();
        gFinal.setColor(new Color(255, 255, 255, 0));
        gFinal.fillRect(0, 0, width, height);

        float rootAlpha = mRootAlpha;
        if (rootAlpha < 1.0) {
            BufferedImage alphaImage = AssetUtil.newArgbBufferedImage(width, height);
            Graphics2D gTemp = (Graphics2D)alphaImage.getGraphics();
            drawTree(gTemp, width, height);
            gFinal.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, rootAlpha));
            gFinal.drawImage(alphaImage, 0, 0, null);
            gTemp.dispose();
        } else {
            drawTree(gFinal, width, height);
        }
        gFinal.dispose();
    }

    void parse(Document doc) {
        Element rootNode = doc.getRootElement();
        if (SHAPE_VECTOR.equals(rootNode.getName())) {
            parseRootNode(rootNode);
            parseTree(rootNode, mRootGroup);
        } else {
            throw new RuntimeException("The root node is not vector!");
        }
    }

    private void parseTree(Element currentNode, VdGroup currentGroup) {
        List<?> childrenNodes = currentNode.elements();
        int length = childrenNodes.size();
        for (int i = 0; i < length; i ++) {
            Element child = (Element) childrenNodes.get(i);
            if (child.getNodeType() == Element.ELEMENT_NODE) {
                if (SHAPE_GROUP.equals(child.getName())) {
                    VdGroup newGroup = parseGroupAttributes(child.attributes());
                    currentGroup.add(newGroup);
                    parseTree(child, newGroup);
                } else if (SHAPE_PATH.equals(child.getName())) {
                    VdPath newPath = parsePathAttributes(child.attributes());
                    currentGroup.add(newPath);
                }
            }
        }
    }

    private void parseRootNode(Element rootNode) {
        if (rootNode.attributeCount() != 0) {
            parseSize(rootNode.attributes());
        }
    }

    private void parseSize(List<?> attributes) {

        Pattern pattern = Pattern.compile("^\\s*(\\d+(\\.\\d+)*)\\s*([a-zA-Z]+)\\s*$");

        int len = attributes.size();

        for (int i = 0; i < len; i++) {
            String name = ((Attribute)attributes.get(i)).getQualifiedName();
            String value = ((Attribute)attributes.get(i)).getValue();
            Matcher matcher = pattern.matcher(value);
            float size = 0;
            if (matcher.matches()) {
                size = Float.parseFloat(matcher.group(1));
            }
            if ("android:width".equals(name)) {
                mBaseWidth = size;
            } else if ("android:height".equals(name)) {
                mBaseHeight = size;
            } else if ("android:viewportWidth".equals(name)) {
                mPortWidth = Float.parseFloat(value);
            } else if ("android:viewportHeight".equals(name)) {
                mPortHeight = Float.parseFloat(value);
            } else if ("android:alpha".equals(name)) {
                mRootAlpha = Float.parseFloat(value);
            }
        }
    }

    private VdPath parsePathAttributes(List<?> attributes) {
        VdPath vgPath = new VdPath();
        vgPath.parseAttributes(attributes);
        return vgPath;
    }

    private VdGroup parseGroupAttributes(List<?> attributes) {
        VdGroup vgGroup = new VdGroup();
        vgGroup.parseAttributes(attributes);
        return vgGroup;
    }
}
