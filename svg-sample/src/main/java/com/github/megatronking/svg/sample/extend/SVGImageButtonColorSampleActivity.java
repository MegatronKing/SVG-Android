package com.github.megatronking.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.megatronking.svg.sample.R;
import com.github.megatronking.svg.support.extend.SVGImageButton;


public class SVGImageButtonColorSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_imagebutton_color_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGImageButton view1 = (SVGImageButton) findViewById(R.id.extend_imagebutton1);
        view1.setSvgColor(getResources().getColorStateList(R.color.selector_image_color));

        SVGImageButton view2 = (SVGImageButton) findViewById(R.id.extend_imagebutton2);
        view2.setSvgColor(getResources().getColorStateList(R.color.selector_image_color));
        view2.setImageResource(R.drawable.ic_android_red);
    }
}
