/*
 * Copyright (C) 2017, Megatron King
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
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
