package com.android.svg.support.render;


import com.android.svg.support.model.Vector;

public class VectorRootRenderer extends NotifyVectorRenderer<Vector> {

    @Override
    public void render(Vector vector) {
        notifyResult("");
        notifyResult("final float scaleX = w / " + vector.viewportWidth + "f;");
        notifyResult("final float scaleY = h / " + vector.viewportHeight + "f;");
        notifyResult("final float minScale = Math.min(scaleX, scaleY);");
        notifyResult("");
    }

    @Override
    protected void notifyResult(String result) {
        super.notifyResult(HEAD_SPACE + HEAD_SPACE + result + "\n");
    }
}
