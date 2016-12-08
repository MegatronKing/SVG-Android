package com.github.megatronking.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.megatronking.svg.sample.R;
import com.github.megatronking.svg.support.extend.SVGView;


public class SVGViewColorSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_view_color_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGView view1 = (SVGView) findViewById(R.id.extend_view1);
        view1.setSvgColor(getResources().getColorStateList(R.color.selector_image_color));

        SVGView view2 = (SVGView) findViewById(R.id.extend_view2);
        view2.setSvgColor(getResources().getColorStateList(R.color.selector_image_color));
        view2.setBackgroundResource(R.drawable.ic_android_red);
    }
}
