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
