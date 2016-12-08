package com.android.svg.support.vector.parser;


import com.android.svg.support.vector.model.Vector;
import com.android.svg.support.vector.model.VectorConstants;
import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

/**
 * Build vector's field values from attributes of the element.
 *
 * @author Megatron King
 * @since 2016/9/1 9:39
 */

public class VectorAttributeParser extends CommonAbstractAttributeParser<Vector> {

    @Override
    public void parse(Element element, Vector vector) {
        vector.name = parseString(element, VectorConstants.ATTR_NAME);
        vector.alpha = parseFloat(element, VectorConstants.ATTR_ALPHA, 1.0f);
        vector.width = parseString(element, VectorConstants.ATTR_WIDTH);
        vector.height = parseString(element, VectorConstants.ATTR_HEIGHT);
        vector.viewportWidth = parseFloat(element, VectorConstants.ATTR_VIEWPORT_WIDTH);
        vector.viewportHeight = parseFloat(element, VectorConstants.ATTR_VIEWPORT_HEIGHT);
        vector.autoMirrored = parseBoolean(element, VectorConstants.ATTR_AUTO_MIRRORED);
        vector.tint = parseColor(element, VectorConstants.ATTR_TINT);
        vector.tintMode = parseString(element, VectorConstants.ATTR_TINT_MODE);
    }
}
