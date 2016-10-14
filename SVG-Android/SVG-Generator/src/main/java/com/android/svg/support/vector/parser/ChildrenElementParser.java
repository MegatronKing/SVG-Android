package com.android.svg.support.vector.parser;

import org.dom4j.Element;

/**
 * Some Elements may contain children nodes. In this condition,
 * We must parse not only the attributes but the children nodes.
 *
 * @author Megatron King
 * @since 2016/9/1 11:04
 */

public abstract class ChildrenElementParser<T> implements IElementParser<T> {

    private IAttributeParser<T> mAttributeParser;

    public ChildrenElementParser(IAttributeParser<T> mAttributeParser) {
        this.mAttributeParser = mAttributeParser;
    }

    @Override
    public void parse(Element element, T t) {
        mAttributeParser.parse(element, t);
        for (Object childElement : element.elements()) {
            parseChild((Element) childElement, t);
        }
    }

    /**
     * Parse the children nodes.
     *
     * @param childElement a child element.
     * @param t a child element object.
     */
    protected abstract void parseChild(Element childElement, T t);

}
