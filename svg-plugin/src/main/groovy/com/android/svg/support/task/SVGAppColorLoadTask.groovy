package com.android.svg.support.task

import com.android.svg.support.utils.Color
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