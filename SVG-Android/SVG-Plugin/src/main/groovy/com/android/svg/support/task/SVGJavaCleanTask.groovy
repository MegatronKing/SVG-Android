package com.android.svg.support.task;

import org.gradle.api.tasks.TaskAction;

public class SVGJavaCleanTask extends SVGBaseTask {

    @TaskAction
    public void run() {
        super.run();
        if (configuration != null && configuration.javaDir != null) {
            def dir = file(configuration.javaDir)
            dir.deleteDir()
        }
    }
}
