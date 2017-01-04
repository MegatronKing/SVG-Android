package com.github.megatronking.svg.generator.svg.parser.element;

import com.github.megatronking.svg.generator.svg.model.Defs;
import com.github.megatronking.svg.generator.svg.parser.SvgGroupNodeAbstractElementParser;
import com.github.megatronking.svg.generator.svg.parser.EmptyAttributeParser;

/**
 * Parse the svg's defs element.
 *
 * @author Megatron King
 * @since 2016/11/23 10:46
 */

public class DefsElementParser extends SvgGroupNodeAbstractElementParser<Defs> {

    public DefsElementParser() {
        super(new EmptyAttributeParser<>());
    }
}