package com.android.svg.support.svg.parser.attribute;

import com.android.svg.support.svg.model.Circle;
import com.android.svg.support.svg.model.SvgConstants;
import com.android.svg.support.svg.parser.SvgNodeAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Build circle's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/22 19:00
 */

public class CircleAttributeParser extends SvgNodeAbstractAttributeParser<Circle> {

    @Override
    public void parse(Element element, Circle circle) throws DocumentException {
        super.parse(element, circle);
        circle.cx = parseFloat(element, SvgConstants.ATTR_CX);
        circle.cy = parseFloat(element, SvgConstants.ATTR_CY);
        circle.r = parseFloat(element, SvgConstants.ATTR_R);
        circle.toPath();
    }
}
