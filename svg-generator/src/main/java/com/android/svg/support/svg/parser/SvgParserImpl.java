package com.android.svg.support.svg.parser;

import com.android.svg.support.svg.parser.attribute.CircleAttributeParser;
import com.android.svg.support.svg.parser.attribute.EllipseAttributeParser;
import com.android.svg.support.svg.parser.attribute.GAttributeParser;
import com.android.svg.support.svg.parser.attribute.LineAttributeParser;
import com.android.svg.support.svg.parser.attribute.PathAttributeParser;
import com.android.svg.support.svg.parser.attribute.PolygonAttributeParser;
import com.android.svg.support.svg.parser.attribute.PolylineAttributeParser;
import com.android.svg.support.svg.parser.attribute.RectAttributeParser;
import com.android.svg.support.svg.parser.attribute.SvgAttributeParser;
import com.android.svg.support.svg.parser.element.GElementParser;
import com.android.svg.support.svg.parser.element.SvgElementParser;

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

    // elements
    SvgElementParser SVG_ELEMENT_PARSER = new SvgElementParser();
    GElementParser G_ELEMENT_PARSER = new GElementParser();

}
