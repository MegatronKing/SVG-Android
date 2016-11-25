package com.android.svg.support.svg.parser;

import com.android.svg.support.svg.model.Line;
import com.android.svg.support.svg.model.SvgConstants;
import com.android.svg.support.svg.utils.StyleUtils;
import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

import java.util.HashMap;

/**
 * Build line's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/22 19:00
 */

public class LineAttributeParser extends CommonAbstractAttributeParser<Line>{

    @Override
    public void parse(Element element, Line line) {
        line.x1 = parseFloat(element, SvgConstants.ATTR_X1);
        line.y1 = parseFloat(element, SvgConstants.ATTR_Y1);
        line.x2 = parseFloat(element, SvgConstants.ATTR_X2);
        line.y2 = parseFloat(element, SvgConstants.ATTR_Y2);
        line.styleMaps = StyleUtils.convertStyleString2Map(parseString(element, SvgConstants.ATTR_STYLE));
        if (line.styleMaps == null) {
            line.styleMaps = new HashMap<>();
        }
        String strokeWidth = parseString(element, SvgConstants.ATTR_STROKE_WIDTH);
        if (strokeWidth != null) {
            line.styleMaps.put(SvgConstants.ATTR_STROKE_WIDTH, strokeWidth);
        }
        String strokeColor = parseString(element, SvgConstants.ATTR_STROKE);
        if (strokeColor != null) {
            line.styleMaps.put(SvgConstants.ATTR_STROKE, strokeColor);
        }
        line.toPath();
    }
}
