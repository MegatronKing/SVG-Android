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
     * @throws DocumentException an exception when parsing xml.
     */
    void parse(Element element, T t) throws DocumentException;

}
