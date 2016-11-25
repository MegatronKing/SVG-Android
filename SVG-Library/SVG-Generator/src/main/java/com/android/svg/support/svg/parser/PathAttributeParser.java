package com.android.svg.support.svg.parser;

import com.android.svg.support.svg.model.SvgConstants;
import com.android.svg.support.svg.utils.StyleUtils;
import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

import java.util.HashMap;

/**
 * Build path's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/23 11:13
 */

public class PathAttributeParser extends CommonAbstractAttributeParser<com.android.svg.support.svg.model.Path> {

    @Override
    public void parse(Element element, com.android.svg.support.svg.model.Path path) {
        path.d = parseString(element, SvgConstants.ATTR_D);
        path.styleMaps = StyleUtils.convertStyleString2Map(parseString(element, SvgConstants.ATTR_STYLE));
        if (path.styleMaps == null) {
            path.styleMaps = new HashMap<>();
        }
        String strokeWidth = parseString(element, SvgConstants.ATTR_STROKE_WIDTH);
        if (strokeWidth != null) {
            path.styleMaps.put(SvgConstants.ATTR_STROKE_WIDTH, strokeWidth);
        }
        String strokeColor = parseString(element, SvgConstants.ATTR_STROKE);
        if (strokeColor != null) {
            path.styleMaps.put(SvgConstants.ATTR_STROKE, strokeColor);
        }
        String fillColor = parseString(element, SvgConstants.ATTR_FILL);
        if (fillColor != null) {
            path.styleMaps.put(SvgConstants.ATTR_FILL, fillColor);
        }
        path.toPath();
    }

}