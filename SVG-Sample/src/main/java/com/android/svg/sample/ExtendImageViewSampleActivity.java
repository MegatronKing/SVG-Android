package com.android.svg.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGColorImageView;

public class ExtendImageViewSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_imageview_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGColorImageView view = (SVGColorImageView) findViewById(R.id.extend_imageview);
        view.setImageColor(getResources().getColorStateList(R.color.selector_image_color));
    }
}
