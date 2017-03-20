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

public class VectorRenderer implements IVectorRenderer<Vector>, NotifyVectorRenderer.VectorRendererListener {

    private VectorRootRenderer mRootRenderer;
    private VectorPathRenderer mPathRenderer;

    private StringBuilder mRenderResult;

    public VectorRenderer() {
        mRootRenderer = new VectorRootRenderer();
        mRootRenderer.setVectorRendererListener(this);

        mPathRenderer = new VectorPathRenderer();
        mPathRenderer.setVectorRendererListener(this);

        mRenderResult = new StringBuilder();
    }

    @Override
    public void render(Vector vector) {
        mRootRenderer.render(vector);
        for (Object child : vector.children) {
            renderChild(child);
        }
    }

    private void renderChild(Object object) {
        if (object instanceof Path) {
            mPathRenderer.render((Path) object);
        } else if(object instanceof Group) {
            for (Object child : ((Group)object).children) {
                renderChild(child);
            }
        }
    }

    @Override
    public void receiveResult(String result) {
        mRenderResult.append(result);
    }

    public String renderResult() {
        return mRenderResult.toString();
    }

}
