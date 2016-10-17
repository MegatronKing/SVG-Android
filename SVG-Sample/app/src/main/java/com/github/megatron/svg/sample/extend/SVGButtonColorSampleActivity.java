package com.github.megatron.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGButton;
import com.github.megatron.svg.sample.R;

public class SVGButtonColorSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_button_color_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGButton view = (SVGButton) findViewById(R.id.extend_button);
        view.setSvgColor(getResources().getColorStateList(R.color.selector_image_color));
    }
}
