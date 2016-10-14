package com.github.megatron.svg.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGColorImageButton;

public class ExtendImageButtonSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_imagebutton_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGColorImageButton view = (SVGColorImageButton) findViewById(R.id.extend_imagebutton);
        view.setImageColor(getResources().getColorStateList(R.color.selector_image_color));
    }
}
