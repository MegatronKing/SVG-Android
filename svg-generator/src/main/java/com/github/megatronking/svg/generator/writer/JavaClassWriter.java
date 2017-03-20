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
package com.github.megatronking.svg.generator.writer;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class JavaClassWriter implements IBufferWriter {

    public static final String HEAD_SPACE = "    ";

    protected abstract void writeFields(BufferedWriter bw) throws IOException;

    protected abstract void writeConstructMethods(BufferedWriter bw) throws IOException;

    protected abstract void writeMethods(BufferedWriter bw) throws IOException;

    private String mPackage;
    private String[] mImports;
    protected String mClassSimpleName;
    private String mClassModifiers = "public";
    private String mClassExtends;
    private String[] mClassImplements;

    public void setPackage(String packageInfo) {
        mPackage = packageInfo;
    }

    public void setImports(String[] imports) {
        mImports = imports;
    }

    public void setClassSimpleName(String classSimpleName) {
        mClassSimpleName = classSimpleName;
    }

    public void setClassModifiers(String classModifiers) {
        mClassModifiers = classModifiers;
    }

    public void setClassRelation(String classExtends, String[] classImplements) {
        mClassExtends = classExtends;
        mClassImplements = classImplements;
    }

    @Override
    public void write(BufferedWriter bw) throws IOException {
        writePackage(bw);
        writeImports(bw);
        writeClassComment(bw);
        writeClass(bw);
        writeFields(bw);
        writeConstructMethods(bw);
        writeMethods(bw);
        writeInnerClasses(bw);
        writeEnd(bw);
        // The end.
        bw.flush();
        bw.close();
    }

    protected void writePackage(BufferedWriter bw) throws IOException {
        bw.write("package " + mPackage + ";");
        bw.newLine();
        bw.newLine();
    }

    protected void writeImports(BufferedWriter bw) throws IOException {
        if (mImports != null && mImports.length != 0) {
            for (int i = 0; i < mImports.length; i++) {
                bw.write("import " + mImports[i] + ";");
                bw.newLine();
            }
            bw.newLine();
        }
    }

    protected void writeClassComment(BufferedWriter bw) throws IOException {

    }

    protected void writeClass(BufferedWriter bw) throws IOException {
        bw.newLine();
        bw.write(mClassModifiers + " class " + mClassSimpleName + " ");
        if (mClassExtends != null && mClassExtends.length() != 0) {
            bw.write("extends " + mClassExtends);
        }
        if (mClassImplements != null && mClassImplements.length != 0) {
            bw.write(" implements ");
            for (int i = 0; i < mClassImplements.length; i++) {
                bw.write(mClassImplements[i]);
                if (i != mClassImplements.length - 1) {
                    bw.write(", ");
                }
            }
        }
        bw.write(" {");
        bw.newLine();
    }

    protected void writeInnerClasses(BufferedWriter bw) throws IOException {
        // by sub
    }

    protected void writeEnd(BufferedWriter bw) throws IOException {
        bw.newLine();
        bw.write("}");
    }

}
