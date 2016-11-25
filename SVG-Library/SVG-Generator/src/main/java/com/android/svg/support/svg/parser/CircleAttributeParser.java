package com.android.svg.support.svg.parser;

import com.android.svg.support.svg.model.SvgConstants;
import com.android.svg.support.svg.utils.StyleUtils;
import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

/**
 * Build circle's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/22 19:00
 */

public class CircleAttributeParser extends CommonAbstractAttributeParser<com.android.svg.support.svg.model.Circle>{

    @Override
    public void parse(Element element, com.android.svg.support.svg.model.Circle circle) {
        circle.cx = parseFloat(element, SvgConstants.ATTR_CX);
        circle.cy = parseFloat(element, SvgConstants.ATTR_CY);
        circle.r = parseFloat(element, SvgConstants.ATTR_R);
        circle.styleMaps = StyleUtils.convertStyleString2Map(parseString(element, SvgConstants.ATTR_STYLE));
        circle.toPath();
    }
}
