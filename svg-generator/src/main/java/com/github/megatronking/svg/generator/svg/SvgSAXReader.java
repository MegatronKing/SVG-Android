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
package com.github.megatronking.svg.generator.svg;

import com.github.megatronking.svg.generator.svg.model.Svg;
import com.github.megatronking.svg.generator.svg.model.SvgConstants;
import com.github.megatronking.svg.generator.svg.parser.SvgParserImpl;
import com.github.megatronking.svg.generator.xml.SimpleImplementSAXReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.List;

/**
 * Read svg xml file to a {@link Svg} Object.
 *
 * @author Megatron King
 * @since 2016/11/22 17:39
 */

public class SvgSAXReader extends SimpleImplementSAXReader<Svg> {

    @Override
    protected Svg parseDocument(Document document) throws DocumentException {
        Element svgElement = document.getRootElement();
        // simple validate
        if (!SvgConstants.TAG_SVG.equals(svgElement.getName())) {
            throw new SvgParseException("The root element must be " + SvgConstants.TAG_SVG);
        }
        List<?> childElements = svgElement.elements();
        if (childElements == null || childElements.isEmpty()) {
            throw new SvgParseException("There is no child node in the svg");
        }
        Svg svg = new Svg();
        SvgParserImpl.SVG_ELEMENT_PARSER.parse(svgElement, svg);
        return svg;
    }
}
