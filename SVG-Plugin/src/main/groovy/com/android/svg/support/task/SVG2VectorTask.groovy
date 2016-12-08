package com.android.svg.support.task

import com.android.svg.support.utils.Holder
import org.gradle.api.tasks.TaskAction;
import com.android.svg.support.svg.Svg2Vector;

public class SVG2VectorTask extends SVGBaseTask {

    def errorSvgs = []

    def extensionName;

    public void setExtensionName(def extensionName) {
        this.extensionName = extensionName;
    }

    @TaskAction
    public void run() {
        super.run();
        errorSvgs.clear()
        svg2vectorConfigurations.each { svg2vectorConfiguration->
            if (extensionName.equals(svg2vectorConfiguration.name)) {
                doSvg2Vector(svg2vectorConfiguration.svgDir, resolveProjectDir(svg2vectorConfiguration.vectorDir)
                        , svg2vectorConfiguration.width, svg2vectorConfiguration.height)
            }
        }
        errorSvgs.each { errorSvg ->
            logger.error(errorSvg + " 失败！")
        }
    }

    void doSvg2Vector(def svgDir, def vectorDir, def width, def height) {
        def dir = file(svgDir)
        if (dir.exists() && dir.isDirectory()) {
            dir.listFiles().each { svgFile->
                if(!svgFile.isDirectory() && svgFile.length() > 0 && (svgFile.name.endsWith(".svg") || svgFile.name.endsWith(".svgz"))) {
                    def svgName = svgFile.name.substring(0, svgFile.name.lastIndexOf(".svg"))
                    svg2vector(svgFile, file(vectorDir, svgName + ".xml"), width, height)
                }
            }
        }
    }

    void svg2vector(def svgFile, def vectorFile, def width, def height) {
        if(!vectorFile.getParentFile().exists() && !vectorFile.getParentFile().mkdirs()) {
            return
        }
        if(Holder.SVG_HOLDER.contains(vectorFile.name)) {
            logger.error("Duplicated svg image file named ${svgFile.name}")
            return
        }
        Holder.SVG_HOLDER.add(vectorFile.name)
        String error = Svg2Vector.parseSvgToXml(svgFile, vectorFile, width, height)
        if (error != null && !error.isEmpty()) {
            errorSvgs.add(svgFile.path)
            vectorFile.delete()
            if (configuration.debugMode) {
                logger.error(error)
            }
        }
    }
}
