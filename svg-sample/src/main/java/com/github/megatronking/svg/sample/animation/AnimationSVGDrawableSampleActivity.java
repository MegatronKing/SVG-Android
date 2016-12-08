package com.github.megatronking.svg.sample.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.megatronking.svg.sample.R;
import com.github.megatronking.svg.sample.drawables.ic_android_red;
import com.github.megatronking.svg.support.AnimatedSVGDrawable;

public class AnimationSVGDrawableSampleActivity extends AppCompatActivity {

    private AnimatedSVGDrawable mSVGDrawable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_svg_drawable_sample);
        setTitle(getIntent().getStringExtra("title"));

        ImageView imageView = (ImageView) findViewById(R.id.animation_svgdrawable_image);
        mSVGDrawable = new AnimatedSVGDrawable(new ic_android_red(this));
        mSVGDrawable.setPivotX(0);
        mSVGDrawable.setPivotY(0);

        imageView.setImageDrawable(mSVGDrawable);

        final Animator animatorTranslationX = AnimatorInflater.loadAnimator(this, R.animator.animator_translation_x);
        final Animator animatorTranslationY = AnimatorInflater.loadAnimator(this, R.animator.animator_translation_y);
        final Animator animatorScaleX = AnimatorInflater.loadAnimator(this, R.animator.animator_scale_x);
        final Animator animatorScaleY = AnimatorInflater.loadAnimator(this, R.animator.animator_scale_y);
        final Animator animatorRotation = AnimatorInflater.loadAnimator(this, R.animator.animator_rotation);
        final Animator animatorAlpha = AnimatorInflater.loadAnimator(this, R.animator.animator_alpha);

        final TextView textTranslationX = (TextView) findViewById(R.id.animation_translationX);
        final TextView textTranslationY = (TextView) findViewById(R.id.animation_translationY);
        final TextView textScaleX = (TextView) findViewById(R.id.animation_scaleX);
        final TextView textScaleY = (TextView) findViewById(R.id.animation_scaleY);
        final TextView textRotation = (TextView) findViewById(R.id.animation_rotation);
        final TextView textAlpha = (TextView) findViewById(R.id.animation_alpha);

        final TextView textPivotX = (TextView) findViewById(R.id.animation_pivotX);
        final TextView textPivotY = (TextView) findViewById(R.id.animation_pivotY);

        textPivotX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAnimation();
                mSVGDrawable.setPivotX(textPivotX.isSelected() ? 0 : 0.5f);
                textPivotX.setSelected(!textPivotX.isSelected());
                startAnimation();
            }
        });
        textPivotY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAnimation();
                mSVGDrawable.setPivotY(textPivotY.isSelected() ? 0 : 0.5f);
                textPivotY.setSelected(!textPivotY.isSelected());
                startAnimation();
            }
        });


        bindAnimator(textTranslationX, animatorTranslationX);
        bindAnimator(textTranslationY, animatorTranslationY);
        bindAnimator(textScaleX, animatorScaleX);
        bindAnimator(textScaleY, animatorScaleY);
        bindAnimator(textRotation, animatorRotation);
        bindAnimator(textAlpha, animatorAlpha);
    }

    private void bindAnimator(final TextView textView, final Animator animator) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAnimation();
                if (textView.isSelected()) {
                    mSVGDrawable.setdownAnimators(animator);
                } else {
                    mSVGDrawable.setupAnimators(animator);
                }
                textView.setSelected(!textView.isSelected());
                startAnimation();
            }
        });
    }

    private void startAnimation() {
        mSVGDrawable.start();
    }

    private void stopAnimation() {
        mSVGDrawable.reset();
    }
}
