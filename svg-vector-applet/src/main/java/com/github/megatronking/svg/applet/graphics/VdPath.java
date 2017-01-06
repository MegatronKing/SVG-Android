package com.github.megatronking.svg.applet.graphics;

import com.github.megatronking.svg.generator.utils.PathDataNode;

import org.dom4j.Attribute;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.List;

/**
 * Used to represent one VectorDrawable's path element.
 */
class VdPath extends VdElement {

    private static final String PATH_ID = "android:name";
    private static final String PATH_DESCRIPTION = "android:pathData";
    private static final String PATH_FILL = "android:fillColor";
    private static final String PATH_FILL_OPACITY = "android:fillAlpha";
    private static final String PATH_FILL_TYPE = "android:fillType";
    private static final String PATH_STROKE = "android:strokeColor";
    private static final String PATH_STROKE_OPACTIY = "android:strokeAlpha";

    private static final String FILL_TYPE_EVEN_ODD = "evenOdd";

    private static final String PATH_STROKE_WIDTH = "android:strokeWidth";
    private static final String PATH_STROKE_LINECAP = "android:strokeLineCap";
    private static final String PATH_STROKE_LINEJOIN = "android:strokeLineJoin";
    private static final String PATH_STROKE_MITERLIMIT = "android:strokeMiterLimit";

    private static final String LINECAP_BUTT = "butt";
    private static final String LINECAP_ROUND = "round";
    private static final String LINECAP_SQUARE = "square";
    private static final String LINEJOIN_MITER = "miter";
    private static final String LINEJOIN_ROUND = "round";
    private static final String LINEJOIN_BEVEL = "bevel";

    private PathDataNode[] mNodeList = null;
    private int mStrokeColor = 0;
    private int mFillColor = 0;

    private float mStrokeWidth = 0;
    private int mStrokeLineCap = 0;
    private int mStrokeLineJoin = 0;
    private float mStrokeMiterlimit = 4;
    private float mStrokeAlpha = 1.0f;
    private float mFillAlpha = 1.0f;
    private int mFillType = PathIterator.WIND_NON_ZERO;

    private void toPath(Path2D path) {
        path.reset();
        if (mNodeList != null) {
            VdNodeRender.createPath(mNodeList, path);
        }
    }

    /**
     * @return color value in #AARRGGBB format.
     */
    private static int calculateColor(String value) {
        int len = value.length();
        int ret;
        int k = 0;
        switch (len) {
            case 7: // #RRGGBB
                ret = (int) Long.parseLong(value.substring(1), 16);
                ret |= 0xFF000000;
                break;
            case 9: // #AARRGGBB
                ret = (int) Long.parseLong(value.substring(1), 16);
                break;
            case 4: // #RGB
                ret = (int) Long.parseLong(value.substring(1), 16);

                k |= ((ret >> 8) & 0xF) * 0x110000;
                k |= ((ret >> 4) & 0xF) * 0x1100;
                k |= ((ret) & 0xF) * 0x11;
                ret = k | 0xFF000000;
                break;
            case 5: // #ARGB
                ret = (int) Long.parseLong(value.substring(1), 16);
                k |= ((ret >> 12) & 0xF) * 0x11000000;
                k |= ((ret >> 8) & 0xF) * 0x110000;
                k |= ((ret >> 4) & 0xF) * 0x1100;
                k |= ((ret) & 0xF) * 0x11;
                ret = k;
                break;
            default:
                return 0xFF000000;
        }
        return ret;
    }

