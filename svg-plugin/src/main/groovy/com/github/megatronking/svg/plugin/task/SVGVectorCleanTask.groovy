package com.github.megatronking.svg.plugin.task;

import org.gradle.api.tasks.TaskAction;

public class SVGVectorCleanTask extends SVGBaseTask {

    @TaskAction
    public void run() {
        super.run();
        if (configuration != null && configuration.vectorDirs != null) {
            configuration.vectorDirs.each { vectorDir->
                def dir = file(vectorDir)
                dir.deleteDir()
            }

        }
    }
}
