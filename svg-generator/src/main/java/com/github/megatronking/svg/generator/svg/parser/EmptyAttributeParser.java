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
package com.github.megatronking.svg.generator.svg.parser;

import com.github.megatronking.svg.generator.xml.CommonAbstractAttributeParser;

import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * An empty attribute parser.
 *
 * @author Megatron King
 * @since 2017 10:40
 */

public class EmptyAttributeParser<T> extends CommonAbstractAttributeParser<T> {

    @Override
    public void parse(Element element, T t) throws DocumentException {
        // Nothing to parse
    }
}
