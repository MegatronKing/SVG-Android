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
package com.github.megatronking.svg.generator.svg.parser;

import com.github.megatronking.svg.generator.svg.model.SvgConstants;
import com.github.megatronking.svg.generator.svg.model.SvgNode;
import com.github.megatronking.svg.generator.svg.utils.StyleUtils;
import com.github.megatronking.svg.generator.svg.utils.TransformUtils;
import com.github.megatronking.svg.generator.utils.SCU;
import com.github.megatronking.svg.generator.xml.CommonAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.HashMap;

/**
 * All svg nodes have some common attribute, we handle them in this class.
 *
 * @author Megatron King
 * @since 2016/11/25 15:33
 */

public abstract class SvgNodeAbstractAttributeParser<T extends SvgNode> extends CommonAbstractAttributeParser<T> {

    @Override
    public void parse(Element element, T node) throws DocumentException {
        handleCommonAttributes(element, node);
    }

    private void handleCommonAttributes(Element element, SvgNode node) {
        node.id = parseString(element, SvgConstants.ATTR_ID);
        node.clazz = parseString(element, SvgConstants.ATTR_CLASS);
        String matrix = parseString(element, SvgConstants.ATTR_TRANSFORM);
        if (matrix != null) {
            if (matrix.startsWith("matrix")) {
                node.matrix = new float[6];
                node.matrix[0] = 1.0f;
                node.matrix[3] = 1.0f;
                String[] matrixValues = matrix.substring("matrix(".length(), matrix.length() - 1).split(",");
                for (int i = 0; i < matrixValues.length; i++) {
                    node.matrix[i] = SCU.parseFloat(matrixValues[i].trim(), node.matrix[i]);
                }
            } else {
                node.matrix = TransformUtils.formatTransform(matrix);
            }
        }
        node.styleMaps = StyleUtils.convertStyleString2Map(parseString(element, SvgConstants.ATTR_STYLE));
        if (node.styleMaps == null) {
            node.styleMaps = new HashMap<>();
        }
        String display = parseString(element, SvgConstants.ATTR_DISPLAY);
        if (display != null) {
            node.styleMaps.put(SvgConstants.ATTR_DISPLAY, display.trim());
        }
        String strokeWidth = parseString(element, SvgConstants.ATTR_STROKE_WIDTH);
        if (strokeWidth != null) {
            node.styleMaps.put(SvgConstants.ATTR_STROKE_WIDTH, strokeWidth);
        }
        String strokeColor = parseString(element, SvgConstants.ATTR_STROKE);
        if (strokeColor != null) {
            node.styleMaps.put(SvgConstants.ATTR_STROKE, strokeColor);
        }
        String strokeLineJoin = parseString(element, SvgConstants.ATTR_STROKE_LINEJOINE);
        if (strokeLineJoin != null) {
            node.styleMaps.put(SvgConstants.ATTR_STROKE_LINEJOINE, strokeLineJoin.trim());
        }
        String strokeLineCap = parseString(element, SvgConstants.ATTR_STROKE_LINECAP);
        if (strokeLineCap != null) {
            node.styleMaps.put(SvgConstants.ATTR_STROKE_LINECAP, strokeLineCap.trim());
        }
        String strokeMiterLimit = parseString(element, SvgConstants.ATTR_STROKE_MITERLIMIT);
        if (strokeMiterLimit != null) {
            node.styleMaps.put(SvgConstants.ATTR_STROKE_MITERLIMIT, strokeMiterLimit);
        }
        String strokeOpacity = parseString(element, SvgConstants.ATTR_STROKE_OPACITY);
        if (strokeOpacity != null) {
            node.styleMaps.put(SvgConstants.ATTR_STROKE_OPACITY, strokeOpacity);
        }
        String fillColor = parseString(element, SvgConstants.ATTR_FILL);
        if (fillColor != null) {
            node.styleMaps.put(SvgConstants.ATTR_FILL, fillColor);
        }
        String fillOpacity = parseString(element, SvgConstants.ATTR_FILL_OPACITY);
        if (fillOpacity != null) {
            node.styleMaps.put(SvgConstants.ATTR_FILL_OPACITY, fillOpacity);
        }
        String fillRule = parseString(element, SvgConstants.ATTR_FILL_RULE);
        if (fillRule != null) {
            node.styleMaps.put(SvgConstants.ATTR_FILL_RULE, fillRule.trim());
        }
    }

}
