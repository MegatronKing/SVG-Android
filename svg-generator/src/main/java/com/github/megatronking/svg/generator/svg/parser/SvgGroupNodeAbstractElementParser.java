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

import com.github.megatronking.svg.generator.svg.model.Circle;
import com.github.megatronking.svg.generator.svg.model.Defs;
import com.github.megatronking.svg.generator.svg.model.Ellipse;
import com.github.megatronking.svg.generator.svg.model.G;
import com.github.megatronking.svg.generator.svg.model.Line;
import com.github.megatronking.svg.generator.svg.model.Path;
import com.github.megatronking.svg.generator.svg.model.Polygon;
import com.github.megatronking.svg.generator.svg.model.Polyline;
import com.github.megatronking.svg.generator.svg.model.Rect;
import com.github.megatronking.svg.generator.svg.model.Style;
import com.github.megatronking.svg.generator.svg.model.SvgConstants;
import com.github.megatronking.svg.generator.svg.model.SvgGroupNode;
import com.github.megatronking.svg.generator.svg.model.Symbol;
import com.github.megatronking.svg.generator.svg.model.Use;
import com.github.megatronking.svg.generator.xml.ChildrenElementParser;
import com.github.megatronking.svg.generator.xml.IAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;


/**
 * The svg group nodes have some common child element, we handle them in this class.
 *
 * @author Megatron King
 * @since 2016/11/25 16:03
 */

public abstract class SvgGroupNodeAbstractElementParser<T extends SvgGroupNode> extends ChildrenElementParser<T> {

    public SvgGroupNodeAbstractElementParser(IAttributeParser<T> attributeParser) {
        super(attributeParser);
    }

    @Override
    protected void parseChild(Element childElement, T groupNode) throws DocumentException {
        if (SvgConstants.TAG_GROUP.equals(childElement.getName())) {
            G group = new G();
            groupNode.children.add(group);
            SvgParserImpl.G_ELEMENT_PARSER.parse(childElement, group);
        }
        if (SvgConstants.TAG_CIRCLE.equals(childElement.getName())) {
            Circle circle = new Circle();
            groupNode.children.add(circle);
            SvgParserImpl.CIRCLE_ATTRIBUTE_PARSER.parse(childElement, circle);
        }
        if (SvgConstants.TAG_ELLIPSE.equals(childElement.getName())) {
            Ellipse ellipse = new Ellipse();
            groupNode.children.add(ellipse);
            SvgParserImpl.ELLIPSE_ATTRIBUTE_PARSER.parse(childElement, ellipse);
        }
        if (SvgConstants.TAG_LINE.equals(childElement.getName())) {
            Line line = new Line();
            groupNode.children.add(line);
            SvgParserImpl.LINE_ATTRIBUTE_PARSER.parse(childElement, line);
        }
        if (SvgConstants.TAG_RECT.equals(childElement.getName())) {
            Rect rect = new Rect();
            groupNode.children.add(rect);
            SvgParserImpl.RECT_ATTRIBUTE_PARSER.parse(childElement, rect);
        }
        if (SvgConstants.TAG_POLYGON.equals(childElement.getName())) {
            Polygon polygon = new Polygon();
            groupNode.children.add(polygon);
            SvgParserImpl.POLYGON_ATTRIBUTE_PARSER.parse(childElement, polygon);
        }
        if (SvgConstants.TAG_POLYLINE.equals(childElement.getName())) {
            Polyline polyline = new Polyline();
            groupNode.children.add(polyline);
            SvgParserImpl.POLYLINE_ATTRIBUTE_PARSER.parse(childElement, polyline);
        }
        if (SvgConstants.TAG_PATH.equals(childElement.getName())) {
            Path path = new Path();
            groupNode.children.add(path);
            SvgParserImpl.PATH_ATTRIBUTE_PARSER.parse(childElement, path);
        }
        if (SvgConstants.TAG_USE.equals(childElement.getName())) {
            Use use = new Use();
            groupNode.children.add(use);
            SvgParserImpl.USE_ATTRIBUTE_PARSER.parse(childElement, use);
        }
        if (SvgConstants.TAG_STYLE.equals(childElement.getName())) {
            String styleText = childElement.getText();
            if (styleText != null && styleText.length() > 0) {
                Style style = new Style();
                style.cssStyle = styleText;
                groupNode.children.add(style);
            }
        }
        if (SvgConstants.TAG_DEFS.equals(childElement.getName())) {
            Defs defs = new Defs();
            groupNode.children.add(defs);
            SvgParserImpl.DEFS_ELEMENT_PARSER.parse(childElement, defs);
        }
        if (SvgConstants.TAG_SYMBOL.equals(childElement.getName())) {
            Symbol symbol = new Symbol();
            groupNode.children.add(symbol);
            SvgParserImpl.SYMBOL_ELEMENT_PARSER.parse(childElement, symbol);
        }
    }
}
