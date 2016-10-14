package com.android.svg.support.vector.parser;


import com.android.svg.support.model.Group;
import com.android.svg.support.model.Path;
import com.android.svg.support.model.VectorConstants;

import org.dom4j.Element;

/**
 * Build groups's groups and paths values from the children of the element.
 *
 * @author Megatron King
 * @since 2016/9/1 11:00
 */

public class GroupElementParser extends ChildrenElementParser<Group> {

    public GroupElementParser() {
        super(ParserImpl.GROUP_ATTRIBUTE_PARSER);
    }

    @Override
    protected void parseChild(Element childElement, Group group) {
        if (VectorConstants.TAG_GROUP.equals(childElement.getName())) {
            Group childGroup = new Group(group);
            group.children.add(childGroup);
            ParserImpl.GROUP_ELEMENT_PARSER.parse(childElement, childGroup);
        }
        if (VectorConstants.TAG_PATH.equals(childElement.getName())) {
            Path childPath = new Path(group);
            group.children.add(childPath);
            ParserImpl.PATH_ATTRIBUTE_PARSER.parse(childElement, childPath);
        }
        if (VectorConstants.TAG_CLIP_PATH.equals(childElement.getName())) {
            Path childPath = new Path(group);
            group.children.add(childPath);
            ParserImpl.CLIP_PATH_ATTRIBUTE_PARSER.parse(childElement, childPath);
        }
    }
}
