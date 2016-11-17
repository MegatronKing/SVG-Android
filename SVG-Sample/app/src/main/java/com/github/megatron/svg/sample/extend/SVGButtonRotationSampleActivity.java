package com.github.megatron.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGButton;
import com.github.megatron.svg.sample.R;

public class SVGButtonRotationSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_button_rotation_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGButton view1 = (SVGButton) findViewById(R.id.extend_button1);
        view1.setLeftSvgRotation(90);
        view1.setTopSvgRotation(180);
        view1.setRightSvgRotation(270);
        view1.setBottomSvgRotation(360);

        SVGButton view2 = (SVGButton) findViewById(R.id.extend_button2);
        view2.setLeftSvgRotation(90);
        view2.setTopSvgRotation(180);
        view2.setRightSvgRotation(270);
        view2.setBottomSvgRotation(360);
        view2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_android_red,
                R.drawable.ic_android_red, R.drawable.ic_android_red, R.drawable.ic_android_red);

        // influence all compound drawables
        // view.setLeftSvgRotation(90);
    }
}
