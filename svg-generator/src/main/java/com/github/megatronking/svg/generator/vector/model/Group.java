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


import com.github.megatronking.svg.generator.utils.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * The group element model in the vector xml.
 *
 * @author Megatron King
 * @since 2016/8/31 20:42
 */
public class Group {

    public Group(Group parentGroup) {
        this.parentGroup = parentGroup;
    }

    public String name;

    public float pivotX;
    public float pivotY;

    public float scaleX = 1.0f;
    public float scaleY = 1.0f;

    public float rotation;

    public float translateX;
    public float translateY;

    public List<Object> children = new ArrayList<>();

    public Group parentGroup;

    public Matrix getMatrix() {
        // Calculate current group's matrix by preConcat the parent's and
        // and the current one on the top of the stack.
        // Basically the Mfinal = Mviewport * M0 * M1 * M2;
        // Mi the local matrix at level i of the group tree.
        Matrix local = new Matrix();
        local.postTranslate(-pivotX, -pivotY);
        local.postScale(scaleX, scaleY);
        local.postRotate(rotation, 0, 0);
        local.postTranslate(translateX + pivotX, translateY + pivotY);
        if (parentGroup != null) {
            Matrix matrix = parentGroup.getMatrix();
            matrix.preConcat(local);
            return matrix;
        }
        return local;
    }
}
