import com.android.svg.support.SVG2VectorExtension
import com.android.svg.support.SVGExtension
import org.gradle.api.DefaultTask

public class SVGBaseTask extends DefaultTask {

    def SVGExtension configuration
    def SVG2VectorExtension[] svg2vectorConfigurations;

    public void run() {
        configuration = project.svg
        configuration.javaDir = resolveProjectDir(configuration.javaDir)
        configuration.shapeDir = resolveProjectDir(configuration.shapeDir)
        if (configuration.vectorDirs != null) {
            def vectorDirs = []
            configuration.vectorDirs.each { vectorDir->
                vectorDir = resolveProjectDir(vectorDir);
                vectorDirs.add(vectorDir)
            }
            configuration.vectorDirs = vectorDirs
        }

        svg2vectorConfigurations = project.extensions.svg2vector
        svg2vectorConfigurations.each { svg2vectorConfiguration->
            if(!configuration.vectorDirs.contains(svg2vectorConfiguration.vectorDir)) {
                configuration.vectorDirs.add(svg2vectorConfiguration.vectorDir)
            }
        }
    }

    public def file(def filePath) {
        return new File(filePath)
    }

    public def file(def dir, def fileName) {
        return new File(dir, fileName)
    }

    public def resolveProjectDir(def dir) {
        def dirFile = file(dir)
        if (dir != null && !dirFile.exists() && !dir.startsWith(project.projectDir.absolutePath)) {
            dir = project.projectDir.absolutePath + File.separator + dir
        }
        return dir
    }
}