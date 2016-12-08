package com.android.svg.support.svg.parser;

import com.android.svg.support.svg.model.SvgConstants;
import com.android.svg.support.svg.model.SvgNode;
import com.android.svg.support.svg.utils.StyleUtils;
import com.android.svg.support.svg.utils.TransformUtils;
import com.android.svg.support.utils.SCU;
import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.HashMap;

/**
 * All svg nodes have some common attribute, we handle them in this class.
 *
 * @author Megatron King
 * @since 2016/11/25 15:33
 */

public abstract class SvgNodeAbstractAttributeParser<T extends SvgNode> extends CommonAbstractAttributeParser<T> {

    @Override
    public void parse(Element element, T node) throws DocumentException {
        handleCommonAttributes(element, node);
    }

    private void handleCommonAttributes(Element element, SvgNode node) {
        String matrix = parseString(element, SvgConstants.ATTR_TRANSFORM);
        if (matrix != null) {
            if (matrix.startsWith("matrix")) {
                node.matrix = new float[6];
                node.matrix[0] = 1.0f;
                node.matrix[3] = 1.0f;
                String[] matrixValues = matrix.substring("matrix(".length(), matrix.length() - 1).split(",");
                for (int i = 0; i < matrixValues.length; i++) {
                    node.matrix[i] = SCU.parseFloat(matrixValues[i].trim(), node.matrix[i]);
                }
            } else {
                node.matrix = TransformUtils.formatTransform(matrix);
            }
        }
        node.styleMaps = StyleUtils.convertStyleString2Map(parseString(element, SvgConstants.ATTR_STYLE));
        if (node.styleMaps == null) {
            node.styleMaps = new HashMap<>();
        }
        String strokeWidth = parseString(element, SvgConstants.ATTR_STROKE_WIDTH);
        if (strokeWidth != null) {
            node.styleMaps.put(SvgConstants.ATTR_STROKE_WIDTH, strokeWidth);
        }
        String strokeColor = parseString(element, SvgConstants.ATTR_STROKE);
        if (strokeColor != null) {
            node.styleMaps.put(SvgConstants.ATTR_STROKE, strokeColor);
        }
        String fillColor = parseString(element, SvgConstants.ATTR_FILL);
        if (fillColor != null) {
            node.styleMaps.put(SvgConstants.ATTR_FILL, fillColor);
        }
        String display = parseString(element, SvgConstants.ATTR_DISPLAY);
        if (display != null) {
            node.styleMaps.put(SvgConstants.ATTR_DISPLAY, display.trim());
        }
        String strokeLineJoin = parseString(element, SvgConstants.ATTR_STROKE_LINEJOINE);
        if (strokeLineJoin != null) {
            node.styleMaps.put(SvgConstants.ATTR_STROKE_LINEJOINE, strokeLineJoin.trim());
        }
        String strokeLineCap = parseString(element, SvgConstants.ATTR_STROKE_LINECAP);
        if (strokeLineCap != null) {
            node.styleMaps.put(SvgConstants.ATTR_STROKE_LINECAP, strokeLineCap.trim());
        }
        String strokeMiterLimit = parseString(element, SvgConstants.ATTR_STROKE_MITERLIMIT);
        if (strokeMiterLimit != null) {
            node.styleMaps.put(SvgConstants.ATTR_STROKE_MITERLIMIT, strokeMiterLimit);
        }
    }

}