    private void setNameValue(String name, String value) {
        if (PATH_DESCRIPTION.equals(name)) {
            mNodeList = PathDataNode.createNodesFromPathData(value);
        } else if (PATH_ID.equals(name)) {
            mName = value;
        } else if (PATH_FILL.equals(name)) {
            mFillColor = calculateColor(value);
        } else if (PATH_FILL_TYPE.equals(name)) {
            mFillType = parseFillType(value);
        } else if (PATH_STROKE.equals(name)) {
            mStrokeColor = calculateColor(value);
        } else if (PATH_FILL_OPACITY.equals(name)) {
            mFillAlpha = Float.parseFloat(value);
        } else if (PATH_STROKE_OPACTIY.equals(name)) {
            mStrokeAlpha = Float.parseFloat(value);
        } else if (PATH_STROKE_WIDTH.equals(name)) {
            mStrokeWidth = Float.parseFloat(value);
        } else if (PATH_STROKE_LINECAP.equals(name)) {
            if (LINECAP_BUTT.equals(value)) {
                mStrokeLineCap = 0;
            } else if (LINECAP_ROUND.equals(value)) {
                mStrokeLineCap = 1;
            } else if (LINECAP_SQUARE.equals(value)) {
                mStrokeLineCap = 2;
            }
        } else if (PATH_STROKE_LINEJOIN.equals(name)) {
            if (LINEJOIN_MITER.equals(value)) {
                mStrokeLineJoin = 0;
            } else if (LINEJOIN_ROUND.equals(value)) {
                mStrokeLineJoin = 1;
            } else if (LINEJOIN_BEVEL.equals(value)) {
                mStrokeLineJoin = 2;
            }
        } else if (PATH_STROKE_MITERLIMIT.equals(name)) {
            mStrokeMiterlimit = Float.parseFloat(value);
        }
    }

    private static int parseFillType(String value) {
        if (FILL_TYPE_EVEN_ODD.equalsIgnoreCase(value)) {
            return PathIterator.WIND_EVEN_ODD;
        }
        return PathIterator.WIND_NON_ZERO;
    }

    /**
     * Multiply the <code>alpha</code> value into the alpha channel <code>color</code>.
     */
    private static int applyAlpha(int color, float alpha) {
        int alphaBytes = (color >> 24) & 0xff;
        color &= 0x00FFFFFF;
        color |= ((int) (alphaBytes * alpha)) << 24;
        return color;
    }

    /**
     * Draw the current path
     */
    @Override
    public void draw(Graphics2D g, AffineTransform currentMatrix, float scaleX, float scaleY) {
        Path2D path2d = new Path2D.Double(mFillType);
        toPath(path2d);

        // SWing operate the matrix is using pre-concatenate by default.
        // Below is how this is handled in Android framework.
        // pathMatrix.set(groupStackedMatrix);
        // pathMatrix.postScale(scaleX, scaleY);
        g.setTransform(new AffineTransform());
        g.scale(scaleX, scaleY);
        g.transform(currentMatrix);

        // TODO: support clip path here.
        if (mFillColor != 0) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color fillColor = new Color(applyAlpha(mFillColor, mFillAlpha), true);
            g.setColor(fillColor);
            g.fill(path2d);
        }
        if (mStrokeColor != 0) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            BasicStroke stroke = new BasicStroke(mStrokeWidth, mStrokeLineCap, mStrokeLineJoin, mStrokeMiterlimit);
            g.setStroke(stroke);
            Color strokeColor = new Color(applyAlpha(mStrokeColor, mStrokeAlpha), true);
            g.setColor(strokeColor);
            g.draw(path2d);
        }
    }

    @Override
    public void parseAttributes(List<?> attributes) {
        int len = attributes.size();
        for (int i = 0; i < len; i++) {
            String name = ((Attribute)attributes.get(i)).getQualifiedName();
            String value = ((Attribute)attributes.get(i)).getValue();
            setNameValue(name, value);
        }
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    @Override
    public String toString() {
        //noinspection ImplicitArrayToString
        return "Path:" +
                " Name: " + mName +
                " Node: " + mNodeList.toString() +
                " mFillColor: " + Integer.toHexString(mFillColor) +
                " mFillAlpha:" + mFillAlpha +
                " mFillType:" + mFillType +
                " mStrokeColor:" + Integer.toHexString(mStrokeColor) +
                " mStrokeWidth:" + mStrokeWidth +
                " mStrokeAlpha:" + mStrokeAlpha;
    }
}