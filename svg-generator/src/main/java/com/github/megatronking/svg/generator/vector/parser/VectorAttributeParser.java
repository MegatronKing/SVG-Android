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
package com.github.megatronking.svg.generator.vector.parser;


import com.github.megatronking.svg.generator.vector.model.Vector;
import com.github.megatronking.svg.generator.vector.model.VectorConstants;
import com.github.megatronking.svg.generator.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

/**
 * Build vector's field values from attributes of the element.
 *
 * @author Megatron King
 * @since 2016/9/1 9:39
 */

public class VectorAttributeParser extends CommonAbstractAttributeParser<Vector> {

    @Override
    public void parse(Element element, Vector vector) {
        vector.name = parseString(element, VectorConstants.ATTR_NAME);
        vector.alpha = parseFloat(element, VectorConstants.ATTR_ALPHA, 1.0f);
        vector.width = parseString(element, VectorConstants.ATTR_WIDTH);
        vector.height = parseString(element, VectorConstants.ATTR_HEIGHT);
        vector.viewportWidth = parseFloat(element, VectorConstants.ATTR_VIEWPORT_WIDTH);
        vector.viewportHeight = parseFloat(element, VectorConstants.ATTR_VIEWPORT_HEIGHT);
        vector.autoMirrored = parseBoolean(element, VectorConstants.ATTR_AUTO_MIRRORED);
        vector.tint = parseColor(element, VectorConstants.ATTR_TINT);
        vector.tintMode = parseString(element, VectorConstants.ATTR_TINT_MODE);
    }
}
