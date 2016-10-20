package com.android.svg.support.task;

import org.gradle.api.tasks.TaskAction;
import com.android.svg.support.svg.Svg2Vector;

public class SVG2VectorTask extends SVGBaseTask {

    def extensionName;

    public void setExtensionName(def extensionName) {
        this.extensionName = extensionName;
    }

    @TaskAction
    public void run() {
        super.run();
        def svg2vectorConfigurations = project.extensions.svg2vector
        svg2vectorConfigurations.each { svg2vectorConfiguration->
            if (extensionName.equals(svg2vectorConfiguration.name)) {
                doSvg2Vector(svg2vectorConfiguration.svgDir, resolveProjectDir(svg2vectorConfiguration.vectorDir)
                        , svg2vectorConfiguration.width, svg2vectorConfiguration.height)
            }
        }
    }

    void doSvg2Vector(def svgDir, def vectorDir, def width, def height) {
        def dir = file(svgDir)
        if (dir.exists() && dir.isDirectory()) {
            dir.listFiles().each { svgFile->
                if(!svgFile.isDirectory() && svgFile.length() > 0 && svgFile.name.endsWith(".svg")) {
                    def svgName = svgFile.name.substring(0, svgFile.name.lastIndexOf(".svg"))
                    svg2vector(svgFile, file(vectorDir, svgName + ".xml"), width, height)
                }
            }
        }
    }

    void svg2vector(def svgFile, def vectorFile, def width, def height) {
        OutputStream outStream = new FileOutputStream(vectorFile)
        String error = Svg2Vector.parseSvgToXml(svgFile, outStream, width, height)
        if (!error.isEmpty()) {
            logger.error(error)
        }
        outStream.flush()
        outStream.close()
    }

}
