package com.github.megatronking.svg.generator.svg.parser.attribute;

import com.github.megatronking.svg.generator.svg.model.SvgConstants;
import com.github.megatronking.svg.generator.svg.model.Use;
import com.github.megatronking.svg.generator.svg.parser.SvgNodeAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;


/**
 * Build use's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/23 11:13
 */

public class UseAttributeParser extends SvgNodeAbstractAttributeParser<Use> {

    @Override
    public void parse(Element element, Use use) throws DocumentException {
        super.parse(element, use);
        use.x = parseFloat(element, SvgConstants.ATTR_X);
        use.y = parseFloat(element, SvgConstants.ATTR_Y);
        use.width = parseFloat(element, SvgConstants.ATTR_WIDTH);
        use.height = parseFloat(element, SvgConstants.ATTR_HEIGHT);
        use.href = parseString(element, SvgConstants.ATTR_HREF);
    }

}