package com.android.svg.support.svg.parser.attribute;

import com.android.svg.support.svg.model.Ellipse;
import com.android.svg.support.svg.model.SvgConstants;
import com.android.svg.support.svg.parser.SvgNodeAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Build ellipse's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/24 20:20
 */

public class EllipseAttributeParser extends SvgNodeAbstractAttributeParser<Ellipse> {

    @Override
    public void parse(Element element, Ellipse ellipse) throws DocumentException {
        super.parse(element, ellipse);
        ellipse.cx = parseFloat(element, SvgConstants.ATTR_CX);
        ellipse.cy = parseFloat(element, SvgConstants.ATTR_CY);
        ellipse.rx = parseFloat(element, SvgConstants.ATTR_RX);
        ellipse.ry = parseFloat(element, SvgConstants.ATTR_RY);
        ellipse.toPath();
    }
}
