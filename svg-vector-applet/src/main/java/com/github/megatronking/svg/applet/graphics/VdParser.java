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