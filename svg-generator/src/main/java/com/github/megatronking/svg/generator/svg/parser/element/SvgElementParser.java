package com.github.megatronking.svg.generator.svg.parser.element;

import com.github.megatronking.svg.generator.svg.model.Defs;
import com.github.megatronking.svg.generator.svg.model.Svg;
import com.github.megatronking.svg.generator.svg.model.SvgConstants;
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

        // Apply styles from the root node of svg tree, until all leaf nodes was traversed.
        svg.applyStyles(null, null);
        // Transform from the root node of svg tree, until all leaf nodes was traversed.
        svg.transform(1.0f, 0, 0, 1.0f, 0, 0);
    }

    @Override
    protected void parseChild(Element childElement, Svg groupNode) throws DocumentException {
        super.parseChild(childElement, groupNode);
        if (SvgConstants.TAG_DEFS.equals(childElement.getName())) {
            Defs defs = new Defs();
            groupNode.children.add(defs);
            SvgParserImpl.DEFS_ELEMENT_PARSER.parse(childElement, defs);
        }
    }
}
