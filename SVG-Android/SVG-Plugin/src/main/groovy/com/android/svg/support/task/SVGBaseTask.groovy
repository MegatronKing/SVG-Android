import com.android.svg.support.SVGExtension
import org.gradle.api.DefaultTask

public class SVGBaseTask extends DefaultTask {

    def SVGExtension configuration;

    public void run() {
        configuration = project.svg;
        configuration.javaDir = resolveProjectDir(configuration.javaDir);
        configuration.shapeDir = resolveProjectDir(configuration.shapeDir);
        if (configuration.vectorDirs != null) {
            def vectorDirs = [];
            configuration.vectorDirs.each { vectorDir->
                vectorDir = resolveProjectDir(vectorDir);
                vectorDirs.add(vectorDir)
            }
            configuration.vectorDirs = vectorDirs;
        }
    }

    public def file(def filePath) {
        return new File(filePath);
    }

    public def file(def dir, def fileName) {
        return new File(dir, fileName);
    }

    public def resolveProjectDir(def dir) {
        if (dir != null && !dir.startsWith(project.projectDir.absolutePath)) {
            dir = project.projectDir.absolutePath + File.separator + dir;
        }
        return dir;
    }
}