package com.github.megatronking.svg.applet.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

/**
 * Used to represent one VectorDrawable's element, can be a group or path.
 */
abstract class VdElement {

    String mName;

    public String getName() {
        return mName;
    }

    public abstract void draw(Graphics2D g, AffineTransform currentMatrix, float scaleX, float scaleY);

    public abstract void parseAttributes(List<?> attributes);

    public abstract boolean isGroup();

}
