package com.android.svg.support.vector;


import com.android.svg.support.model.Vector;
import com.android.svg.support.model.VectorConstants;
import com.android.svg.support.vector.parser.ParserImpl;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class VectorSAXReader implements VectorReader {

    private SAXReader mReader;

    public VectorSAXReader() {
        mReader = new SAXReader();
    }

    @Override
    public Vector read(String filePath) throws IOException, VectorException {
        return read(new File(filePath));
    }

    @Override
    public Vector read(File file) throws IOException, VectorException {
        return read(new FileInputStream(file));
    }

    @Override
    public Vector read(InputStream is) throws IOException, VectorException {
        try {
            return parseDocument(mReader.read(is));
        } catch (DocumentException e) {
            throw new VectorException(e.getMessage());
        }
    }

    private Vector parseDocument(Document document) throws VectorException {
        Element vectorElement = document.getRootElement();
        // simple validate
        if (!VectorConstants.TAG_VECTOR.equals(vectorElement.getName())) {
            throw new VectorException("The root element must be " + VectorConstants.TAG_VECTOR);
        }
        List<?> childElements = vectorElement.elements();
        if (childElements == null || childElements.isEmpty()) {
            throw new VectorException("There is no child node in the vector");
        }

        Vector vector = new Vector();
        ParserImpl.VECTOR_ELEMENT_PARSER.parse(vectorElement, vector);

        return vector;
    }
}
