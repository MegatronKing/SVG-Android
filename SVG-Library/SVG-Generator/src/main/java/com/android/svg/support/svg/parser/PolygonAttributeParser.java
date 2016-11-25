package com.android.svg.support.svg.parser;

import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

/**
 * Build polygon's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/23 9:57
 */

public class PolygonAttributeParser extends CommonAbstractAttributeParser<com.android.svg.support.svg.model.Polygon> {

    @Override
    public void parse(Element element, com.android.svg.support.svg.model.Polygon polygon) {
        polygon.points = parseString(element, com.android.svg.support.svg.model.SvgConstants.ATTR_POINTS);
        polygon.styleMaps = com.android.svg.support.svg.utils.StyleUtils.convertStyleString2Map(parseString(element, com.android.svg.support.svg.model.SvgConstants.ATTR_STYLE));
        polygon.toPath();
    }
}