package com.android.svg.support.svg.parser;

import com.android.svg.support.svg.SvgParseException;
import com.android.svg.support.svg.model.Svg;
import com.android.svg.support.svg.model.SvgConstants;
import com.android.svg.support.svg.utils.StyleUtils;
import com.android.svg.support.svg.utils.TransformUtils;
import com.android.svg.support.utils.SCU;
import com.android.svg.support.xml.CommonAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Build svg's field values from attributes of the element.
 *
 * @author Megatron King
 * @since 2016/11/22 18:31
 */

public class SvgAttributeParser extends CommonAbstractAttributeParser<Svg> {

    @Override
    public void parse(Element element, Svg svg) throws DocumentException {
        svg.w = parseDimen(element, SvgConstants.ATTR_WIDTH);
        svg.h = parseDimen(element, SvgConstants.ATTR_HEIGHT);
        // Fsix a case-sensitive or multi-name issue, such as 'viewbox' or 'viewBox' or 'viewPort' or 'viewport'
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
            String[] viewBoxValues = viewBox.split(" ");
            svg.viewBox = new float[4];
            for(int i = 0; i < viewBoxValues.length; i++) {
                svg.viewBox[i] = SCU.parseFloat(viewBoxValues[i], 0.0f);
            }
        }
        if (svg.viewBox == null && svg.w != 0 && svg.h != 0) {
            svg.viewBox = new float[4];
            svg.viewBox[2] = svg.w;
            svg.viewBox[3] = svg.h;
        }
        if ((svg.w == 0 || svg.h == 0) && svg.viewBox != null && svg.viewBox[2] > 0 && svg.viewBox[3] > 0) {
            svg.w = svg.viewBox[2];
            svg.h = svg.viewBox[3];
        }
        if (svg.viewBox == null) {
            throw new SvgParseException("No viewBox attribute found in svg file.");
        }
        // The transform only supports matrix tag.
        String matrix = parseString(element, SvgConstants.ATTR_TRANSFORM);
        if (matrix != null) {
            if (matrix.startsWith("matrix")) {
                svg.matrix = new float[6];
                svg.matrix[0] = 1.0f;
                svg.matrix[3] = 1.0f;
                String[] matrixValues = matrix.substring("matrix(".length(), matrix.length() - 1).split(",");
                for (int i = 0; i < matrixValues.length; i++) {
                    svg.matrix[i] = SCU.parseFloat(matrixValues[i].trim(), svg.matrix[i]);
                }
            } else {
                svg.matrix = TransformUtils.formatTransform(matrix);
            }
        }
        svg.styleMaps = StyleUtils.convertStyleString2Map(parseString(element, SvgConstants.ATTR_STYLE));
    }
}
