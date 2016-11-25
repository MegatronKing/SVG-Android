package com.android.svg.support.svgnew;

import com.android.svg.support.svgnew.model.Svg;
import com.android.svg.support.svgnew.model.SvgConstants;
import com.android.svg.support.svgnew.parser.SvgParserImpl;
import com.android.svg.support.xml.SimpleImplementSAXReader;

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
