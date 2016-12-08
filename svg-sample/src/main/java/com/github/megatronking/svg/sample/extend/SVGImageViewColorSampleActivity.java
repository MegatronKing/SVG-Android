package com.github.megatronking.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.megatronking.svg.sample.R;
import com.github.megatronking.svg.support.extend.SVGImageView;


public class SVGImageViewColorSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_imageview_color_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGImageView view1 = (SVGImageView) findViewById(R.id.extend_imageview1);
        view1.setSvgColor(getResources().getColorStateList(R.color.selector_image_color));

        SVGImageView view2 = (SVGImageView) findViewById(R.id.extend_imageview2);
        view2.setSvgColor(getResources().getColorStateList(R.color.selector_image_color));
        view2.setImageResource(R.drawable.ic_android_red);
    }
}
