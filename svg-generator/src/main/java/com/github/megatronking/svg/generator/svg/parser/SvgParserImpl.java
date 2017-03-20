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

import com.github.megatronking.svg.generator.svg.parser.attribute.CircleAttributeParser;
import com.github.megatronking.svg.generator.svg.parser.attribute.EllipseAttributeParser;
import com.github.megatronking.svg.generator.svg.parser.attribute.GAttributeParser;
import com.github.megatronking.svg.generator.svg.parser.attribute.LineAttributeParser;
import com.github.megatronking.svg.generator.svg.parser.attribute.PathAttributeParser;
import com.github.megatronking.svg.generator.svg.parser.attribute.PolygonAttributeParser;
import com.github.megatronking.svg.generator.svg.parser.attribute.PolylineAttributeParser;
import com.github.megatronking.svg.generator.svg.parser.attribute.RectAttributeParser;
import com.github.megatronking.svg.generator.svg.parser.attribute.SvgAttributeParser;
import com.github.megatronking.svg.generator.svg.parser.attribute.UseAttributeParser;
import com.github.megatronking.svg.generator.svg.parser.element.DefsElementParser;
import com.github.megatronking.svg.generator.svg.parser.element.GElementParser;
import com.github.megatronking.svg.generator.svg.parser.element.SvgElementParser;
import com.github.megatronking.svg.generator.svg.parser.element.SymbolElementParser;

/**
 * The impl svg parsers.
 *
 * @author Megatron King
 * @since 2016/9/1 14:07
 */

public interface SvgParserImpl {

    // attributes
    SvgAttributeParser SVG_ATTRIBUTE_PARSER = new SvgAttributeParser();
    GAttributeParser G_ATTRIBUTE_PARSER = new GAttributeParser();
    CircleAttributeParser CIRCLE_ATTRIBUTE_PARSER = new CircleAttributeParser();
    EllipseAttributeParser ELLIPSE_ATTRIBUTE_PARSER = new EllipseAttributeParser();
    LineAttributeParser LINE_ATTRIBUTE_PARSER = new LineAttributeParser();
    PolylineAttributeParser POLYLINE_ATTRIBUTE_PARSER = new PolylineAttributeParser();
    PolygonAttributeParser POLYGON_ATTRIBUTE_PARSER = new PolygonAttributeParser();
    RectAttributeParser RECT_ATTRIBUTE_PARSER = new RectAttributeParser();
    PathAttributeParser PATH_ATTRIBUTE_PARSER = new PathAttributeParser();
    UseAttributeParser USE_ATTRIBUTE_PARSER = new UseAttributeParser();

    // elements
    SvgElementParser SVG_ELEMENT_PARSER = new SvgElementParser();
    GElementParser G_ELEMENT_PARSER = new GElementParser();
    DefsElementParser DEFS_ELEMENT_PARSER = new DefsElementParser();
    SymbolElementParser SYMBOL_ELEMENT_PARSER = new SymbolElementParser();

}
