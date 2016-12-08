package com.android.svg.support.svg.parser.attribute;

import com.android.svg.support.svg.model.Polygon;
import com.android.svg.support.svg.model.SvgConstants;
import com.android.svg.support.svg.parser.SvgNodeAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Build polygon's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/23 9:57
 */

public class PolygonAttributeParser extends SvgNodeAbstractAttributeParser<Polygon> {

    @Override
    public void parse(Element element, Polygon polygon) throws DocumentException {
        super.parse(element, polygon);
        polygon.points = parseString(element, SvgConstants.ATTR_POINTS);
        polygon.toPath();
    }
}