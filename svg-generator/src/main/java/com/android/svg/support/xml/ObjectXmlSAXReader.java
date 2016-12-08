package com.android.svg.support.xml;

import org.dom4j.DocumentException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Read a xml file to an object.
 *
 * @author Megatron King
 * @since 2016/11/22 17:22
 */

public interface ObjectXmlSAXReader<T> {

    T read(String filePath) throws IOException, DocumentException;

    T read(File file) throws IOException, DocumentException;

    T read(InputStream is) throws IOException, DocumentException;

}
