package com.android.svg.support;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * A svg path drawable used in android projects.
 *
 * You can always create a SVGDrawable object and use it as a Drawable by the Java API.
 * And you can use this directly inside a XML file,  such as src="@drawable/xxx".
 *
 * @author Megatron King
 * @since 2016/8/31 14:46
 */

public class SVGDrawable extends Drawable {

    private static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;

    private SVGDrawableConstantState mState;

    private PorterDuffColorFilter mTintFilter;
    private ColorFilter mColorFilter;

    // Temp variable, only for saving "new" operation at the draw() time.
    private final float[] mTmpFloats = new float[9];
    private final Matrix mTmpMatrix = new Matrix();
    private final Rect mTmpBounds = new Rect();

    private boolean mMutated;

    public SVGDrawable(SVGRenderer renderer) {
        this(new SVGDrawableConstantState(renderer));
    }

    public SVGDrawable(SVGDrawableConstantState state) {
        mState = state;
        mTintFilter = updateTintFilter(state.mTint, state.mTintMode);
    }

    @Override
    public void setAlpha(int alpha) {
        if (getAlpha() != alpha) {
            mState.mRenderer.mAlpha = (float)alpha / 0xFF;
            invalidateSelf();
        }
    }

    public void setWidth(int width) {
        if (getIntrinsicWidth() != width) {
            mState.mRenderer.mWidth = width;
            setBounds(0, 0, getIntrinsicWidth(), getIntrinsicHeight());
            invalidateSelf();
        }
    }

    public void setHeight(int height) {
        if (getIntrinsicHeight() != height) {
            mState.mRenderer.mHeight = height;
            setBounds(0, 0, getIntrinsicWidth(), getIntrinsicHeight());
            invalidateSelf();
        }
    }

    @Override
    public int getAlpha() {
        return (int) (mState.mRenderer.mAlpha * 0xFF);
    }

    @Override
    public int getIntrinsicWidth() {
        return mState.mRenderer.mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mState.mRenderer.mHeight;
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mColorFilter = colorFilter;
        invalidateSelf();
    }

    @Override
    public void setTint(int tint) {
        setTintList(ColorStateList.valueOf(tint));
    }

    @Override
    public void setTintList(ColorStateList tint) {
        final SVGDrawableConstantState state = mState;
        if (state.mTint != tint) {
            state.mTint = tint;
            mTintFilter = updateTintFilter(tint, state.mTintMode);
            invalidateSelf();
        }
    }

    @Override
    public void setTintMode(@NonNull PorterDuff.Mode tintMode) {
        final SVGDrawableConstantState state = mState;
        if (state.mTintMode != tintMode) {
            state.mTintMode = tintMode;
            mTintFilter = updateTintFilter(state.mTint, tintMode);
            invalidateSelf();
        }
    }

    @Override
    public boolean isStateful() {
        return super.isStateful() || (mState != null && mState.mTint != null
                && mState.mTint.isStateful());
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        final SVGDrawableConstantState state = mState;
        if (state.mTint != null && state.mTintMode != null) {
            mTintFilter = updateTintFilter(state.mTint, state.mTintMode);
            invalidateSelf();
            return true;
        }
        return false;
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | mState.getChangingConfigurations();
    }

    /**
     * Ensures the tint filter is consistent with the current tint color and
     * mode.
     */
    private PorterDuffColorFilter updateTintFilter(ColorStateList tint, PorterDuff.Mode tintMode) {
        if (tint == null || tintMode == null) {
            return null;
        }
        // setMode, setColor of PorterDuffColorFilter are not public method in SDK v7.
        // Therefore we create a new one all the time here. Don't expect this is called often.
        final int color = tint.getColorForState(getState(), Color.TRANSPARENT);
        return new PorterDuffColorFilter(color, tintMode);
    }

    @Override
    public ConstantState getConstantState() {
        mState.mChangingConfigurations = getChangingConfigurations();
        return mState;
    }

    @NonNull
    @Override
    public Drawable mutate() {
        if (!mMutated && super.mutate() == this) {
            mState = new SVGDrawableConstantState(mState);
            mMutated = true;
        }
        return this;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // We will offset the bounds for draw, so copyBounds() here instead
        // of getBounds().
        copyBounds(mTmpBounds);
        if (mTmpBounds.width() <= 0 || mTmpBounds.height() <= 0) {
            // Nothing to draw
            return;
        }

        // Color filters always override tint filters.
        final ColorFilter colorFilter = (mColorFilter == null ? mTintFilter : mColorFilter);

        canvas.getMatrix(mTmpMatrix);
        mTmpMatrix.getValues(mTmpFloats);
        float canvasScaleX = Math.abs(mTmpFloats[Matrix.MSCALE_X]);
        float canvasScaleY = Math.abs(mTmpFloats[Matrix.MSCALE_Y]);

        float canvasSkewX = Math.abs(mTmpFloats[Matrix.MSKEW_X]);
        float canvasSkewY = Math.abs(mTmpFloats[Matrix.MSKEW_Y]);

        // When there is any rotation / skew, then the scale value is not valid.
        if (canvasSkewX != 0 || canvasSkewY != 0) {
            canvasScaleX = 1.0f;
            canvasScaleY = 1.0f;
        }

        int scaledWidth = (int) (mTmpBounds.width() * canvasScaleX);
        int scaledHeight = (int) (mTmpBounds.height() * canvasScaleY);

        if (scaledWidth <= 0 || scaledHeight <= 0) {
            return;
        }

        final int saveCount = canvas.save();
        canvas.translate(mTmpBounds.left, mTmpBounds.top);
        // Use the renderer to draw.
        mState.mRenderer.draw(canvas, scaledWidth, scaledHeight, colorFilter, mTmpBounds);

        canvas.restoreToCount(saveCount);
    }

    /**
     * This class is used by {@link SVGDrawable}s to store shared constant state and data
     * between Drawables. The most important, a {@link SVGRenderer} is stored in this class,
     * The render of {@link SVGDrawable} depends on this renderer.
     *
     * <p>
     * {@link #newDrawable(Resources)} can be used as a factory to create new Drawable instances
     * from this ConstantState.
     * </p>
     *
     * Use {@link Drawable#getConstantState()} to retrieve the ConstantState of a Drawable. Calling
     * {@link Drawable#mutate()} on a Drawable should typically create a new ConstantState for that
     * Drawable.
     *
     * @author Megatron King
     * @since 2016/9/1 18:25
     */
    public static class SVGDrawableConstantState extends ConstantState {

        private SVGRenderer mRenderer;

        int mChangingConfigurations;

        PorterDuff.Mode mTintMode = SVGDrawable.DEFAULT_TINT_MODE;
        ColorStateList mTint = null;
        private boolean mAutoMirrored;

        public static SVGDrawableConstantState create(SVGRenderer renderer) {
            return new SVGDrawableConstantState(renderer);
        }

        public SVGDrawableConstantState(SVGRenderer renderer) {
            mRenderer = renderer;
        }

        // Deep copy for mutate() or implicitly mutate.
        private SVGDrawableConstantState(SVGDrawableConstantState copy) {
            if (copy != null) {
                mRenderer = copy.mRenderer.clone();
                mChangingConfigurations = copy.mChangingConfigurations;
                mTint = copy.mTint;
                mTintMode = copy.mTintMode;
                mAutoMirrored = copy.mAutoMirrored;
            }
        }

        @NonNull
        @Override
        public Drawable newDrawable() {
            return new SVGDrawable(this);
        }

        @Override
        public int getChangingConfigurations() {
            return mChangingConfigurations;
        }
    }
}
