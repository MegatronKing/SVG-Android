package com.github.megatronking.svg.generator.svg.parser.element;

import com.github.megatronking.svg.generator.svg.model.Svg;
import com.github.megatronking.svg.generator.svg.parser.SvgGroupNodeAbstractElementParser;
import com.github.megatronking.svg.generator.svg.parser.SvgParserImpl;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Parse the svg's root element.
 *
 * @author Megatron King
 * @since 2016/11/22 18:12
 */

public class SvgElementParser extends SvgGroupNodeAbstractElementParser<Svg> {

    public SvgElementParser() {
        super(SvgParserImpl.SVG_ATTRIBUTE_PARSER);
    }

    @Override
    public void parse(Element element, Svg svg) throws DocumentException {
        super.parse(element, svg);
        svg.parseEnd();
    }
}
