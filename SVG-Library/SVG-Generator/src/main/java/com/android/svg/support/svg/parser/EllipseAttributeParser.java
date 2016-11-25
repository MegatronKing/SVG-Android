package com.android.svg.support.svg.parser;

import com.android.svg.support.svg.model.SvgConstants;
import com.android.svg.support.svg.utils.StyleUtils;
import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

/**
 * Build ellipse's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/24 20:20
 */

public class EllipseAttributeParser extends CommonAbstractAttributeParser<com.android.svg.support.svg.model.Ellipse> {

    @Override
    public void parse(Element element, com.android.svg.support.svg.model.Ellipse ellipse) {
        ellipse.cx = parseFloat(element, SvgConstants.ATTR_CX);
        ellipse.cy = parseFloat(element, SvgConstants.ATTR_CY);
        ellipse.rx = parseFloat(element, SvgConstants.ATTR_RX);
        ellipse.ry = parseFloat(element, SvgConstants.ATTR_RY);
        ellipse.styleMaps = StyleUtils.convertStyleString2Map(parseString(element, SvgConstants.ATTR_STYLE));
        ellipse.toPath();
    }
}
