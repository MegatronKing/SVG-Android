package com.android.svg.support.task

import com.android.svg.support.utils.Color
import org.gradle.api.tasks.TaskAction

public class SVGAppColorLoadTask extends SVGBaseTask {

    @TaskAction
    public void run() {
        super.run();
        if (configuration != null && configuration.appColors != null) {
            configuration.appColors.keySet().each  { name ->
                Color.appColorMaps.put(name, configuration.appColors[name])
            }
        }
    }
}