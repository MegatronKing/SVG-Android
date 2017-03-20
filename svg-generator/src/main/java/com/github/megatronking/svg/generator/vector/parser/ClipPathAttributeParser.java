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


import com.github.megatronking.svg.generator.vector.model.Path;

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
