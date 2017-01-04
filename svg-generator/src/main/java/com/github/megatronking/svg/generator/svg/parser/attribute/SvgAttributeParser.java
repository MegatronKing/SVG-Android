package com.github.megatronking.svg.generator.svg.parser.attribute;

import com.github.megatronking.svg.generator.svg.model.Svg;
import com.github.megatronking.svg.generator.svg.model.SvgConstants;
import com.github.megatronking.svg.generator.svg.parser.SvgNodeAbstractAttributeParser;
import com.github.megatronking.svg.generator.utils.SCU;
import com.github.megatronking.svg.generator.utils.TextUtils;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Build svg's field values from attributes of the element.
 *
 * @author Megatron King
 * @since 2016/11/22 18:31
 */

public class SvgAttributeParser extends SvgNodeAbstractAttributeParser<Svg> {

    @Override
    public void parse(Element element, Svg svg) throws DocumentException {
        super.parse(element, svg);
        svg.w = parseDimen(element, SvgConstants.ATTR_WIDTH);
        svg.h = parseDimen(element, SvgConstants.ATTR_HEIGHT);
        // Fix a case-sensitive or multi-name issue, such as 'viewbox' or 'viewBox' or 'viewPort' or 'viewport'
        String viewBox = parseString(element, SvgConstants.ATTR_VIEW_BOX1, null);
        if (viewBox == null) {
            viewBox = parseString(element, SvgConstants.ATTR_VIEW_BOX2, null);
        }
        if (viewBox == null) {
            viewBox = parseString(element, SvgConstants.ATTR_VIEW_PORT1, null);
        }
        if (viewBox == null) {
            viewBox = parseString(element, SvgConstants.ATTR_VIEW_PORT2, null);
        }
        if (viewBox != null) {
            viewBox = TextUtils.removeMultiSpace(viewBox);
            String[] viewBoxValues = viewBox.trim().split(" ");
            svg.viewBox = new float[4];
            for(int i = 0; i < viewBoxValues.length; i++) {
                svg.viewBox[i] = SCU.parseFloat(viewBoxValues[i], 0.0f);
            }
        } else {
            svg.viewBox = new float[4];
        }
        if (svg.w != 0) {
            svg.viewBox[2] = svg.w;
        } else {
            svg.w = svg.viewBox[2];
        }
        if (svg.h != 0) {
            svg.viewBox[3] = svg.h;
        } else {
            svg.h = svg.viewBox[3];
        }
    }
}
