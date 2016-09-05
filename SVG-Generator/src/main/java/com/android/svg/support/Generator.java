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
        // There must be two arguments.
        // The first is the resource path of vector xml.
        // The second is the code path of generated classes.
        File imageFolder = new File(args[0]);
        File projectFolder = new File(args[1]);

        if (!imageFolder.exists() || !imageFolder.isDirectory()) {
            return;
        }
        if (!projectFolder.exists() || !projectFolder.isDirectory()) {
            return;
        }

        String[] configPackage = Config.SVG_CODE_PACKAGE.split("\\.");
        String codeFolder = "\\src\\main\\java\\";
        for (String configPackagePath : configPackage) {
            codeFolder += "\\" + configPackagePath;
        }
        File projectCodeFolder = new File(projectFolder.getPath() + codeFolder);
        if (!projectCodeFolder.exists()) {
            projectCodeFolder.mkdirs();
        }
        deleteFilesInFolder(projectCodeFolder);

        String xmlFolder = "\\src\\main\\res\\drawable-ldpi";
        File projectXmlFolder = new File(projectFolder.getPath() + xmlFolder);
        deleteFilesInFolder(projectXmlFolder);

        File[] images = imageFolder.listFiles();
        if (images == null || images.length == 0) {
            return;
        }

        // write SVGRenderer.java
        SVGLoaderTemplateWriter svgLoaderTemplateWriter = new SVGLoaderTemplateWriter();
        SVGFakeXmlTemplateWriter svgFakeXmlTemplateWriter = new SVGFakeXmlTemplateWriter();
        VectorSAXReader reader = new VectorSAXReader();
        for (File image : images) {
            if (image.getName().endsWith("xml")) {
                String noExtentionName = image.getName().substring(0, image.getName().lastIndexOf(".xml"));
                generateCode(reader.read(image), noExtentionName, projectCodeFolder.getPath());
                generateXml(svgFakeXmlTemplateWriter, noExtentionName, projectXmlFolder.getPath());
                svgLoaderTemplateWriter.addRendererName(noExtentionName);
            }
        }

        // write SVGLoader.java
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(projectCodeFolder.getPath(), "SVGLoader.java")));
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

    private static void generateCode(Vector vector, String imageName, String codeFolder) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(codeFolder, imageName + ".java")));
        VectorRenderer renderer = new VectorRenderer();
        renderer.render(vector);
        JavaClassWriter writer = new SVGRendererTemplateWriter(renderer, vector);
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
