package com.android.svg.support.vector;


import com.android.svg.support.model.Vector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Read  a vector file to an object {@link Vector}.
 *
 * @author Megatron King
 * @since 2016/8/31 0:55
 */
public interface VectorReader {

    Vector read(String filePath) throws IOException, VectorException;

    Vector read(File file) throws IOException, VectorException;

    Vector read(InputStream is) throws IOException, VectorException;

}
