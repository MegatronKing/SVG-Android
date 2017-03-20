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

package com.github.megatronking.svg.generator.svg.model;

import java.util.Map;

/**
 * SVG allows graphical objects to be defined for later reuse. It is recommended that, wherever possible,
 * referenced elements be defined inside of a defs element. Defining these elements inside of a defs
 * element promotes understandability of the SVG content and thus promotes accessibility. Graphical elements
 * defined in a defs element will not be directly rendered.
 *
 * @author Megatron King
 * @since 2017/1/4 10:19
 */

public class Defs extends SvgGroupNode {

    @Override
    public void applyStyles(Map<String, String> inheritStyles, Map<String, Map<String, String>> defineStyles) {
        // do nothing
    }

    @Override
    public boolean isValid() {
        return false;
    }

}
