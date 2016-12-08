package com.github.megatron.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGEditText;
import com.github.megatron.svg.sample.R;

public class SVGEditTextAlphaSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_edittext_alpha_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGEditText view1 = (SVGEditText) findViewById(R.id.extend_edittext1);
        view1.setLeftSvgAlpha(0.2f);
        view1.setTopSvgAlpha(0.4f);
        view1.setRightSvgAlpha(0.6f);
        view1.setBottomSvgAlpha(0.8f);

        SVGEditText view2 = (SVGEditText) findViewById(R.id.extend_edittext2);
        view2.setLeftSvgAlpha(0.2f);
        view2.setTopSvgAlpha(0.4f);
        view2.setRightSvgAlpha(0.6f);
        view2.setBottomSvgAlpha(0.8f);
        view2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_android_red,
                R.drawable.ic_android_red, R.drawable.ic_android_red, R.drawable.ic_android_red);

        // influence all compound drawables
        // view.setSvgAlpha(0.5f);
    }
}
