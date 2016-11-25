package com.android.svg.support.svg.parser;


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
    PolygonAttributeParser POLYGON_ATTRIBUTE_PARSER = new PolygonAttributeParser();
    RectAttributeParser RECT_ATTRIBUTE_PARSER = new RectAttributeParser();
    PathAttributeParser PATH_ATTRIBUTE_PARSER = new PathAttributeParser();

    // elements
    SvgElementParser SVG_ELEMENT_PARSER = new SvgElementParser();
    GElementParser G_ELEMENT_PARSER = new GElementParser();
}
