package com.android.svg.support.svg.parser;

import com.android.svg.support.utils.SCU;
import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

import java.util.HashMap;

/**
 * Build groups's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/22 19:00
 */

public class GAttributeParser extends CommonAbstractAttributeParser<com.android.svg.support.svg.model.G>{

    @Override
    public void parse(Element element, com.android.svg.support.svg.model.G group) {
        String matrix = parseString(element, com.android.svg.support.svg.model.SvgConstants.ATTR_TRANSFORM);
        if (matrix != null) {
            if (matrix.startsWith("matrix")) {
                group.matrix = new float[6];
                group.matrix[0] = 1.0f;
                group.matrix[3] = 1.0f;
                String[] matrixValues = matrix.substring("matrix(".length(), matrix.length() - 1).split(",");
                for (int i = 0; i < matrixValues.length; i++) {
                    group.matrix[i] = SCU.parseFloat(matrixValues[i].trim(), group.matrix[i]);
                }
            } else {
                group.matrix = com.android.svg.support.svg.utils.TransformUtils.formatTransform(matrix);
            }
        }
        group.styleMaps = com.android.svg.support.svg.utils.StyleUtils.convertStyleString2Map(parseString(element, com.android.svg.support.svg.model.SvgConstants.ATTR_STYLE));
        if (group.styleMaps == null) {
            group.styleMaps = new HashMap<>();
        }
        String strokeColor = parseString(element, com.android.svg.support.svg.model.SvgConstants.ATTR_STROKE);
        if (strokeColor != null) {
            group.styleMaps.put(com.android.svg.support.svg.model.SvgConstants.ATTR_STROKE, strokeColor);
        }
        String fillColor = parseString(element, com.android.svg.support.svg.model.SvgConstants.ATTR_FILL);
        if (fillColor != null) {
            group.styleMaps.put(com.android.svg.support.svg.model.SvgConstants.ATTR_FILL, fillColor);
        }
        String strokeWidth = parseString(element, com.android.svg.support.svg.model.SvgConstants.ATTR_STROKE_WIDTH);
        if (strokeWidth != null) {
            group.styleMaps.put(com.android.svg.support.svg.model.SvgConstants.ATTR_STROKE_WIDTH, strokeWidth);
        }
    }

}
