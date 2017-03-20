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
import com.github.megatronking.svg.generator.vector.model.Path;
import com.github.megatronking.svg.generator.vector.model.VectorConstants;
import com.github.megatronking.svg.generator.xml.ChildrenElementParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Build groups's groups and paths values from the children of the element.
 *
 * @author Megatron King
 * @since 2016/9/1 11:00
 */

public class GroupElementParser extends ChildrenElementParser<Group> {

    public GroupElementParser() {
        super(VectorParserImpl.GROUP_ATTRIBUTE_PARSER);
    }

    @Override
    protected void parseChild(Element childElement, Group group) throws DocumentException {
        if (VectorConstants.TAG_GROUP.equals(childElement.getName())) {
            Group childGroup = new Group(group);
            group.children.add(childGroup);
            VectorParserImpl.GROUP_ELEMENT_PARSER.parse(childElement, childGroup);
        }
        if (VectorConstants.TAG_PATH.equals(childElement.getName())) {
            Path childPath = new Path(group);
            group.children.add(childPath);
            VectorParserImpl.PATH_ATTRIBUTE_PARSER.parse(childElement, childPath);
        }
        if (VectorConstants.TAG_CLIP_PATH.equals(childElement.getName())) {
            Path childPath = new Path(group);
            group.children.add(childPath);
            VectorParserImpl.CLIP_PATH_ATTRIBUTE_PARSER.parse(childElement, childPath);
        }
    }
}
