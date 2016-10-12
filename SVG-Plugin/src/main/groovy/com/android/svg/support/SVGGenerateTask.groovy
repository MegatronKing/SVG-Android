package com.android.svg.support

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class SVGGenerateTask extends DefaultTask {

    def SVGExtension configuration;

    SVGGenerateTask() {
        configuration = project.svg;
    }

    @TaskAction
    public void generateSVG() {
        // TODO
    }

}