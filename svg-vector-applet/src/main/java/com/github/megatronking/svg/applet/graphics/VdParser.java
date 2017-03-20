/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.megatronking.svg.applet.graphics;

import com.github.megatronking.svg.generator.xml.IgnoreDTDEntityResolver;

import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * Parse a VectorDrawable's XML file, and generate an internal tree representation,
 * which can be used for drawing / previewing.
 */
class VdParser {

    // Note that the incoming file is the VectorDrawable's XML file, not the SVG.
    VdTree parse(InputStream is, StringBuilder vdErrorLog) {
        final VdTree tree = new VdTree();
        try {
            SAXReader reader = new SAXReader();
            reader.setEntityResolver(new IgnoreDTDEntityResolver());
            tree.parse(reader.read(is));
        }
        catch (Exception e) {
            vdErrorLog.append(e.getMessage());
        }
        return tree;
    }

}