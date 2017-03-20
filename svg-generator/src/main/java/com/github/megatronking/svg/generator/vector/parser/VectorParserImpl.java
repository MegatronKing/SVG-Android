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

/**
 * The impl vector parsers.
 *
 * @author Megatron King
 * @since 2016/9/1 14:07
 */

public interface VectorParserImpl {

    // attributes
    VectorAttributeParser VECTOR_ATTRIBUTE_PARSER = new VectorAttributeParser();
    GroupAttributeParser GROUP_ATTRIBUTE_PARSER = new GroupAttributeParser();
    PathAttributeParser PATH_ATTRIBUTE_PARSER = new PathAttributeParser();
    ClipPathAttributeParser CLIP_PATH_ATTRIBUTE_PARSER = new ClipPathAttributeParser();

    // elements
    VectorElementParser VECTOR_ELEMENT_PARSER = new VectorElementParser();
    GroupElementParser GROUP_ELEMENT_PARSER = new GroupElementParser();

}
