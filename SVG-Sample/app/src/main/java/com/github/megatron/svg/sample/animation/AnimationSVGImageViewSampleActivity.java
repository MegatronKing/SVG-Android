package com.github.megatron.svg.sample.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;

import com.android.svg.support.extend.SVGImageView;
import com.github.megatron.svg.sample.R;

public class AnimationSVGImageViewSampleActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_svg_imageview_sample);
        setTitle(getIntent().getStringExtra("title"));

        final SVGImageView imageView1 = (SVGImageView) findViewById(R.id.animation_svgimageview_image1);
        ObjectAnimator animatorRotation = ObjectAnimator.ofFloat(imageView1, "svgRotation", 0, 360);
        animatorRotation.setDuration(2000);
        animatorRotation.setRepeatCount(ValueAnimator.INFINITE);
        animatorRotation.setInterpolator(new LinearInterpolator());
        animatorRotation.start();

        SVGImageView imageView2 = (SVGImageView) findViewById(R.id.animation_svgimageview_image2);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(imageView2, "svgAlpha", 0, 1);
        animatorAlpha.setDuration(4000);
        animatorAlpha.setRepeatCount(ValueAnimator.INFINITE);
        animatorAlpha.setRepeatMode(ValueAnimator.REVERSE);
        animatorAlpha.setInterpolator(new LinearInterpolator());
        animatorAlpha.start();

        final SVGImageView imageView3 = (SVGImageView) findViewById(R.id.animation_svgimageview_image3);
        ObjectAnimator animatorWidth = ObjectAnimator.ofInt(imageView3, "svgWidth", 50, 150);
        animatorWidth.setDuration(2000);
        animatorWidth.setInterpolator(new LinearInterpolator());
        animatorWidth.setRepeatCount(ValueAnimator.INFINITE);
        animatorWidth.setRepeatMode(ValueAnimator.REVERSE);
        animatorWidth.start();
        animatorWidth.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // There is a bug in ImageView(ImageButton), in this case, we must call requestLayout() here.
                imageView3.requestLayout();
            }
        });

        SVGImageView imageView4 = (SVGImageView) findViewById(R.id.animation_svgimageview_image4);
        ObjectAnimator animatorColor = ObjectAnimator.ofInt(imageView4, "svgColor", Color.BLACK, Color.BLUE);
        animatorColor.setDuration(2000);
        animatorColor.setRepeatCount(ValueAnimator.INFINITE);
        animatorColor.setRepeatMode(ValueAnimator.REVERSE);
        animatorColor.setInterpolator(new LinearInterpolator());
        animatorColor.start();

    }
}
