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

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This a implementation of {@link ObjectXmlSAXReader}, the sub class just need to implement one method.
 *
 * @author Megatron King
 * @since 2016/11/22 17:29
 */

public abstract class SimpleImplementSAXReader<T> implements ObjectXmlSAXReader {

    private SAXReader mReader;

    public SimpleImplementSAXReader() {
        mReader = new SAXReader();
        mReader.setEntityResolver(new IgnoreDTDEntityResolver());
    }

    @Override
    public T read(String filePath) throws IOException, DocumentException {
        return read(new File(filePath));
    }

    @Override
    public T read(File file) throws IOException, DocumentException {
        return read(new FileInputStream(file));
    }

    @Override
    public T read(InputStream is) throws IOException, DocumentException {
        return parseDocument(mReader.read(is));
    }

    protected abstract T parseDocument(Document document) throws DocumentException;

}
