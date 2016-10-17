package com.github.megatron.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGImageButton;
import com.github.megatron.svg.sample.R;

public class SVGImageButtonAlphaSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_imagebutton_alpha_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGImageButton view = (SVGImageButton) findViewById(R.id.extend_imagebutton);
        view.setSvgAlpha(0.5f);
    }
}
