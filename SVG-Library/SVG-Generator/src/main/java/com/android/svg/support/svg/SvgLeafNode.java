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

import com.android.annotations.Nullable;
import com.google.common.collect.ImmutableMap;

import org.w3c.dom.Node;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represent a SVG file's leave element.
 */
class SvgLeafNode extends SvgNode {
    private static Logger logger = Logger.getLogger(SvgLeafNode.class.getSimpleName());
    private String mPathData;
    // Key is the attributes for vector drawable, and the value is the converted from SVG.
    private HashMap<String, String> mVdAttributesMap = new HashMap<String, String>();

    public SvgLeafNode(SvgTree svgTree, Node node, String nodeName) {
        super(svgTree, node, nodeName);
    }

    private String getAttributeValues(ImmutableMap<String, String> presentationMap) {
        StringBuilder sb = new StringBuilder("/>\n");
        for (String key : mVdAttributesMap.keySet()) {
            String vectorDrawableAttr = presentationMap.get(key);
            String svgValue = mVdAttributesMap.get(key);
            String vdValue = svgValue.trim();
            // There are several cases we need to convert from SVG format to
            // VectorDrawable format. Like "none", "3px" or "rgb(255, 0, 0)"
            if ("none".equals(vdValue)) {
                vdValue = "#00000000";
            } else if (vdValue.endsWith("px")) {
                vdValue = vdValue.substring(0, vdValue.length() - 2);
            } else if (vdValue.startsWith("rgb")) {
                vdValue = vdValue.substring(3, vdValue.length());
                vdValue = convertRGBToHex(vdValue);
                if (vdValue == null) {
                    getTree().logErrorLine("Unsupported Color format " + vdValue, getDocumentNode(),
                            SvgTree.SvgLogLevel.ERROR);
                }
            }
            String attr = "\n        " + vectorDrawableAttr + "=\"" +
                    vdValue + "\"";
            sb.insert(0, attr);
        }
        return sb.toString();
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * SVG allows using rgb(int, int, int) or rgb(float%, float%, float%) to
     * represent a color, but Android doesn't. Therefore, we need to convert
     * them into #RRGGBB format.
     *
     * @param svgValue in either "(int, int, int)" or "(float%, float%, float%)"
     * @return #RRGGBB in hex format, or null, if an error is found.
     */
    @Nullable
    private String convertRGBToHex(String svgValue) {
        // We don't support color keyword yet.
        // http://www.w3.org/TR/SVG11/types.html#ColorKeywords
        String result = null;
        String functionValue = svgValue.trim();
        functionValue = svgValue.substring(1, functionValue.length() - 1);
        // After we cut the "(", ")", we can deal with the numbers.
        String[] numbers = functionValue.split(",");
        if (numbers.length != 3) {
            return null;
        }
        int[] color = new int[3];
        for (int i = 0; i < 3; i++) {
            String number = numbers[i];
            number = number.trim();
            if (number.endsWith("%")) {
                float value = Float.parseFloat(number.substring(0, number.length() - 1));
                color[i] = clamp((int) (value * 255.0f / 100.0f), 0, 255);
            } else {
                int value = Integer.parseInt(number);
                color[i] = clamp(value, 0, 255);
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("#");
        for (int i = 0; i < 3; i++) {
            builder.append(String.format("%02X", color[i]));
        }
        result = builder.toString();
        assert result.length() == 7;
        return result;
    }

    @Override
    public void dumpNode(String indent) {
        logger.log(Level.FINE, indent + (mPathData != null ? mPathData : " null pathData ") +
                (mName != null ? mName : " null name "));
    }

    public void setPathData(String pathData) {
        mPathData = pathData;
    }

    @Override
    public boolean isGroupNode() {
        return false;
    }

    @Override
    public void transform(float a, float b, float c, float d, float e, float f) {
        if ("none".equals(mVdAttributesMap.get("fill")) || (mPathData == null)) {
            // Nothing to draw and transform, early return.
            return;
        }
        // TODO: We need to just apply the transformation to group.
        VdPath.Node[] n = VdParser.parsePath(mPathData);
        if (!(a == 1 && d == 1 && b == 0 && c == 0 && e == 0 && f == 0)) {
            VdPath.Node.transform(a, b, c, d, e, f, n);
        }
        mPathData = VdPath.Node.NodeListToString(n);
    }

    @Override
    public void writeXML(OutputStreamWriter writer) throws IOException {
        String fillColor = mVdAttributesMap.get(Svg2Vector.SVG_FILL_COLOR);
        String strokeColor = mVdAttributesMap.get(Svg2Vector.SVG_STROKE_COLOR);
        logger.log(Level.FINE, "fill color " + fillColor);
        boolean emptyFill = fillColor != null && ("none".equals(fillColor) || "#0000000".equals(fillColor));
        boolean emptyStroke = strokeColor == null || "none".equals(strokeColor);
        boolean emptyPath = mPathData == null;
        boolean nothingToDraw = emptyPath || emptyFill && emptyStroke;
        if (nothingToDraw) {
            return;
        }
        writer.write("    <path\n");
        if (!mVdAttributesMap.containsKey(Svg2Vector.SVG_FILL_COLOR)) {
            logger.log(Level.FINE, "ADDING FILL SVG_FILL_COLOR");
            writer.write("        android:fillColor=\"#FF000000\"\n");
        }
        writer.write("        android:pathData=\"" + mPathData + "\"");
        writer.write(getAttributeValues(Svg2Vector.presentationMap));
    }

    public void fillPresentationAttributes(String name, String value) {
        logger.log(Level.FINE, ">>>> PROP " + name + " = " + value);
        if (value.startsWith("url(")) {
            getTree().logErrorLine("Unsupported URL value: " + value, getDocumentNode(),
                    SvgTree.SvgLogLevel.ERROR);
            return;
        }
        mVdAttributesMap.put(name, value);
    }
}