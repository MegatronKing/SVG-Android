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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Converts SVG to VectorDrawable's XML
 */
public class Svg2Vector {
    private static Logger logger = Logger.getLogger(Svg2Vector.class.getSimpleName());
    public static final String SVG_POLYGON = "polygon";
    public static final String SVG_RECT = "rect";
    public static final String SVG_CIRCLE = "circle";
    public static final String SVG_LINE = "line";
    public static final String SVG_PATH = "path";
    public static final String SVG_GROUP = "g";
    public static final String SVG_TRANSFORM = "transform";
    public static final String SVG_WIDTH = "width";
    public static final String SVG_HEIGHT = "height";
    public static final String SVG_VIEW_BOX = "viewBox";
    public static final String SVG_STYLE = "style";
    public static final String SVG_DISPLAY = "display";
    public static final String SVG_D = "d";
    public static final String SVG_STROKE_COLOR = "stroke";
    public static final String SVG_STROKE_OPACITY = "stroke-opacity";
    public static final String SVG_STROKE_LINEJOINE = "stroke-linejoin";
    public static final String SVG_STROKE_LINECAP = "stroke-linecap";
    public static final String SVG_STROKE_WIDTH = "stroke-width";
    public static final String SVG_FILL_COLOR = "fill";
    public static final String SVG_FILL_OPACITY = "fill-opacity";
    public static final String SVG_OPACITY = "opacity";
    public static final String SVG_CLIP = "clip";
    public static final String SVG_POINTS = "points";
    public static final ImmutableMap<String, String> presentationMap =
            ImmutableMap.<String, String>builder()
                    .put(SVG_STROKE_COLOR, "android:strokeColor")
                    .put(SVG_STROKE_OPACITY, "android:strokeAlpha")
                    .put(SVG_STROKE_LINEJOINE, "android:strokeLinejoin")
                    .put(SVG_STROKE_LINECAP, "android:strokeLinecap")
                    .put(SVG_STROKE_WIDTH, "android:strokeWidth")
                    .put(SVG_FILL_COLOR, "android:fillColor")
                    .put(SVG_FILL_OPACITY, "android:fillAlpha")
                    .put(SVG_CLIP, "android:clip").put(SVG_OPACITY, "android:fillAlpha")
                    .build();
    // List all the Svg nodes that we don't support. Categorized by the types.
    private static final HashSet<String> unsupportedSvgNodes = Sets.newHashSet(
            // Animation elements
            "animate", "animateColor", "animateMotion", "animateTransform", "mpath", "set",
            // Container elements
            "a", "defs", "glyph", "marker", "mask", "missing-glyph", "pattern", "switch", "symbol",
            // Filter primitive elements
            "feBlend", "feColorMatrix", "feComponentTransfer", "feComposite", "feConvolveMatrix",
            "feDiffuseLighting", "feDisplacementMap", "feFlood", "feFuncA", "feFuncB", "feFuncG",
            "feFuncR", "feGaussianBlur", "feImage", "feMerge", "feMergeNode", "feMorphology",
            "feOffset", "feSpecularLighting", "feTile", "feTurbulence",
            // Font elements
            "font", "font-face", "font-face-format", "font-face-name", "font-face-src", "font-face-uri",
            "hkern", "vkern",
            // Gradient elements
            "linearGradient", "radialGradient", "stop",
            // Graphics elements
            "ellipse", "polyline", "text", "use",
            // Light source elements
            "feDistantLight", "fePointLight", "feSpotLight",
            // Structural elements
            "defs", "symbol", "use",
            // Text content elements
            "altGlyph", "altGlyphDef", "altGlyphItem", "glyph", "glyphRef", "textPath", "text", "tref",
            "tspan",
            // Text content child elements
            "altGlyph", "textPath", "tref", "tspan",
            // Uncategorized elements
            "clipPath", "color-profile", "cursor", "filter", "foreignObject", "script", "view");

