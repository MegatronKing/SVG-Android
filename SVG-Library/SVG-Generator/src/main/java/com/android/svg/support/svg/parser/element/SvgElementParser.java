package com.android.svg.support.svg.parser.element;

import com.android.svg.support.svg.model.Svg;
import com.android.svg.support.svg.parser.SvgGroupNodeAbstractElementParser;
import com.android.svg.support.svg.parser.SvgParserImpl;

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
        // Apply styles from the root node of svg tree, until all leaf nodes was traversed.
        svg.applyStyles(null);
        // Transform from the root node of svg tree, until all leaf nodes was traversed.
        svg.transform(1.0f, 0, 0, 1.0f, 0, 0);
    }
}
