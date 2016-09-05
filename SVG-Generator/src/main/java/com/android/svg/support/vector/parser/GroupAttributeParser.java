package com.android.svg.support.vector.parser;


import com.android.svg.support.model.Group;
import com.android.svg.support.model.VectorConstants;

import org.dom4j.Element;

/**
 * Build group's field values from attributes of the element.
 *
 * @author Megatron King
 * @since 2016/9/1 10:04
 */

public class GroupAttributeParser extends BaseVectorAttributeParser<Group> {

    @Override
    public void parse(Element element, Group group) {
        group.name = parseString(element, VectorConstants.ATTR_NAME);
        group.pivotX = parseFloat(element, VectorConstants.ATTR_PIVOTX);
        group.pivotY = parseFloat(element, VectorConstants.ATTR_PIVOTY);
        group.scaleX = parseFloat(element, VectorConstants.ATTR_SCALEX, 1.0f);
        group.scaleY = parseFloat(element, VectorConstants.ATTR_SCALEY, 1.0f);
        group.rotation = parseFloat(element, VectorConstants.ATTR_ROTATION);
        group.translateX = parseFloat(element, VectorConstants.ATTR_TRANSLATEX);
        group.translateY = parseFloat(element, VectorConstants.ATTR_TRANSLATEY);
    }

}
