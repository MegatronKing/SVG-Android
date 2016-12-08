package com.android.svg.support.svg.parser.attribute;

import com.android.svg.support.svg.model.Path;
import com.android.svg.support.svg.model.SvgConstants;
import com.android.svg.support.svg.parser.SvgNodeAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;


/**
 * Build path's field values from attributes of the svg element.
 *
 * @author Megatron King
 * @since 2016/11/23 11:13
 */

public class PathAttributeParser extends SvgNodeAbstractAttributeParser<Path> {

    @Override
    public void parse(Element element, Path path) throws DocumentException {
        super.parse(element, path);
        path.d = parseString(element, SvgConstants.ATTR_D);
        path.toPath();
    }

}