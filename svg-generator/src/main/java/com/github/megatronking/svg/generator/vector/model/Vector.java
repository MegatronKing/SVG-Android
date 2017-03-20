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
package com.github.megatronking.svg.generator.vector.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The vector element (root element) model in the vector xml.
 *
 * @author Megatron King
 * @since 2016/8/31 20:42
 */

public class Vector {

    public String name;

    public boolean autoMirrored;

    public float alpha;

    public String tintMode;

    public int tint;

    public String width;
    public String height;

    public float viewportWidth;
    public float viewportHeight;

    public List<Object> children = new ArrayList<>();

}
