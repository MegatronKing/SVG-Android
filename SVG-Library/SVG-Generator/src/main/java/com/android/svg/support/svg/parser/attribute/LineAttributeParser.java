package com.android.svg.support.svg.parser.attribute;

import com.android.svg.support.svg.model.Line;
import com.android.svg.support.svg.model.SvgConstants;
import com.android.svg.support.svg.parser.SvgNodeAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Build line's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/22 19:00
 */

public class LineAttributeParser extends SvgNodeAbstractAttributeParser<Line> {

    @Override
    public void parse(Element element, Line line) throws DocumentException {
        super.parse(element, line);
        line.x1 = parseFloat(element, SvgConstants.ATTR_X1);
        line.y1 = parseFloat(element, SvgConstants.ATTR_Y1);
        line.x2 = parseFloat(element, SvgConstants.ATTR_X2);
        line.y2 = parseFloat(element, SvgConstants.ATTR_Y2);
        line.toPath();
    }
}
