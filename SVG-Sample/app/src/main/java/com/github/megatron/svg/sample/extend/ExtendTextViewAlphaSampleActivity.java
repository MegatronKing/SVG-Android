package com.github.megatron.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGTextView;
import com.github.megatron.svg.sample.R;

public class ExtendTextViewAlphaSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_textview_alpha_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGTextView view = (SVGTextView) findViewById(R.id.extend_textview);
        view.setSvgAlpha(0.5f);
    }
}
