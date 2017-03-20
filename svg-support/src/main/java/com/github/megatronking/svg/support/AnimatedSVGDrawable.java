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

package com.github.megatronking.svg.support;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * This class uses {@link android.animation.ObjectAnimator} and
 * {@link android.animation.AnimatorSet} to animate the properties of a
 * {@link SVGDrawable} to create an animated drawable.
 *
 * @author Megatron King
 * @since 2016/12/08 15:28
 */
public class AnimatedSVGDrawable extends Drawable implements Animatable {

    private AnimatedSVGDrawableState mAnimatedSVGState;

    private boolean mMutated;

    public AnimatedSVGDrawable(SVGRenderer renderer) {
        mAnimatedSVGState = new AnimatedSVGDrawableState(renderer);
        mAnimatedSVGState.mSVGDrawable.setCallback(mCallback);
    }

    public AnimatedSVGDrawable(SVGDrawable drawable) {
        mAnimatedSVGState = new AnimatedSVGDrawableState(drawable);
        mAnimatedSVGState.mSVGDrawable.setCallback(mCallback);
    }

    private AnimatedSVGDrawable(AnimatedSVGDrawableState state, Resources res) {
        mAnimatedSVGState = new AnimatedSVGDrawableState(state, mCallback, res);
    }

    @Override
    public @NonNull Drawable mutate() {
        if (!mMutated && super.mutate() == this) {
            mAnimatedSVGState = new AnimatedSVGDrawableState(
                    mAnimatedSVGState, mCallback, null);
            mMutated = true;
        }
        return this;
    }

    @Override
    public ConstantState getConstantState() {
        mAnimatedSVGState.mChangingConfigurations = getChangingConfigurations();
        return mAnimatedSVGState;
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | mAnimatedSVGState.mChangingConfigurations;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        mAnimatedSVGState.mSVGDrawable.draw(canvas);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mAnimatedSVGState.mSVGDrawable.setBounds(bounds);
    }

    @Override
    protected boolean onStateChange(int[] state) {
        return mAnimatedSVGState.mSVGDrawable.setState(state);
    }

    @Override
    protected boolean onLevelChange(int level) {
        return mAnimatedSVGState.mSVGDrawable.setLevel(level);
    }

    @Override
    public int getAlpha() {
        return mAnimatedSVGState.mSVGDrawable.getAlpha();
    }

    public void setAlpha(int alpha) {
        mAnimatedSVGState.mSVGDrawable.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        mAnimatedSVGState.mSVGDrawable.setColorFilter(colorFilter);
    }

    @Override
    public void setTintList(ColorStateList tint) {
        mAnimatedSVGState.mSVGDrawable.setTintList(tint);
    }

    @Override
    public void setTintMode(@NonNull PorterDuff.Mode tintMode) {
        mAnimatedSVGState.mSVGDrawable.setTintMode(tintMode);
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        mAnimatedSVGState.mSVGDrawable.setVisible(visible, restart);
        return super.setVisible(visible, restart);
    }

    @Override
    public boolean isStateful() {
        return mAnimatedSVGState.mSVGDrawable.isStateful();
    }

    @Override
    public int getOpacity() {
        return mAnimatedSVGState.mSVGDrawable.getOpacity();
    }

    @Override
    public int getIntrinsicWidth() {
        return mAnimatedSVGState.mSVGDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mAnimatedSVGState.mSVGDrawable.getIntrinsicHeight();
    }

    private static class AnimatedSVGDrawableState extends ConstantState {

        int mChangingConfigurations;
        SVGDrawable mSVGDrawable;
        ArrayList<Animator> mAnimators;

        private AnimatedSVGDrawableState(SVGRenderer renderer) {
            this(new SVGDrawable(renderer));
        }

        private AnimatedSVGDrawableState(SVGDrawable drawable) {
            mSVGDrawable = drawable;
        }

        private AnimatedSVGDrawableState(AnimatedSVGDrawableState copy, Callback owner, Resources res) {
            if (copy != null) {
                mChangingConfigurations = copy.mChangingConfigurations;
                if (copy.mSVGDrawable != null) {
                    final ConstantState cs = copy.mSVGDrawable.getConstantState();
                    if (cs != null) {
                        if (res != null) {
                            mSVGDrawable = (SVGDrawable) cs.newDrawable(res);
                        } else {
                            mSVGDrawable = (SVGDrawable) cs.newDrawable();
                        }
                    } else {
                        return;
                    }
                    mSVGDrawable = (SVGDrawable) mSVGDrawable.mutate();
                    mSVGDrawable.setCallback(owner);
                    mSVGDrawable.setBounds(copy.mSVGDrawable.getBounds());
                }
                if (copy.mAnimators != null) {
                    final int numAnimators = copy.mAnimators.size();
                    mAnimators = new ArrayList<>(numAnimators);
                    for (int i = 0; i < numAnimators; ++i) {
                        Animator anim = copy.mAnimators.get(i);
                        Animator animClone = anim.clone();
                        animClone.setTarget(mSVGDrawable);
                        mAnimators.add(animClone);
                    }
                }
            }
        }

