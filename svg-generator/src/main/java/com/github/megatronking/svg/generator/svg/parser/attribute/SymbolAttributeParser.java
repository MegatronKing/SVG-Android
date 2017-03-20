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
package com.github.megatronking.svg.generator.svg.parser.attribute;

import com.github.megatronking.svg.generator.svg.model.SvgConstants;
import com.github.megatronking.svg.generator.svg.model.Symbol;
import com.github.megatronking.svg.generator.svg.parser.SvgNodeAbstractAttributeParser;
import com.github.megatronking.svg.generator.utils.SCU;
import com.github.megatronking.svg.generator.utils.TextUtils;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Build symbol's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2017/1/12 10:11
 */

public class SymbolAttributeParser extends SvgNodeAbstractAttributeParser<Symbol> {

    @Override
    public void parse(Element element, Symbol symbol) throws DocumentException {
        super.parse(element, symbol);
        // Fix a case-sensitive or multi-name issue, such as 'viewbox' or 'viewBox' or 'viewPort' or 'viewport'
        String viewBox = parseString(element, SvgConstants.ATTR_VIEW_BOX1, null);
        if (viewBox == null) {
            viewBox = parseString(element, SvgConstants.ATTR_VIEW_BOX2, null);
        }
        if (viewBox == null) {
            viewBox = parseString(element, SvgConstants.ATTR_VIEW_PORT1, null);
        }
        if (viewBox == null) {
            viewBox = parseString(element, SvgConstants.ATTR_VIEW_PORT2, null);
        }
        if (viewBox != null) {
            viewBox = TextUtils.removeMultiSpace(viewBox);
            String[] viewBoxValues = viewBox.trim().split(" ");
            symbol.viewBox = new float[4];
            for(int i = 0; i < viewBoxValues.length; i++) {
                symbol.viewBox[i] = SCU.parseFloat(viewBoxValues[i], 0.0f);
            }
        } else {
            symbol.viewBox = new float[4];
        }
    }

}
