package com.github.megatronking.svg.plugin.task

import com.github.megatronking.svg.generator.utils.Color
import org.gradle.api.tasks.TaskAction

public class SVGAppColorLoadTask extends SVGBaseTask {

    @TaskAction
    public void run() {
        super.run();
        if (configuration != null && configuration.appColors != null && !onfiguration.appColors.isEmpty()) {
            configuration.appColors.keySet().each  { key ->
                Color.appColorMaps.put(key, configuration.appColors[key])
            }
        }
    }
}