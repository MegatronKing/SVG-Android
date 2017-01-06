package com.github.megatronking.svg.cli;

import com.github.megatronking.svg.generator.svg.Svg2Vector;
import com.github.megatronking.svg.generator.utils.FileUtils;
import com.github.megatronking.svg.generator.utils.SCU;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import java.io.File;
import java.io.IOException;

/**
 * The main.
 *
 * @author Megatron King
 * @since 2017/01/05 15:28
 */
public class Main {

    private static final String HELPER_INFO = "-f d:/svg/a.svg -o d:/vector/a.xml";

    public static void main(String[] args) {
        Options opt = new Options();
        opt.addOption("d", "dir", true, "the target svg directory");
        opt.addOption("f", "file", true, "the target svg file");
        opt.addOption("o", "output", true, "the output vector file or directory");
        opt.addOption("w", "width", true, "the width size of target vector image");
        opt.addOption("h", "height", true, "the height size of target vector image");

        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new PosixParser();

        CommandLine cl;
        try {
            cl = parser.parse(opt, args);
        } catch (ParseException e) {
            formatter.printHelp(HELPER_INFO, opt);
            return;
        }

        if(cl == null) {
            formatter.printHelp(HELPER_INFO, opt);
            return;
        }

        int width = 0;
        int height = 0;
        if (cl.hasOption("w")) {
            width = SCU.parseInt(cl.getOptionValue("w"));
        }
        if (cl.hasOption("h")) {
            height = SCU.parseInt(cl.getOptionValue("h"));
        }

        String dir = null;
        String file = null;
        if (cl.hasOption("d")) {
            dir = cl.getOptionValue("d");
        } else if (cl.hasOption("f")) {
            file = cl.getOptionValue("f");
        }

        String output = null;
        if (cl.hasOption("o")) {
            output = cl.getOptionValue("o");
        }

        if (output == null) {
            if (dir != null) {
                output = dir;
            }
            if (file != null) {
                output = FileUtils.noExtensionName(file) + ".xml";
            }
        }

        if (dir == null && file == null) {
            formatter.printHelp(HELPER_INFO, opt);
            throw new RuntimeException("You must input the target svg file or directory");
        }

        if (dir != null) {
            File inputDir = new File(dir);
            if (!inputDir.exists() || !inputDir.isDirectory()) {
                throw new RuntimeException("The path [" + dir + "] is not exist or valid directory");
            }
            File outputDir = new File(output);
            if (outputDir.exists() || outputDir.mkdirs()) {
                svg2vectorForDirectory(inputDir, outputDir, width, height);
            } else {
                throw new RuntimeException("The path [" + outputDir + "] is not a valid directory");
            }
        }

        if (file != null) {
            File inputFile = new File(file);
            if (!inputFile.exists() || !inputFile.isFile()) {
                throw new RuntimeException("The path [" + file + "] is not exist or valid file");
            }
            svg2vectorForFile(inputFile, new File(output), width, height);
        }
    }

    private static void svg2vectorForDirectory(File inputDir, File outputDir, int width, int height) {
        File[] childFiles = inputDir.listFiles();
        if (childFiles != null) {
            for (File childFile : childFiles) {
                if (childFile.isFile() && childFile.length() > 0) {
                    svg2vectorForFile(childFile, new File(outputDir, FileUtils.noExtensionLastName(childFile) + ".xml"), width, height);
                }
            }
        }
    }

    private static void svg2vectorForFile(File inputFile, File outputFile, int width, int height) {
        if (inputFile.getName().endsWith(".svgz")) {
            File tempUnzipFile = new File(inputFile.getParent(), FileUtils.noExtensionLastName(inputFile) + ".svg");
            try {
                FileUtils.unZipGzipFile(inputFile, tempUnzipFile);
                svg2vectorForFile(tempUnzipFile, outputFile, width, height);
            } catch (IOException e){
                throw new RuntimeException("Unzip file occur an error: " + e.getMessage());
            } finally {
                tempUnzipFile.delete();
            }
        } else if (inputFile.getName().endsWith(".svg")) {
            Svg2Vector.parseSvgToXml(inputFile, outputFile, width, height);
        }
    }

}

