package com.android.svg.support.vector.parser;


import com.android.svg.support.vector.model.Path;

import org.dom4j.Element;

/**
 * Build clip-path's field values from attributes of the element.
 *
 * @author Megatron King
 * @since 2016/9/1 10:06
 */

public class ClipPathAttributeParser extends PathAttributeParser {

    @Override
    public void parse(Element element, Path path) {
        // The clip-path node is the same as the path node,
        // so we don't need do anything here.
        super.parse(element, path);
    }
}