    private static SvgTree parse(File f) throws Exception {
        SvgTree svgTree = new SvgTree();
        Document doc = svgTree.parse(f);
        NodeList nSvgNode;
        // Parse svg elements
        nSvgNode = doc.getElementsByTagName("svg");
        if (nSvgNode.getLength() != 1) {
            throw new IllegalStateException("Not a proper SVG file");
        }
        Node rootNode = nSvgNode.item(0);
        for (int i = 0; i < nSvgNode.getLength(); i++) {
            Node nNode = nSvgNode.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                parseDimension(svgTree, nNode);
            }
        }
        if (svgTree.viewBox == null) {
            svgTree.logErrorLine("Missing \"viewBox\" in <svg> element", rootNode, SvgTree.SvgLogLevel.ERROR);
            return svgTree;
        }
        if ((svgTree.w == 0 || svgTree.h == 0) && svgTree.viewBox[2] > 0 && svgTree.viewBox[3] > 0) {
            svgTree.w = svgTree.viewBox[2];
            svgTree.h = svgTree.viewBox[3];
        }
        // Parse transformation information.
        // TODO: Properly handle transformation in the group level. In the "use" case, we treat
        // it as global for now.
        NodeList nUseTags;
        svgTree.matrix = new float[6];
        svgTree.matrix[0] = 1;
        svgTree.matrix[3] = 1;
        nUseTags = doc.getElementsByTagName("use");
        for (int temp = 0; temp < nUseTags.getLength(); temp++) {
            Node nNode = nUseTags.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                parseTransformation(svgTree, nNode);
            }
        }
        SvgGroupNode root = new SvgGroupNode(svgTree, rootNode, "root");
        svgTree.setRoot(root);
        // Parse all the group and path node recursively.
        traverseSVGAndExtract(svgTree, root, rootNode);
        svgTree.dump(root);
        return svgTree;
    }

    private static void traverseSVGAndExtract(SvgTree svgTree, SvgGroupNode currentGroup, Node item) {
        // Recursively traverse all the group and path nodes
        NodeList allChildren = item.getChildNodes();
        for (int i = 0; i < allChildren.getLength(); i++) {
            Node currentNode = allChildren.item(i);
            String nodeName = currentNode.getNodeName();
            if (SVG_PATH.equals(nodeName) ||
                    SVG_RECT.equals(nodeName) ||
                    SVG_CIRCLE.equals(nodeName) ||
                    SVG_POLYGON.equals(nodeName) ||
                    SVG_LINE.equals(nodeName)) {
                SvgLeafNode child = new SvgLeafNode(svgTree, currentNode, nodeName + i);
                extractAllItemsAs(svgTree, child, currentNode);
                currentGroup.addChild(child);
            } else if (SVG_GROUP.equals(nodeName)) {
                SvgGroupNode childGroup = new SvgGroupNode(svgTree, currentNode, "child" + i);
                currentGroup.addChild(childGroup);
                traverseSVGAndExtract(svgTree, childGroup, currentNode);
            } else {
                // For other fancy tags, like <refs>, they can contain children too.
                // Report the unsupported nodes.
                if (unsupportedSvgNodes.contains(nodeName)) {
                    svgTree.logErrorLine("<" + nodeName + "> is not supported", currentNode,
                            SvgTree.SvgLogLevel.ERROR);
                }
                traverseSVGAndExtract(svgTree, currentGroup, currentNode);
            }
        }
    }

    private static void parseTransformation(SvgTree avg, Node nNode) {
        NamedNodeMap a = nNode.getAttributes();
        int len = a.getLength();
        for (int i = 0; i < len; i++) {
            Node n = a.item(i);
            String name = n.getNodeName();
            String value = n.getNodeValue();
            if (SVG_TRANSFORM.equals(name)) {
                if (value.startsWith("matrix(")) {
                    value = value.substring("matrix(".length(), value.length() - 1);
                    String[] sp = value.split(" ");
                    for (int j = 0; j < sp.length; j++) {
                        avg.matrix[j] = Float.parseFloat(sp[j]);
                    }
                }
            }
        }
    }

    private static void parseDimension(SvgTree avg, Node nNode) {
        NamedNodeMap a = nNode.getAttributes();
        int len = a.getLength();
        for (int i = 0; i < len; i++) {
            Node n = a.item(i);
            String name = n.getNodeName();
            String value = n.getNodeValue();
            int subStringSize = value.length();
            if (subStringSize > 2) {
                if (value.endsWith("px")) {
                    subStringSize = subStringSize - 2;
                }
            }
            if (SVG_WIDTH.equals(name)) {
                avg.w = Float.parseFloat(value.substring(0, subStringSize));
            } else if (SVG_HEIGHT.equals(name)) {
                avg.h = Float.parseFloat(value.substring(0, subStringSize));
            } else if (SVG_VIEW_BOX.equals(name)) {
                avg.viewBox = new float[4];
                String[] strbox = value.split(" ");
                for (int j = 0; j < avg.viewBox.length; j++) {
                    avg.viewBox[j] = Float.parseFloat(strbox[j]);
                }
            }
        }
        if (avg.viewBox == null && avg.w != 0 && avg.h != 0) {
            avg.viewBox = new float[4];
            avg.viewBox[2] = avg.w;
            avg.viewBox[3] = avg.h;
        }
    }

    // Read the content from currentItem, and fill into "child"
    private static void extractAllItemsAs(SvgTree avg, SvgLeafNode child, Node currentItem) {
        Node currentGroup = currentItem.getParentNode();
        boolean hasNodeAttr = false;
        String styleContent = "";
        boolean nothingToDisplay = false;
        while (currentGroup != null && currentGroup.getNodeName().equals("g")) {
            // Parse the group's attributes.
            logger.log(Level.FINE, "Printing current parent");
            printlnCommon(currentGroup);
            NamedNodeMap attr = currentGroup.getAttributes();
            Node nodeAttr = attr.getNamedItem(SVG_STYLE);
            // Search for the "display:none", if existed, then skip this item.
            if (nodeAttr != null) {
                styleContent += nodeAttr.getTextContent() + ";";
                logger.log(Level.FINE, "styleContent is :" + styleContent + "at number group ");
                if (styleContent.contains("display:none")) {
                    logger.log(Level.FINE, "Found none style, skip the whole group");
                    nothingToDisplay = true;
                    break;
                } else {
                    hasNodeAttr = true;
                }
            }
            Node displayAttr = attr.getNamedItem(SVG_DISPLAY);
            if (displayAttr != null && "none".equals(displayAttr.getNodeValue())) {
                logger.log(Level.FINE, "Found display:none style, skip the whole group");
                nothingToDisplay = true;
                break;
            }
            currentGroup = currentGroup.getParentNode();
        }
        if (nothingToDisplay) {
            // Skip this current whole item.
            return;
        }
        logger.log(Level.FINE, "Print current item");
        printlnCommon(currentItem);
        if (hasNodeAttr && styleContent != null) {
            addStyleToPath(child, styleContent);
        }
        Node currentGroupNode = currentItem;
        if (SVG_PATH.equals(currentGroupNode.getNodeName())) {
            extractPathItem(avg, child, currentGroupNode);
        }
        if (SVG_RECT.equals(currentGroupNode.getNodeName())) {
            extractRectItem(avg, child, currentGroupNode);
        }
        if (SVG_CIRCLE.equals(currentGroupNode.getNodeName())) {
            extractCircleItem(avg, child, currentGroupNode);
        }
        if (SVG_POLYGON.equals(currentGroupNode.getNodeName())) {
            extractPolyItem(avg, child, currentGroupNode);
        }
        if (SVG_LINE.equals(currentGroupNode.getNodeName())) {
            extractLineItem(avg, child, currentGroupNode);
        }
    }

    private static void printlnCommon(Node n) {
        logger.log(Level.FINE, " nodeName=\"" + n.getNodeName() + "\"");
        String val = n.getNamespaceURI();
        if (val != null) {
            logger.log(Level.FINE, " uri=\"" + val + "\"");
        }
        val = n.getPrefix();
        if (val != null) {
            logger.log(Level.FINE, " pre=\"" + val + "\"");
        }
        val = n.getLocalName();
        if (val != null) {
            logger.log(Level.FINE, " local=\"" + val + "\"");
        }
        val = n.getNodeValue();
        if (val != null) {
            logger.log(Level.FINE, " nodeValue=");
            if (val.trim().equals("")) {
                // Whitespace
                logger.log(Level.FINE, "[WS]");
            } else {
                logger.log(Level.FINE, "\"" + n.getNodeValue() + "\"");
            }
        }
    }

    /**
     * Convert polygon element into a path.
     */
    private static void extractPolyItem(SvgTree avg, SvgLeafNode child, Node currentGroupNode) {
        logger.log(Level.FINE, "Rect found" + currentGroupNode.getTextContent());
        if (currentGroupNode.getNodeType() == Node.ELEMENT_NODE) {
            NamedNodeMap a = currentGroupNode.getAttributes();
            int len = a.getLength();
            for (int itemIndex = 0; itemIndex < len; itemIndex++) {
                Node n = a.item(itemIndex);
                String name = n.getNodeName();
                String value = n.getNodeValue();
                if (name.equals(SVG_STYLE)) {
                    addStyleToPath(child, value);
                } else if (presentationMap.containsKey(name)) {
                    child.fillPresentationAttributes(name, value);
                } else if (name.equals(SVG_POINTS)) {
                    PathBuilder builder = new PathBuilder();
                    String[] split = value.split("[\\s,]+");
                    float baseX = Float.parseFloat(split[0]);
                    float baseY = Float.parseFloat(split[1]);
                    builder.absoluteMoveTo(baseX, baseY);
                    for (int j = 2; j < split.length; j += 2) {
                        float x = Float.parseFloat(split[j]);
                        float y = Float.parseFloat(split[j + 1]);
                        builder.relativeLineTo(x - baseX, y - baseY);
                        baseX = x;
                        baseY = y;
                    }
                    builder.relativeClose();
                    child.setPathData(builder.toString());
                }
            }
        }
    }

    /**
     * Convert rectangle element into a path.
     */
    private static void extractRectItem(SvgTree avg, SvgLeafNode child, Node currentGroupNode) {
        logger.log(Level.FINE, "Rect found" + currentGroupNode.getTextContent());
        if (currentGroupNode.getNodeType() == Node.ELEMENT_NODE) {
            float x = 0;
            float y = 0;
            float width = Float.NaN;
            float height = Float.NaN;
            NamedNodeMap a = currentGroupNode.getAttributes();
            int len = a.getLength();
            boolean pureTransparent = false;
            for (int j = 0; j < len; j++) {
                Node n = a.item(j);
                String name = n.getNodeName();
                String value = n.getNodeValue();
                if (name.equals(SVG_STYLE)) {
                    addStyleToPath(child, value);
                    if (value.contains("opacity:0;")) {
                        pureTransparent = true;
                    }
                } else if (presentationMap.containsKey(name)) {
                    child.fillPresentationAttributes(name, value);
                } else if (name.equals("clip-path") && value.startsWith("url(#SVGID_")) {
                } else if (name.equals("x")) {
                    x = Float.parseFloat(value);
                } else if (name.equals("y")) {
                    y = Float.parseFloat(value);
                } else if (name.equals("width")) {
                    width = Float.parseFloat(value);
                } else if (name.equals("height")) {
                    height = Float.parseFloat(value);
                } else if (name.equals("style")) {
                }
            }
            if (!pureTransparent && avg != null && !Float.isNaN(x) && !Float.isNaN(y)
                    && !Float.isNaN(width)
                    && !Float.isNaN(height)) {
                // "M x, y h width v height h -width z"
                PathBuilder builder = new PathBuilder();
                builder.absoluteMoveTo(x, y);
                builder.relativeHorizontalTo(width);
                builder.relativeVerticalTo(height);
                builder.relativeHorizontalTo(-width);
                builder.relativeClose();
                child.setPathData(builder.toString());
            }
        }
    }

    /**
     * Convert circle element into a path.
     */
    private static void extractCircleItem(SvgTree avg, SvgLeafNode child, Node currentGroupNode) {
        logger.log(Level.FINE, "circle found" + currentGroupNode.getTextContent());
        if (currentGroupNode.getNodeType() == Node.ELEMENT_NODE) {
            float cx = 0;
            float cy = 0;
            float radius = 0;
            NamedNodeMap a = currentGroupNode.getAttributes();
            int len = a.getLength();
            boolean pureTransparent = false;
            for (int j = 0; j < len; j++) {
                Node n = a.item(j);
                String name = n.getNodeName();
                String value = n.getNodeValue();
                if (name.equals(SVG_STYLE)) {
                    addStyleToPath(child, value);
                    if (value.contains("opacity:0;")) {
                        pureTransparent = true;
                    }
                } else if (presentationMap.containsKey(name)) {
                    child.fillPresentationAttributes(name, value);
                } else if (name.equals("clip-path") && value.startsWith("url(#SVGID_")) {
                } else if (name.equals("cx")) {
                    cx = Float.parseFloat(value);
                } else if (name.equals("cy")) {
                    cy = Float.parseFloat(value);
                } else if (name.equals("r")) {
                    radius = Float.parseFloat(value);
                }
            }
            if (!pureTransparent && avg != null && !Float.isNaN(cx) && !Float.isNaN(cy)) {
                // "M cx cy m -r, 0 a r,r 0 1,1 (r * 2),0 a r,r 0 1,1 -(r * 2),0"
                PathBuilder builder = new PathBuilder();
                builder.absoluteMoveTo(cx, cy);
                builder.relativeMoveTo(-radius, 0);
                builder.relativeArcTo(radius, radius, false, true, true, 2 * radius, 0);
                builder.relativeArcTo(radius, radius, false, true, true, -2 * radius, 0);
                child.setPathData(builder.toString());
            }
        }
    }

    /**
     * Convert line element into a path.
     */
    private static void extractLineItem(SvgTree avg, SvgLeafNode child, Node currentGroupNode) {
        logger.log(Level.FINE, "line found" + currentGroupNode.getTextContent());
        if (currentGroupNode.getNodeType() == Node.ELEMENT_NODE) {
            float x1 = 0;
            float y1 = 0;
            float x2 = 0;
            float y2 = 0;
            NamedNodeMap a = currentGroupNode.getAttributes();
            int len = a.getLength();
            boolean pureTransparent = false;
            for (int j = 0; j < len; j++) {
                Node n = a.item(j);
                String name = n.getNodeName();
                String value = n.getNodeValue();
                if (name.equals(SVG_STYLE)) {
                    addStyleToPath(child, value);
                    if (value.contains("opacity:0;")) {
                        pureTransparent = true;
                    }
                } else if (presentationMap.containsKey(name)) {
                    child.fillPresentationAttributes(name, value);
                } else if (name.equals("clip-path") && value.startsWith("url(#SVGID_")) {
                    // TODO: Handle clip path here.
                } else if (name.equals("x1")) {
                    x1 = Float.parseFloat(value);
                } else if (name.equals("y1")) {
                    y1 = Float.parseFloat(value);
                } else if (name.equals("x2")) {
                    x2 = Float.parseFloat(value);
                } else if (name.equals("y2")) {
                    y2 = Float.parseFloat(value);
                }
            }
            if (!pureTransparent && avg != null && !Float.isNaN(x1) && !Float.isNaN(y1)
                    && !Float.isNaN(x2) && !Float.isNaN(y2)) {
                // "M x1, y1 L x2, y2"
                PathBuilder builder = new PathBuilder();
                builder.absoluteMoveTo(x1, y1);
                builder.absoluteLineTo(x2, y2);
                child.setPathData(builder.toString());
            }
        }
    }

    private static void extractPathItem(SvgTree avg, SvgLeafNode child, Node currentGroupNode) {
        logger.log(Level.FINE, "Path found " + currentGroupNode.getTextContent());
        if (currentGroupNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) currentGroupNode;
            NamedNodeMap a = currentGroupNode.getAttributes();
            int len = a.getLength();
            for (int j = 0; j < len; j++) {
                Node n = a.item(j);
                String name = n.getNodeName();
                String value = n.getNodeValue();
                if (name.equals(SVG_STYLE)) {
                    addStyleToPath(child, value);
                } else if (presentationMap.containsKey(name)) {
                    child.fillPresentationAttributes(name, value);
                } else if (name.equals(SVG_D)) {
                    String pathData = value.replaceAll("(\\d)-", "$1,-");
                    child.setPathData(pathData);
                }
            }
        }
    }

    private static void addStyleToPath(SvgLeafNode path, String value) {
        logger.log(Level.FINE, "Style found is " + value);
        if (value != null) {
            String[] parts = value.split(";");
            for (int k = parts.length - 1; k >= 0; k--) {
                String subStyle = parts[k];
                String[] nameValue = subStyle.split(":");
                if (nameValue.length == 2 && nameValue[0] != null && nameValue[1] != null) {
                    if (presentationMap.containsKey(nameValue[0])) {
                        path.fillPresentationAttributes(nameValue[0], nameValue[1]);
                    } else if (nameValue[0].equals(SVG_OPACITY)) {
                        // TODO: This is hacky, since we don't have a group level
                        // android:opacity. This only works when the path didn't overlap.
                        path.fillPresentationAttributes(SVG_FILL_OPACITY, nameValue[1]);
                    }
                }
            }
        }
    }

    private static final String head = "<vector xmlns:android=\"http://schemas.android.com/apk/res/android\"\n";

    private static String getSizeString(float w, float h, float scaleFactor) {
        String size = "        android:width=\"" + (int) (w * scaleFactor) + "dp\"\n" +
                "        android:height=\"" + (int) (h * scaleFactor) + "dp\"\n";
        return size;
    }

    private static void writeFile(OutputStream outStream, SvgTree svgTree, int width, int height) throws IOException {
        OutputStreamWriter fw = new OutputStreamWriter(outStream);
        fw.write(head);
        float finalWidth = width <= 0 ? svgTree.w : width;
        float finalHeight = height <= 0 ? svgTree.h : height;
        fw.write(getSizeString(finalWidth, finalHeight, svgTree.mScaleFactor));
        fw.write("        android:viewportWidth=\"" + svgTree.w + "\"\n");
        fw.write("        android:viewportHeight=\"" + svgTree.h + "\">\n");
        svgTree.normalize();
        // TODO: this has to happen in the tree mode!!!
        writeXML(svgTree, fw);
        fw.write("</vector>\n");
        fw.close();
    }

    private static void writeXML(SvgTree svgTree, OutputStreamWriter fw) throws IOException {
        svgTree.getRoot().writeXML(fw);
    }

    /**
     * Convert a SVG file into VectorDrawable's XML content, if no error is found.
     *
     * @param inputSVG  the input SVG file
     * @param outStream the converted VectorDrawable's content. This can be
     *                  empty if there is any error found during parsing
     * @return the error messages, which contain things like all the tags
     * VectorDrawble don't support or exception message.
     */
    public static String parseSvgToXml(File inputSVG, OutputStream outStream, int width, int height) {
        // Write all the error message during parsing into SvgTree. and return here as getErrorLog().
        // We will also log the exceptions here.
        String errorLog = null;
        try {
            SvgTree svgTree = parse(inputSVG);
            errorLog = svgTree.getErrorLog();
            // When there was anything in the input SVG file that we can't
            // convert to VectorDrawable, we logged them as errors.
            // After we logged all the errors, we skipped the XML file generation.
            if (svgTree.canConvertToVectorDrawable()) {
                writeFile(outStream, svgTree, width, height);
            }
        } catch (Exception e) {
            errorLog = "EXCEPTION in parsing " + inputSVG.getName() + ":\n" + e.getMessage();
        }
        return errorLog;
    }
}