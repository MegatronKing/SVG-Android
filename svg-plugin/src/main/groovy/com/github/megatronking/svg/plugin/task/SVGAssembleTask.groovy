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

package com.github.megatronking.svg.plugin.task

import com.github.megatronking.svg.plugin.model.VectorModel
import com.github.megatronking.svg.generator.render.VectorRenderer
import com.github.megatronking.svg.generator.vector.VectorSAXReader
import com.github.megatronking.svg.generator.writer.JavaClassWriter
import com.github.megatronking.svg.generator.writer.impl.SVGShapeXmlTemplateWriter
import com.github.megatronking.svg.generator.writer.impl.SVGLoaderTemplateWriter
import com.github.megatronking.svg.generator.writer.impl.SVGRendererTemplateWriter
import org.gradle.api.tasks.TaskAction

public class SVGAssembleTask extends SVGBaseTask {

    @TaskAction
    public void run() {
        super.run();
        // check arguments
        if (configuration == null) {
            return
        }
        if (configuration.vectorDirs == null) {
            return
        }
        if (configuration.shapeDir == null || !checkDirExistOrMkdirs(configuration.shapeDir)) {
            return
        }
        if (configuration.javaDir == null || !checkDirExistOrMkdirs(configuration.javaDir)) {
            return
        }
        if (configuration.packageName == null) {
            return
        }
        // check vector files
        def vectors = collectVectors()
        if (vectors.size() == 0) {
            return
        }
        // read vector files
        def vectorModels = [];
        VectorSAXReader reader = new VectorSAXReader()
        vectors.each { vector->
            def vectorModel = new VectorModel()
            def vectorFile = file(vector)
            vectorModel.name = vectorFile.name.substring(0, vectorFile.name.lastIndexOf(".xml"))
            try {
                vectorModel.vector = reader.read(vector)
            } catch (Exception e) {
                logger.error("Occur an error: " + vector + e.getMessage());
                return true
            }
            vectorModels.add(vectorModel)
        }

        // substring the package name like: "com.android.xxx"
        def javaClassPath = configuration.javaDir.replace("\\", ".").replace("/", ".")
        def javaClassPackage = javaClassPath.substring(javaClassPath.indexOf("src.main.java.") + 14, javaClassPath.length())

        // write renderer
        writeJavaRendererClass(vectorModels, javaClassPackage)
        // write loader
        if (configuration.generateLoader) {
            writeJavaLoaderClass(vectorModels, javaClassPackage)
        }
        // write shape xml
        writeShapeXml(vectorModels)
    }

    private def checkDirExistOrMkdirs(def dir) {
        dir = file(dir)
        return dir.exists() || dir.mkdirs()
    }

    private def collectVectors() {
        def vectors = []
        configuration.vectorDirs.each { dir->
            dir = file(dir)
            if (dir.exists() && dir.isDirectory()) {
                dir.eachFile { file->
                    def path = file.absolutePath
                    if (file.exists() && file.length() != 0 && !vectors.contains(path) && path.endsWith(".xml")) {
                        vectors.add(file.absolutePath)
                    }
                }
            }
        }
        return vectors
    }

    private void writeJavaRendererClass(def vectorModels, def javaClassPackage) {
        vectorModels.each { vectorModel->
            BufferedWriter bw = new BufferedWriter(new FileWriter(file(configuration.javaDir, vectorModel.name + ".java")))
            VectorRenderer renderer = new VectorRenderer()
            renderer.render(vectorModel.vector)
            JavaClassWriter writer = new SVGRendererTemplateWriter(renderer, vectorModel.vector)
            writer.setPackage(javaClassPackage)
            writer.setClassSimpleName(vectorModel.name)
            writer.write(bw)
        }
    }

    private void writeJavaLoaderClass(def vectorModels, def javaClassPackage) {
        SVGLoaderTemplateWriter svgLoaderWriter = new SVGLoaderTemplateWriter(configuration.packageName)
        svgLoaderWriter.setPackage(javaClassPackage)
        vectorModels.each { vectorModel->
            svgLoaderWriter.addRendererName(vectorModel.name)
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file(configuration.javaDir, "SVGLoader.java")))
        svgLoaderWriter.write(bw)
    }

    private void writeShapeXml(def vectorModels) {
        SVGShapeXmlTemplateWriter shapeXmlWriter = new SVGShapeXmlTemplateWriter()
        vectorModels.each { vectorModel->
            BufferedWriter bw = new BufferedWriter(new FileWriter(file(configuration.shapeDir, vectorModel.name + ".xml")))
            shapeXmlWriter.write(bw)
        }
    }
}