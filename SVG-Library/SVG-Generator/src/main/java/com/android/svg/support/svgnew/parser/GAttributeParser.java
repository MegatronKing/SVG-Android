package com.android.svg.support.svgnew.parser;

import com.android.svg.support.svgnew.model.G;
import com.android.svg.support.svgnew.model.SvgConstants;
import com.android.svg.support.svgnew.utils.StyleUtils;
import com.android.svg.support.svgnew.utils.TransformUtils;
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

public class GAttributeParser extends CommonAbstractAttributeParser<G>{

    @Override
    public void parse(Element element, G group) {
        String matrix = parseString(element, SvgConstants.ATTR_TRANSFORM);
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
                group.matrix = TransformUtils.formatTransform(matrix);
            }
        }
        group.styleMaps = StyleUtils.convertStyleString2Map(parseString(element, SvgConstants.ATTR_STYLE));
        if (group.styleMaps == null) {
            group.styleMaps = new HashMap<>();
        }
        String strokeColor = parseString(element, SvgConstants.ATTR_STROKE);
        if (strokeColor != null) {
            group.styleMaps.put(SvgConstants.ATTR_STROKE, strokeColor);
        }
        String fillColor = parseString(element, SvgConstants.ATTR_FILL);
        if (fillColor != null) {
            group.styleMaps.put(SvgConstants.ATTR_FILL, fillColor);
        }
        String strokeWidth = parseString(element, SvgConstants.ATTR_STROKE_WIDTH);
        if (strokeWidth != null) {
            group.styleMaps.put(SvgConstants.ATTR_STROKE_WIDTH, strokeWidth);
        }
    }

}