        @Override
        @NonNull
        public Drawable newDrawable() {
            return new AnimatedSVGDrawable(this, null);
        }

        @Override
        @NonNull
        public Drawable newDrawable(Resources res) {
            return new AnimatedSVGDrawable(this, res);
        }

        @Override
        public int getChangingConfigurations() {
            return mChangingConfigurations;
        }

    }

    private final Callback mCallback = new Callback() {

        @Override
        public void invalidateDrawable(@NonNull Drawable who) {
            invalidateSelf();
        }

        @Override
        public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
            scheduleSelf(what, when);
        }

        @Override
        public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
            unscheduleSelf(what);
        }
    };

    public void setPivotX(float pivotX) {
        if (getPivotX() != pivotX) {
            mAnimatedSVGState.mSVGDrawable.setPivotX(pivotX);
        }
    }

    public float getPivotX() {
        return mAnimatedSVGState.mSVGDrawable.getPivotX();
    }

    public void setPivotY(float pivotY) {
        if (getPivotY() != pivotY) {
            mAnimatedSVGState.mSVGDrawable.setPivotY(pivotY);
        }
    }

    public float getPivotY() {
        return mAnimatedSVGState.mSVGDrawable.getPivotY();
    }

    public void setupAnimators(Animator animator) {
        if (animator == null) {
            return;
        }
        animator.setTarget(mAnimatedSVGState.mSVGDrawable);
        if (mAnimatedSVGState.mAnimators == null) {
            mAnimatedSVGState.mAnimators = new ArrayList<>();
        }
        mAnimatedSVGState.mAnimators.add(animator);
    }

    public void setupAnimators(Context context, int animResId) {
        setupAnimators(AnimatorInflater.loadAnimator(context, animResId));
    }

    public void setdownAnimators(Animator animator) {
        if (animator == null || mAnimatedSVGState.mAnimators == null) {
            return;
        }
        mAnimatedSVGState.mAnimators.remove(animator);
    }

    @Override
    public boolean isRunning() {
        final ArrayList<Animator> animators = mAnimatedSVGState.mAnimators;
        if (animators == null || animators.isEmpty()) {
            return false;
        }
        final int size = animators.size();
        for (int i = 0; i < size; i++) {
            final Animator animator = animators.get(i);
            if (animator.isRunning()) {
                return true;
            }
        }
        return false;
    }

    private boolean isStarted() {
        final ArrayList<Animator> animators = mAnimatedSVGState.mAnimators;
        if (animators == null || animators.isEmpty()) {
            return false;
        }
        final int size = animators.size();
        for (int i = 0; i < size; i++) {
            final Animator animator = animators.get(i);
            if (animator.isStarted()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        // If any one of the animator has not ended, do nothing.
        if (isStarted()) {
            return;
        }
        // Otherwise, kick off every animator.
        final ArrayList<Animator> animators = mAnimatedSVGState.mAnimators;
        if (animators == null || animators.isEmpty()) {
            return;
        }
        final int size = animators.size();
        for (int i = 0; i < size; i++) {
            final Animator animator = animators.get(i);
            animator.start();
        }
        invalidateSelf();
    }

    @Override
    public void stop() {
        final ArrayList<Animator> animators = mAnimatedSVGState.mAnimators;
        if (animators == null || animators.isEmpty()) {
            return;
        }
        final int size = animators.size();
        for (int i = 0; i < size; i++) {
            final Animator animator = animators.get(i);
            animator.end();
        }
    }

    public void reset() {
        stop();
        mAnimatedSVGState.mSVGDrawable.setTranslationX(0);
        mAnimatedSVGState.mSVGDrawable.setTranslationY(0);
        mAnimatedSVGState.mSVGDrawable.setScaleX(1.0f);
        mAnimatedSVGState.mSVGDrawable.setScaleY(1.0f);
        mAnimatedSVGState.mSVGDrawable.setRotation(0);
        mAnimatedSVGState.mSVGDrawable.setAlpha(0xFF);
    }

}