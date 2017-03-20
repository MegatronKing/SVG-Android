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


import com.github.megatronking.svg.generator.utils.Color;
import com.github.megatronking.svg.generator.utils.Dimen;
import com.github.megatronking.svg.generator.utils.SCU;

import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * We define some common methods here.
 *
 * @author Megatron King
 * @since 2016/9/1 9:40
 */

public abstract class CommonAbstractAttributeParser<T> implements IAttributeParser<T> {

    protected String parseString(Element element, String name) {
        Attribute attribute = element.attribute(name);
        return attribute == null ? null : attribute.getValue();
    }

    protected String parseString(Element element, String name, String defaultValue) {
        Attribute attribute = element.attribute(name);
        String value = attribute == null ? null : attribute.getValue();
        return value == null || value.trim().length() == 0 ? defaultValue : value;
    }

    protected float parseFloat(Element element, String name) {
        return parseFloat(element, name, 0.0f);
    }

    protected float parseFloat(Element element, String name, float defaultValue) {
        return SCU.parseFloat(parseString(element, name), defaultValue);
    }

    protected boolean parseBoolean(Element element, String name) {
        return parseBoolean(element, name, false);
    }

    protected boolean parseBoolean(Element element, String name, boolean defaultValue) {
        return SCU.parseBoolean(parseString(element, name), defaultValue);
    }

    protected int parseColor(Element element, String name) {
        return Color.convert(parseString(element, name));
    }

    protected int parseColor(Element element, String name, int defaultColor) {
        return Color.convert(parseString(element, name), defaultColor);
    }

    protected float parseDimen(Element element, String name) {
        return Dimen.convert(parseString(element, name));
    }
}
