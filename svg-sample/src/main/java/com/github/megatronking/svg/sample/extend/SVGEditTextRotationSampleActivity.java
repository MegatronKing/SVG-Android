package com.github.megatronking.svg.sample.extend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.megatronking.svg.sample.R;
import com.github.megatronking.svg.support.extend.SVGEditText;


public class SVGEditTextRotationSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_edittext_rotation_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGEditText view1 = (SVGEditText) findViewById(R.id.extend_edittext1);
        view1.setLeftSvgRotation(90);
        view1.setTopSvgRotation(180);
        view1.setRightSvgRotation(270);
        view1.setBottomSvgRotation(360);

        SVGEditText view2 = (SVGEditText) findViewById(R.id.extend_edittext2);
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
