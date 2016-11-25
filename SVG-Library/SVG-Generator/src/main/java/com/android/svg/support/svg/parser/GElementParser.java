package com.android.svg.support.svg.parser;

import com.android.svg.support.svg.model.Line;
import com.android.svg.support.svg.model.Rect;
import com.android.svg.support.xml.ChildrenElementParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Parse the svg's group element.
 *
 * @author Megatron King
 * @since 2016/11/23 10:46
 */

public class GElementParser extends ChildrenElementParser<com.android.svg.support.svg.model.G> {

    public GElementParser() {
        super(SvgParserImpl.G_ATTRIBUTE_PARSER);
    }

    @Override
    protected void parseChild(Element childElement, com.android.svg.support.svg.model.G g) throws DocumentException {
        if (com.android.svg.support.svg.model.SvgConstants.TAG_GROUP.equals(childElement.getName())) {
            com.android.svg.support.svg.model.G group = new com.android.svg.support.svg.model.G();
            g.children.add(group);
            SvgParserImpl.G_ELEMENT_PARSER.parse(childElement, group);
        }
        if (com.android.svg.support.svg.model.SvgConstants.TAG_CIRCLE.equals(childElement.getName())) {
            com.android.svg.support.svg.model.Circle circle = new com.android.svg.support.svg.model.Circle();
            g.children.add(circle);
            SvgParserImpl.CIRCLE_ATTRIBUTE_PARSER.parse(childElement, circle);
        }
        if (com.android.svg.support.svg.model.SvgConstants.TAG_ELLIPSE.equals(childElement.getName())) {
            com.android.svg.support.svg.model.Ellipse ellipse = new com.android.svg.support.svg.model.Ellipse();
            g.children.add(ellipse);
            SvgParserImpl.ELLIPSE_ATTRIBUTE_PARSER.parse(childElement, ellipse);
        }
        if (com.android.svg.support.svg.model.SvgConstants.TAG_LINE.equals(childElement.getName())) {
            Line line = new Line();
            g.children.add(line);
            SvgParserImpl.LINE_ATTRIBUTE_PARSER.parse(childElement, line);
        }
        if (com.android.svg.support.svg.model.SvgConstants.TAG_RECT.equals(childElement.getName())) {
            Rect rect = new Rect();
            g.children.add(rect);
            SvgParserImpl.RECT_ATTRIBUTE_PARSER.parse(childElement, rect);
        }
        if (com.android.svg.support.svg.model.SvgConstants.TAG_POLYGON.equals(childElement.getName())) {
            com.android.svg.support.svg.model.Polygon polygon = new com.android.svg.support.svg.model.Polygon();
            g.children.add(polygon);
            SvgParserImpl.POLYGON_ATTRIBUTE_PARSER.parse(childElement, polygon);
        }
        if (com.android.svg.support.svg.model.SvgConstants.TAG_PATH.equals(childElement.getName())) {
            com.android.svg.support.svg.model.Path path = new com.android.svg.support.svg.model.Path();
            g.children.add(path);
            SvgParserImpl.PATH_ATTRIBUTE_PARSER.parse(childElement, path);
        }
    }
}