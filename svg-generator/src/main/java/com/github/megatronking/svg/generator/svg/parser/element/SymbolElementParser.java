package com.github.megatronking.svg.generator.svg.parser.element;

import com.github.megatronking.svg.generator.svg.model.Symbol;
import com.github.megatronking.svg.generator.svg.parser.SvgGroupNodeAbstractElementParser;
import com.github.megatronking.svg.generator.svg.parser.attribute.SymbolAttributeParser;

/**
 * Parse the svg's symbol element.
 *
 * @author Megatron King
 * @since 2016/11/23 10:46
 */

public class SymbolElementParser extends SvgGroupNodeAbstractElementParser<Symbol> {

    public SymbolElementParser() {
        super(new SymbolAttributeParser());
    }
}