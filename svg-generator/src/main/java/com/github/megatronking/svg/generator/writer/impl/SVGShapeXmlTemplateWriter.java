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
package com.github.megatronking.svg.generator.writer.impl;

import com.github.megatronking.svg.generator.writer.IBufferWriter;

import java.io.BufferedWriter;
import java.io.IOException;

public class SVGShapeXmlTemplateWriter implements IBufferWriter {

    @Override
    public void write(BufferedWriter bw) throws IOException {
        bw.write("<!-- AUTO-GENERATED FILE.  DO NOT MODIFY. -->");
        bw.newLine();
        bw.write("<shape xmlns:android=\"http://schemas.android.com/apk/res/android\" />");
        // The end.
        bw.flush();
        bw.close();
    }
}
