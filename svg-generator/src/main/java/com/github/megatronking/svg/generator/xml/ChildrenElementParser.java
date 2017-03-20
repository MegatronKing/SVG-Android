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
package com.github.megatronking.svg.generator.xml;

import org.dom4j.DocumentException;
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

    public ChildrenElementParser(IAttributeParser<T> attributeParser) {
        this.mAttributeParser = attributeParser;
    }

    @Override
    public void parse(Element element, T t) throws DocumentException {
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
     * @throws DocumentException an exception when parsing xml.
     */
    protected abstract void parseChild(Element childElement, T t) throws DocumentException;

}
