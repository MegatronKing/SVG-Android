package com.github.megatron.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGImageButton;
import com.github.megatron.svg.sample.R;

public class SVGImageButtonRotationSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_imagebutton_rotation_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGImageButton view1 = (SVGImageButton) findViewById(R.id.extend_imagebutton1);
        view1.setSvgRotation(180);

        SVGImageButton view2 = (SVGImageButton) findViewById(R.id.extend_imagebutton2);
        view2.setSvgRotation(180);
        view2.setImageResource(R.drawable.ic_android_red);
    }
}
