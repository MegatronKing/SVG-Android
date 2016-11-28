package com.android.svg.support.svg.parser.attribute;

import com.android.svg.support.svg.model.G;
import com.android.svg.support.svg.parser.SvgNodeAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Build groups's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/22 19:00
 */

public class GAttributeParser extends SvgNodeAbstractAttributeParser<G> {

    @Override
    public void parse(Element element, G group)  throws DocumentException {
        super.parse(element, group);
        // Nothing to parse.
    }

}
