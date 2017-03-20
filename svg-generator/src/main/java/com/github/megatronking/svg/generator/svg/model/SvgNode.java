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

import com.github.megatronking.svg.generator.svg.utils.TransformUtils;
import com.github.megatronking.svg.generator.utils.Color;
import com.github.megatronking.svg.generator.utils.Dimen;
import com.github.megatronking.svg.generator.utils.FloatUtils;
import com.github.megatronking.svg.generator.utils.PathDataNode;
import com.github.megatronking.svg.generator.utils.SCU;

import java.util.HashMap;
import java.util.Map;

/**
 * Parent class for a SVG file's node, can be either group or leave element.
 *
 * @author Megatron King
 * @since 2016/11/22 17:11
 */

public abstract class SvgNode implements Cloneable {

    public String id;
    public String clazz;

    protected String pathData;

    public Map<String, String> styleMaps;

    /**
     *   a c e
     * ( b d f )
     *   0 0 1
     */
    public float[] matrix;

    public abstract void toPath();

    public void applyStyles(Map<String, String> inheritStyles, Map<String, Map<String, String>> defineStyles) {
        if (styleMaps == null) {
            styleMaps = new HashMap<>();
        }
        for (String key : styleMaps.keySet()) {
            String value = styleMaps.get(key).trim();
            if ("inherit".equals(value)) {
                styleMaps.remove(key);
            }
        }
        // Query for defined styles.
        String nodeName = getClass().getSimpleName().toLowerCase();
        applyDefineStylesByRef(nodeName, defineStyles);
        if (clazz != null) {
            applyDefineStylesByRef("." + clazz, defineStyles);
            applyDefineStylesByRef(nodeName + "." + clazz, defineStyles);
        }
        if (id != null) {
            applyDefineStylesByRef("#" + id, defineStyles);
            applyDefineStylesByRef(nodeName + "#" + id, defineStyles);
        }

        // Apply the styles of parent group node to it.
        if (inheritStyles != null && !inheritStyles.isEmpty()) {
            for (String key : inheritStyles.keySet()) {
                if (!styleMaps.containsKey(key)) {
                    styleMaps.put(key, inheritStyles.get(key));
                }
            }
        }
    }

    private void applyDefineStylesByRef(String refName, Map<String, Map<String, String>> defineStyles) {
        if (defineStyles != null && !defineStyles.isEmpty() && defineStyles.containsKey(refName)) {
            Map<String, String> defineStylesByRefNames = defineStyles.get(refName);
            if (defineStylesByRefNames != null && !defineStylesByRefNames.isEmpty()) {
                for (String styleName : defineStylesByRefNames.keySet()) {
                    if (!styleMaps.containsKey(styleName)) {
                        styleMaps.put(styleName, defineStylesByRefNames.get(styleName));
                    }
                }
            }
        }
    }

