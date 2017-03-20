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

package com.github.megatronking.svg.generator.render;

import com.github.megatronking.svg.generator.vector.model.Group;
import com.github.megatronking.svg.generator.vector.model.Path;
import com.github.megatronking.svg.generator.vector.model.Vector;

import java.util.List;

public class VectorRootRenderer extends NotifyVectorRenderer<Vector> {

    @Override
    public void render(Vector vector) {
        notifyResult("final float scaleX = w / " + vector.viewportWidth + "f;");
        notifyResult("final float scaleY = h / " + vector.viewportHeight + "f;");
        // if no path needs to draw stroke, there is no need to define 'minScale'
        if (hasPathNeedMinScale(vector.children)) {
            notifyResult("final float minScale = Math.min(scaleX, scaleY);");
        }
        writeNewLine();
    }

    @Override
    protected void notifyResult(String result) {
        super.notifyResult(HEAD_SPACE + HEAD_SPACE + result + "\n");
    }

    private boolean hasPathNeedMinScale(List<Object> children) {
        if (children != null && !children.isEmpty()) {
            for (Object child : children) {
                if (child instanceof Path) {
                    if (((Path) child).strokeColor != 0) {
                        return true;
                    }
                } else if(hasPathNeedMinScale(((Group)child).children)) {
                    return true;
                }
            }
        }
        return false;
    }
}
