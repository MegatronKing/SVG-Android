package com.android.svg.support.vector.parser;


import com.android.svg.support.vector.model.Path;
import com.android.svg.support.vector.model.VectorConstants;
import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

/**
 * Build path's field values from attributes of the element.
 *
 * @author Megatron King
 * @since 2016/9/1 10:05
 */

public class PathAttributeParser extends CommonAbstractAttributeParser<Path> {

    @Override
    public void parse(Element element, Path path) {
        path.name = parseString(element, VectorConstants.ATTR_NAME);
        path.fillColor = parseColor(element, VectorConstants.ATTR_FILL_COLOR);
        path.pathData = parseString(element, VectorConstants.ATTR_PATH_DATA);
        path.fillAlpha = parseFloat(element, VectorConstants.ATTR_FILL_ALPHA, 1.0f);
        path.strokeLineCap = parseString(element, VectorConstants.ATTR_STROKE_LINE_CAP, "butt");
        path.strokeLineJoin = parseString(element, VectorConstants.ATTR_STROKE_LINE_JOIN, "miter");
        path.strokeMiterLimit = parseFloat(element, VectorConstants.ATTR_STROKE_MITER_LIMIT, 4);
        path.strokeColor = parseColor(element, VectorConstants.ATTR_STROKE_COLOR);
        path.strokeAlpha = parseFloat(element, VectorConstants.ATTR_STROKE_ALPHA, 1.0f);
        path.strokeWidth = parseFloat(element, VectorConstants.ATTR_STROKE_WIDTH);
        path.trimPathEnd = parseFloat(element, VectorConstants.ATTR_TRIM_PATH_END, 1);
        path.trimPathOffset = parseFloat(element, VectorConstants.ATTR_TRIM_PATH_OFFSET);
        path.trimPathStart = parseFloat(element, VectorConstants.ATTR_TRIM_PATH_START);
    }
}
