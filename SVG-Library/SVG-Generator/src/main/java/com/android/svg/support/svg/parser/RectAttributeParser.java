package com.android.svg.support.svg.parser;

import com.android.svg.support.svg.model.Rect;
import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

/**
 * Build rect's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/22 19:00
 */

public class RectAttributeParser extends CommonAbstractAttributeParser<Rect>{

    @Override
    public void parse(Element element, Rect rect) {
        rect.x = parseFloat(element, com.android.svg.support.svg.model.SvgConstants.ATTR_X);
        rect.y = parseFloat(element, com.android.svg.support.svg.model.SvgConstants.ATTR_Y);
        rect.width = parseFloat(element, com.android.svg.support.svg.model.SvgConstants.ATTR_WIDTH);
        rect.height = parseFloat(element, com.android.svg.support.svg.model.SvgConstants.ATTR_HEIGHT);
        rect.styleMaps = com.android.svg.support.svg.utils.StyleUtils.convertStyleString2Map(parseString(element, com.android.svg.support.svg.model.SvgConstants.ATTR_STYLE));
        rect.toPath();
    }
}
