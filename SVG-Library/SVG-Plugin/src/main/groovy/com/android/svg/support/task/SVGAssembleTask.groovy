package com.android.svg.support.task

import com.android.svg.support.model.VectorModel
import com.android.svg.support.render.VectorRenderer
import com.android.svg.support.vector.VectorSAXReader
import com.android.svg.support.writer.JavaClassWriter
import com.android.svg.support.writer.impl.SVGShapeXmlTemplateWriter
import com.android.svg.support.writer.impl.SVGLoaderTemplateWriter
import com.android.svg.support.writer.impl.SVGRendererTemplateWriter
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
            vectorModel.vector = reader.read(vector)
            vectorModels.add(vectorModel)
        }

        // substring the package name like: "com.android.xxx"
        def javaClassPath = configuration.javaDir.replace("\\", ".").replace("/", ".")
        def javaClassPackage = javaClassPath.substring(javaClassPath.indexOf("src.main.java.") + 14, javaClassPath.length())

        // write renderer
        writeJavaRendererClass(vectorModels, javaClassPackage)
        // write loader
        writeJavaLoaderClass(vectorModels, javaClassPackage)
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
            bw.flush()
            bw.close()
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
        bw.flush()
        bw.close()
    }

    private void writeShapeXml(def vectorModels) {
        SVGShapeXmlTemplateWriter shapeXmlWriter = new SVGShapeXmlTemplateWriter()
        vectorModels.each { vectorModel->
            BufferedWriter bw = new BufferedWriter(new FileWriter(file(configuration.shapeDir, vectorModel.name + ".xml")))
            shapeXmlWriter.write(bw)
            bw.flush()
            bw.close()
        }
    }
}