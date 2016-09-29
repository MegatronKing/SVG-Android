package com.android.svg.support;


import com.android.svg.support.model.Vector;
import com.android.svg.support.render.VectorRenderer;
import com.android.svg.support.vector.VectorException;
import com.android.svg.support.vector.VectorSAXReader;
import com.android.svg.support.writer.IBufferWriter;
import com.android.svg.support.writer.JavaClassWriter;
import com.android.svg.support.writer.impl.SVGFakeXmlTemplateWriter;
import com.android.svg.support.writer.impl.SVGLoaderTemplateWriter;
import com.android.svg.support.writer.impl.SVGRendererTemplateWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Generator {

    public static void main(String[] args) throws IOException, VectorException {
        // There must be three arguments.
        // The first is the resource path of vector xml.
        // The second is the resource path of fake xml.
        // The third is the class path of java code.
        File vectorFolder = new File(args[0]);
        File fakeFolder = new File(args[1]);
        File codeFolder = new File(args[2]);

        if (!vectorFolder.exists() || !vectorFolder.isDirectory()) {
            return;
        }
        if (!fakeFolder.exists() && !fakeFolder.mkdirs()) {
            return;
        }
        if (!codeFolder.exists() && !codeFolder.mkdirs()) {
            return;
        }
        deleteFilesInFolder(fakeFolder);
        deleteFilesInFolder(codeFolder);

        File[] vectors = vectorFolder.listFiles();
        if (vectors == null || vectors.length == 0) {
            return;
        }

        String codePath = codeFolder.getAbsolutePath().replace("\\", ".").replace("/", ".");
        String codePackage = codePath.substring(codePath.indexOf("src.main.java.") + 14, codePath.length());

        // write SVGRenderer.java
        SVGLoaderTemplateWriter svgLoaderTemplateWriter = new SVGLoaderTemplateWriter();
        svgLoaderTemplateWriter.setPackage(codePackage);
        SVGFakeXmlTemplateWriter svgFakeXmlTemplateWriter = new SVGFakeXmlTemplateWriter();
        VectorSAXReader reader = new VectorSAXReader();
        for (File vector : vectors) {
            if (vector.getName().endsWith("xml")) {
                String noExtensionName = vector.getName().substring(0, vector.getName().lastIndexOf(".xml"));
                generateCode(reader.read(vector), noExtensionName, codeFolder.getPath(), codePackage);
                generateXml(svgFakeXmlTemplateWriter, noExtensionName, fakeFolder.getPath());
                svgLoaderTemplateWriter.addRendererName(noExtensionName);
            }
        }

        // write SVGLoader.java
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(codeFolder.getPath(), "SVGLoader.java")));
        svgLoaderTemplateWriter.write(bw);
        bw.flush();
        bw.close();
    }

    private static void deleteFilesInFolder(File folder) {
        if (!folder.exists() || !folder.isDirectory()) {
            return;
        }
        File[] listFiles = folder.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return;
        }
        for (File file : listFiles) {
            file.delete();
        }
    }

    private static void generateCode(Vector vector, String imageName, String codeFolder, String packageName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(codeFolder, imageName + ".java")));
        VectorRenderer renderer = new VectorRenderer();
        renderer.render(vector);
        JavaClassWriter writer = new SVGRendererTemplateWriter(renderer, vector);
        writer.setPackage(packageName);
        writer.setClassSimpleName(imageName);
        writer.write(bw);
        bw.flush();
        bw.close();
    }

    private static void generateXml(IBufferWriter writer, String imageName, String xmlFolder)throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(xmlFolder, imageName + ".xml")));
        writer.write(bw);
        bw.flush();
        bw.close();
    }
}
