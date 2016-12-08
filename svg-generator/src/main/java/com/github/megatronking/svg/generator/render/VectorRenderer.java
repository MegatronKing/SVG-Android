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
