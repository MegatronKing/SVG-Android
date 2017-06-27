/*
 * Copyright (C) 2017, Megatron King
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.megatronking.svg.plugin.task

import com.github.megatronking.svg.generator.utils.Color
import org.gradle.api.tasks.TaskAction

public class SVGAppColorLoadTask extends SVGBaseTask {

    @TaskAction
    public void run() {
        super.run();
        if (configuration != null && configuration.appColors != null && !configuration.appColors.isEmpty()) {
            configuration.appColors.keySet().each  { key ->
                Color.appColorMaps.put(key, (int)(configuration.appColors[key]))
            }
        }
    }
}
