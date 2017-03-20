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
package com.github.megatronking.svg.generator.vector.parser;


import com.github.megatronking.svg.generator.vector.model.Group;
import com.github.megatronking.svg.generator.vector.model.VectorConstants;
import com.github.megatronking.svg.generator.xml.CommonAbstractAttributeParser;

import org.dom4j.Element;

/**
 * Build group's field values from attributes of the element.
 *
 * @author Megatron King
 * @since 2016/9/1 10:04
 */

public class GroupAttributeParser extends CommonAbstractAttributeParser<Group> {

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
