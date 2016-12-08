package com.github.megatron.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGButton;
import com.github.megatron.svg.sample.R;
import com.github.megatron.svg.sample.utils.DimenUtils;

public class SVGButtonSizeSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_button_size_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGButton view1 = (SVGButton) findViewById(R.id.extend_button1);
        view1.setLeftSvgSize(DimenUtils.dip2px(this, 24), DimenUtils.dip2px(this, 24));
        view1.setRightSvgSize(DimenUtils.dip2px(this, 24), DimenUtils.dip2px(this, 24));
        view1.setTopSvgSize(DimenUtils.dip2px(this, 48), DimenUtils.dip2px(this, 48));
        view1.setBottomSvgSize(DimenUtils.dip2px(this, 48), DimenUtils.dip2px(this, 48));

        SVGButton view2 = (SVGButton) findViewById(R.id.extend_button2);
        view2.setLeftSvgSize(DimenUtils.dip2px(this, 24), DimenUtils.dip2px(this, 24));
        view2.setRightSvgSize(DimenUtils.dip2px(this, 24), DimenUtils.dip2px(this, 24));
        view2.setTopSvgSize(DimenUtils.dip2px(this, 48), DimenUtils.dip2px(this, 48));
        view2.setBottomSvgSize(DimenUtils.dip2px(this, 48), DimenUtils.dip2px(this, 48));
        view2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_android_red,
                R.drawable.ic_android_red, R.drawable.ic_android_red, R.drawable.ic_android_red);

        // influence all compound drawables
        // view.setSvgSize(DimenUtils.dip2px(this, 96), DimenUtils.dip2px(this, 96));
    }
}
