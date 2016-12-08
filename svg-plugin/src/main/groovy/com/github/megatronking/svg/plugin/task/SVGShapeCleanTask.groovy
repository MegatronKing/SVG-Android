package com.github.megatronking.svg.plugin.task;

import org.gradle.api.tasks.TaskAction;

public class SVGShapeCleanTask extends SVGBaseTask {

    @TaskAction
    public void run() {
        super.run();
        if (configuration != null && configuration.shapeDir != null) {
            def dir = file(configuration.shapeDir)
            dir.deleteDir()
        }
    }

}
