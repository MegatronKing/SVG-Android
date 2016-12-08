package com.github.megatronking.svg.generator.svg.parser.attribute;

import com.github.megatronking.svg.generator.svg.model.Polyline;
import com.github.megatronking.svg.generator.svg.model.SvgConstants;
import com.github.megatronking.svg.generator.svg.parser.SvgNodeAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Build polygon's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/23 9:57
 */

public class PolylineAttributeParser extends SvgNodeAbstractAttributeParser<Polyline> {

    @Override
    public void parse(Element element, Polyline polyline) throws DocumentException {
        super.parse(element, polyline);
        polyline.points = parseString(element, SvgConstants.ATTR_POINTS);
        polyline.toPath();
    }
}