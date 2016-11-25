package com.android.svg.support.render;


import com.android.svg.support.vector.model.Group;
import com.android.svg.support.vector.model.Path;
import com.android.svg.support.vector.model.Vector;

import java.util.List;

public class VectorRootRenderer extends NotifyVectorRenderer<Vector> {

    @Override
    public void render(Vector vector) {
        notifyResult("");
        notifyResult("final float scaleX = w / " + vector.viewportWidth + "f;");
        notifyResult("final float scaleY = h / " + vector.viewportHeight + "f;");
        // if no path needs to draw stroke, there is no need to define 'minScale'
        if (hasPathNeedMinScale(vector.children)) {
            notifyResult("final float minScale = Math.min(scaleX, scaleY);");
        }
        notifyResult("");
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
