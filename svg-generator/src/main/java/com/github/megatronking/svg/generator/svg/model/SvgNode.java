package com.github.megatronking.svg.generator.svg.model;

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

public abstract class SvgNode {

    protected String pathData;

    public Map<String, String> styleMaps;

    /**
     *   a c e
     * ( b d f )
     *   0 0 1
     */
    public float[] matrix;

    public abstract void toPath();

    public void applyStyles(Map<String, String> inheritStyles) {
        // Apply the styles of parent group node to it.
        if (inheritStyles != null && !inheritStyles.isEmpty()) {
            if (styleMaps == null) {
                styleMaps = new HashMap<>();
            }
            for (String key : inheritStyles.keySet()) {
                if (!styleMaps.containsKey(key)) {
                    styleMaps.put(key, inheritStyles.get(key));
                }
            }
        }
    }

    public void transform(float a, float b, float c, float d, float e, float f) {
        // No need to transform.
        if (!isValid()) {
            return;
        }
        // It is not a good choice to transform the node one by one, this will cause distortion
        // in some condition such as transform the line, but the group cannot support matrix.
        PathDataNode[] node = PathDataNode.createNodesFromPathData(pathData);
        if (!(a == 1.0f && b == 0.0f && c == 0.0f && d == 1.0f && e == 0.0f && f == 0.0f)) {
            PathDataNode.transform(a, b, c, d, e, f, node);
        }
        pathData = PathDataNode.nodeListToString(node);
    }

    public boolean isValid() {
        return pathData != null && somethingToDraw();
    }

    private boolean somethingToDraw() {
        // No style, we use the default color.
        if (styleMaps == null) {
            return true;
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
        boolean emptyFill = fillColor == Color.TRANSPARENT;
        int strokeColor = Color.convert(styleMaps.get(SvgConstants.ATTR_STROKE));
        float strokeWidth = Dimen.convert(styleMaps.get(SvgConstants.ATTR_STROKE_WIDTH));
        boolean emptyStroke = strokeColor == Color.TRANSPARENT || strokeWidth <= 0.0f;
        return !emptyFill || !emptyStroke;
    }

    public String convert2VectorXml(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append("<path\n");
        // Use black fill color as default.
        int fillColor = styleMaps != null && styleMaps.containsKey(SvgConstants.ATTR_FILL) ?
                Color.convert(styleMaps.get(SvgConstants.ATTR_FILL)) : Color.BLACK;
        if (fillColor != Color.TRANSPARENT) {
            sb.append(indent).append("    android:fillColor=\"#").append(Integer.toHexString(fillColor)).append("\"\n");
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
        }
        sb.append(indent).append("    android:pathData=\"").append(pathData).append("\"/>\n");
        return sb.toString();
    }
}
