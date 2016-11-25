package com.android.svg.support.svgnew.parser;

import com.android.svg.support.svgnew.model.Polygon;
import com.android.svg.support.svgnew.model.SvgConstants;
import com.android.svg.support.svgnew.utils.StyleUtils;
import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

/**
 * Build polygon's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/23 9:57
 */

public class PolygonAttributeParser extends CommonAbstractAttributeParser<Polygon> {

    @Override
    public void parse(Element element, Polygon polygon) {
        polygon.points = parseString(element, SvgConstants.ATTR_POINTS);
        polygon.styleMaps = StyleUtils.convertStyleString2Map(parseString(element, SvgConstants.ATTR_STYLE));
        polygon.toPath();
    }
}