package com.github.megatron.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGView;
import com.github.megatron.svg.sample.R;

public class SVGViewColorSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_view_color_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGView view = (SVGView) findViewById(R.id.extend_view);
        view.setSvgColor(getResources().getColorStateList(R.color.selector_image_color));
    }
}
