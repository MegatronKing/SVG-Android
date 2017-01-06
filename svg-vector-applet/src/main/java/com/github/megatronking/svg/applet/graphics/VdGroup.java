package com.github.megatronking.svg.applet.graphics;

import org.dom4j.Attribute;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to represent one VectorDrawable's group element.
 */
class VdGroup extends VdElement {

    private static final String GROUP_ROTATION = "android:rotation";
    private static final String GROUP_PIVOTX = "android:pivotX";
    private static final String GROUP_PIVOTY = "android:pivotY";
    private static final String GROUP_TRANSLATEX = "android:translateX";
    private static final String GROUP_TRANSLATEY = "android:translateY";
    private static final String GROUP_SCALEX = "android:scaleX";
    private static final String GROUP_SCALEY = "android:scaleY";
    private static final String GROUP_NAME = "android:name";

    private float mRotate = 0;
    private float mPivotX = 0;
    private float mPivotY = 0;
    private float mScaleX = 1;
    private float mScaleY = 1;
    private float mTranslateX = 0;
    private float mTranslateY = 0;

    // Used at draw time, basically accumulative matrix from root to current group.
    private AffineTransform mTempStackedMatrix = new AffineTransform();

    // The current group's transformation.
    private AffineTransform mLocalMatrix = new AffineTransform();

    // Children can be either a {@link VdPath} or {@link VdGroup}
    private ArrayList<VdElement> mChildren = new ArrayList<>();

    void add(VdElement pathOrGroup) {
        mChildren.add(pathOrGroup);
    }

    // Src = trans * src, this is called preConcatenate() in Swing, but postConcatenate() in Android
    private void androidPostTransform(AffineTransform src, AffineTransform trans) {
        src.preConcatenate(trans);
    }

    private void updateLocalMatrix() {
        // The order we apply is the same as the
        // RenderNode.cpp::applyViewPropertyTransforms().
        mLocalMatrix.setToIdentity();

        // In Android framework, the transformation is applied in
        // VectorDrawable.java VGroup::updateLocalMatrix()
        AffineTransform tempTrans = new AffineTransform();
        tempTrans.setToIdentity();
        tempTrans.translate(-mPivotX, -mPivotY);
        androidPostTransform(mLocalMatrix, tempTrans);

        tempTrans.setToIdentity();
        tempTrans.scale(mScaleX, mScaleY);
        androidPostTransform(mLocalMatrix, tempTrans);

        tempTrans.setToIdentity();
        tempTrans.rotate(mRotate * 3.1415926 / 180, 0, 0);
        androidPostTransform(mLocalMatrix, tempTrans);

        tempTrans.setToIdentity();
        tempTrans.translate(mTranslateX + mPivotX, mTranslateY + mPivotY);
        androidPostTransform(mLocalMatrix, tempTrans);
    }

    @Override
    public void draw(Graphics2D g, AffineTransform currentMatrix, float scaleX, float scaleY) {
        // SWING default is pre-concatenate
        mTempStackedMatrix.setTransform(currentMatrix);
        mTempStackedMatrix.concatenate(mLocalMatrix);

        for (int i = 0; i < mChildren.size(); i++) {
            mChildren.get(i).draw(g, mTempStackedMatrix, scaleX, scaleY);
        }
    }

    private void setNameValue(String name, String value) {
        if (GROUP_ROTATION.equals(name)) {
            mRotate = Float.parseFloat(value);
        } else if (GROUP_PIVOTX.equals(name)) {
            mPivotX = Float.parseFloat(value);
        } else if (GROUP_PIVOTY.equals(name)) {
            mPivotY = Float.parseFloat(value);
        } else if (GROUP_TRANSLATEX.equals(name)) {
            mTranslateX = Float.parseFloat(value);
        } else if (GROUP_TRANSLATEY.equals(name)) {
            mTranslateY = Float.parseFloat(value);
        } else if (GROUP_SCALEX.equals(name)) {
            mScaleX = Float.parseFloat(value);
        } else if (GROUP_SCALEY.equals(name)) {
            mScaleY = Float.parseFloat(value);
        } else if (GROUP_NAME.equals(name)) {
            mName = value;
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
        updateLocalMatrix();
    }

    @Override
    public boolean isGroup() {
        return true;
    }
}