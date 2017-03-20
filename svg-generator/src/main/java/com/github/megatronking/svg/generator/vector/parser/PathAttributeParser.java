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


import com.github.megatronking.svg.generator.vector.model.Path;
import com.github.megatronking.svg.generator.vector.model.VectorConstants;
import com.github.megatronking.svg.generator.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

/**
 * Build path's field values from attributes of the element.
 *
 * @author Megatron King
 * @since 2016/9/1 10:05
 */

public class PathAttributeParser extends CommonAbstractAttributeParser<Path> {

    @Override
    public void parse(Element element, Path path) {
        path.name = parseString(element, VectorConstants.ATTR_NAME);
        path.fillColor = parseColor(element, VectorConstants.ATTR_FILL_COLOR);
        path.pathData = parseString(element, VectorConstants.ATTR_PATH_DATA);
        path.fillAlpha = parseFloat(element, VectorConstants.ATTR_FILL_ALPHA, 1.0f);
        path.fillType = parseString(element, VectorConstants.ATTR_FILL_TYPE);
        path.strokeLineCap = parseString(element, VectorConstants.ATTR_STROKE_LINE_CAP, "butt");
        path.strokeLineJoin = parseString(element, VectorConstants.ATTR_STROKE_LINE_JOIN, "miter");
        path.strokeMiterLimit = parseFloat(element, VectorConstants.ATTR_STROKE_MITER_LIMIT, 4);
        path.strokeColor = parseColor(element, VectorConstants.ATTR_STROKE_COLOR);
        path.strokeAlpha = parseFloat(element, VectorConstants.ATTR_STROKE_ALPHA, 1.0f);
        path.strokeWidth = parseFloat(element, VectorConstants.ATTR_STROKE_WIDTH);
        path.trimPathEnd = parseFloat(element, VectorConstants.ATTR_TRIM_PATH_END, 1);
        path.trimPathOffset = parseFloat(element, VectorConstants.ATTR_TRIM_PATH_OFFSET);
        path.trimPathStart = parseFloat(element, VectorConstants.ATTR_TRIM_PATH_START);
    }
}
