package com.android.svg.support.svg.parser.element;

import com.android.svg.support.svg.model.G;
import com.android.svg.support.svg.parser.SvgGroupNodeAbstractElementParser;
import com.android.svg.support.svg.parser.SvgParserImpl;

/**
 * Parse the svg's group element.
 *
 * @author Megatron King
 * @since 2016/11/23 10:46
 */

public class GElementParser extends SvgGroupNodeAbstractElementParser<G> {

    public GElementParser() {
        super(SvgParserImpl.G_ATTRIBUTE_PARSER);
    }

}