package com.android.svg.support.vector.parser;

import org.dom4j.Element;

/**
 * We convert xml to tree elements by dom4j framework.
 * The next step is parsing the elements to model objects.
 * An element can have declared namespaces, attributes, child nodes and textual content.
 *
 * @author Megatron King
 * @since 2016/9/1 9:37
 */

public interface IElementParser<T> {

    /**
     * Parse an element to a model object, one element, one object.
     *
     * @param element the dom element.
     * @param t the object.
     */
    void parse(Element element, T t);

}
