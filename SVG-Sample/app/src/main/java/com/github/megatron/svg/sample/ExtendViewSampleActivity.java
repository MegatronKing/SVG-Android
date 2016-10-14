package com.github.megatron.svg.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGColorView;

public class ExtendViewSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_view_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGColorView view = (SVGColorView) findViewById(R.id.extend_view);
        view.setImageColor(getResources().getColorStateList(R.color.selector_image_color));
    }
}
