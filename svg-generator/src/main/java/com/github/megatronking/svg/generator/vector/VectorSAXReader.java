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
package com.github.megatronking.svg.generator.vector;


import com.github.megatronking.svg.generator.vector.model.Vector;
import com.github.megatronking.svg.generator.vector.model.VectorConstants;
import com.github.megatronking.svg.generator.vector.parser.VectorParserImpl;
import com.github.megatronking.svg.generator.xml.SimpleImplementSAXReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.List;

/**
 * The vector structure is organized as a tree. Each node can be a group node, or a path.
 * A group node can have groups or paths as children, but a path node has no children.
 * One example can be:
 *                 Root Group
 *                /    |     \
 *           Group    Path    Group
 *          /     \             |
 *         Path   Path         Path
 *
 * @author Megatron King
 * @since 2016/8/31 20:59
 */

public class VectorSAXReader extends SimpleImplementSAXReader<Vector> {

    protected Vector parseDocument(Document document) throws DocumentException {
        Element vectorElement = document.getRootElement();
        // simple validate
        if (!VectorConstants.TAG_VECTOR.equals(vectorElement.getName())) {
            throw new VectorParseException("The root element must be " + VectorConstants.TAG_VECTOR);
        }
        List<?> childElements = vectorElement.elements();
        if (childElements == null || childElements.isEmpty()) {
            throw new VectorParseException("There is no child node in the vector");
        }

        Vector vector = new Vector();
        VectorParserImpl.VECTOR_ELEMENT_PARSER.parse(vectorElement, vector);

        return vector;
    }
}
