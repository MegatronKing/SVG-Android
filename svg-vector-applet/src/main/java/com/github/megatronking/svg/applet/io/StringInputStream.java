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
package com.github.megatronking.svg.applet.io;

import java.io.IOException;
import java.io.InputStream;

public class StringInputStream extends InputStream {

    private final String data;

    private int index;

    public StringInputStream(String data) {
        this.data = data;
    }

    @Override
    public int read() throws IOException {
        if (data == null || index > data.length() - 1) {
            return -1;
        }
        char charByIndex = data.charAt(index);
        index++;
        return charByIndex;
    }
}