    public void transform(float a, float b, float c, float d, float e, float f) {
        // No need to transform.
        if (!isValid()) {
            return;
        }
        matrix = TransformUtils.preConcat(matrix, new float[]{a, b, c, d, e ,f});
        if (pathData == null) {
            return;
        }
        // It is not a good choice to transform the node one by one, this will cause distortion
        // in some condition such as transform the line, but the group cannot support matrix.
        PathDataNode[] node = PathDataNode.createNodesFromPathData(pathData);
        if (node == null) {
            return;
        }
        if (!(matrix[0] == 1.0f && matrix[1] == 0.0f && matrix[2] == 0.0f && matrix[3] == 1.0f && matrix[4] == 0.0f && matrix[5] == 0.0f)) {
            PathDataNode.transform(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5], node);
        }
        pathData = PathDataNode.nodeListToString(node);
    }

    public boolean isValid() {
        return pathData != null && somethingToDraw();
    }

    private boolean somethingToDraw() {
        // No style, we use the default color.
        if (styleMaps == null) {
            styleMaps = new HashMap<>();
        }
        // Opacity is zero, ignore this node.
        if ("0".equals(styleMaps.get("opacity"))) {
            return false;
        }
        if ("none".equals(styleMaps.get(SvgConstants.ATTR_DISPLAY))) {
            return false;
        }
        // Fill or stroke color must be seen.
        int fillColor = styleMaps.containsKey(SvgConstants.ATTR_FILL) ? Color.convert(styleMaps.get(SvgConstants.ATTR_FILL))
                : Color.BLACK;
        float fillOpacity = styleMaps.containsKey(SvgConstants.ATTR_FILL_OPACITY) ? SCU.parseFloat(styleMaps.get(SvgConstants.ATTR_FILL_OPACITY), 1.0f)
                : 1.0f;
        boolean emptyFill = fillColor == Color.TRANSPARENT || fillOpacity == 0;
        int strokeColor = Color.convert(styleMaps.get(SvgConstants.ATTR_STROKE));
        float strokeWidth = Dimen.convert(styleMaps.get(SvgConstants.ATTR_STROKE_WIDTH));
        if (strokeColor != Color.TRANSPARENT && strokeWidth == 0) {
            strokeWidth = 1.0f;
        }
        float strokeOpacity = styleMaps.containsKey(SvgConstants.ATTR_STROKE_OPACITY) ? SCU.parseFloat(styleMaps.get(SvgConstants.ATTR_STROKE_OPACITY), 1.0f)
                : 1.0f;
        boolean emptyStroke = strokeColor == Color.TRANSPARENT || strokeWidth <= 0.0f || strokeOpacity == 0;
        return !emptyFill || !emptyStroke;
    }

    public String convert2VectorXml(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append("<path\n");
        if (id != null && id.length() != 0) {
            sb.append(indent).append("    android:name=\"").append(id).append("\"\n");
        }
        // Use black fill color as default.
        int fillColor = styleMaps != null && styleMaps.containsKey(SvgConstants.ATTR_FILL) ?
                Color.convert(styleMaps.get(SvgConstants.ATTR_FILL)) : Color.BLACK;
        if (fillColor != Color.TRANSPARENT) {
            sb.append(indent).append("    android:fillColor=\"#").append(Integer.toHexString(fillColor)).append("\"\n");
        }
        String fillOpacity = styleMaps.get(SvgConstants.ATTR_FILL_OPACITY);
        if (fillOpacity != null) {
            float fillOpacityAsFloat = SCU.parseFloat(fillOpacity, 1.0f);
            if (fillOpacityAsFloat != 0) {
                sb.append(indent).append("    android:fillAlpha=\"").append(fillOpacityAsFloat).append("\"\n");
            }
        }
        // fillType used in API 24, and value 'evenodd' or 'nonzero' needs a conversation
        String fillRule = styleMaps.get(SvgConstants.ATTR_FILL_RULE);
        if (fillRule != null && !"inherit".equals(fillRule)) {
            if ("evenodd".equals(fillRule)) {
                fillRule = "evenOdd";
            }
            if ("nonzero".equals(fillRule)) {
                fillRule = "nonZero";
            }
            sb.append(indent).append("    android:fillType=\"").append(fillRule).append("\"\n");
        }
        // Stroke color and width must be valid.
        if (styleMaps != null) {
            int strokeColor = Color.convert(styleMaps.get(SvgConstants.ATTR_STROKE));
            float strokeWidth = Dimen.convert(styleMaps.get(SvgConstants.ATTR_STROKE_WIDTH));
            if (strokeColor != Color.TRANSPARENT) {
                // Has stroke color, the width would be 1.0 with a default value.
                if (strokeWidth == 0) {
                    strokeWidth = 1.0f;
                }
                sb.append(indent).append("    android:strokeColor=\"#").append(Integer.toHexString(strokeColor)).append("\"\n");
                sb.append(indent).append("    android:strokeWidth=\"").append(FloatUtils.format2String(strokeWidth)).append("\"\n");
            }
            // lineJoin, lineCap and strokeMiterLimit not support 'inherit'
            String strokeLineJoin = styleMaps.get(SvgConstants.ATTR_STROKE_LINEJOINE);
            if (strokeLineJoin != null && !"inherit".equals(strokeLineJoin)) {
                sb.append(indent).append("    android:strokeLineJoin=\"").append(strokeLineJoin).append("\"\n");
            }
            String strokeLineCap = styleMaps.get(SvgConstants.ATTR_STROKE_LINECAP);
            if (strokeLineCap != null && !"inherit".equals(strokeLineCap)) {
                sb.append(indent).append("    android:strokeLineCap=\"").append(strokeLineCap).append("\"\n");
            }
            String strokeMiterLimit = styleMaps.get(SvgConstants.ATTR_STROKE_MITERLIMIT);
            if (strokeMiterLimit != null) {
                float strokeMiterLimitAsFloat = SCU.parseFloat(strokeMiterLimit, 4f);
                if (!"inherit".equals(strokeMiterLimit) && strokeMiterLimitAsFloat >= 1) {
                    sb.append(indent).append("    android:strokeMiterLimit=\"").append(strokeMiterLimitAsFloat).append("\"\n");
                }
            }
            String strokeOpacity = styleMaps.get(SvgConstants.ATTR_STROKE_OPACITY);
            if (strokeOpacity != null) {
                float strokeOpacityAsFloat = SCU.parseFloat(strokeOpacity, 1.0f);
                if (strokeOpacityAsFloat != 0) {
                    sb.append(indent).append("    android:strokeAlpha=\"").append(strokeOpacityAsFloat).append("\"\n");
                }
            }
        }
        sb.append(indent).append("    android:pathData=\"").append(pathData).append("\"/>\n");
        return sb.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        SvgNode newNode = (SvgNode) super.clone();
        if (newNode != null && styleMaps != null) {
            newNode.styleMaps = new HashMap<>(styleMaps);
        }
        return newNode;
    }
}
