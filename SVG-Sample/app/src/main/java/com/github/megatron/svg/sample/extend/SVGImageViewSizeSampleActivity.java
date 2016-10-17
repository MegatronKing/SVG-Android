package com.github.megatron.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGImageView;
import com.github.megatron.svg.sample.R;
import com.github.megatron.svg.sample.utils.DimenUtils;

public class SVGImageViewSizeSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_imageview_size_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGImageView view = (SVGImageView) findViewById(R.id.extend_imageview);
        view.setSvgSize(DimenUtils.dip2px(this, 24), DimenUtils.dip2px(this, 96));
    }
}
