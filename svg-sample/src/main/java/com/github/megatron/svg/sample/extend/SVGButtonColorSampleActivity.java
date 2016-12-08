package com.github.megatron.svg.sample.extend;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.svg.support.extend.SVGButton;
import com.github.megatron.svg.sample.R;

public class SVGButtonColorSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_button_color_sample);
        setTitle(getIntent().getStringExtra("title"));

        // set in code
        SVGButton view1 = (SVGButton) findViewById(R.id.extend_button1);
        view1.setLeftSvgColor(getResources().getColorStateList(R.color.selector_image_color));
        view1.setTopSvgColor(Color.GREEN);
        view1.setRightSvgColor(Color.CYAN);
        view1.setBottomSvgColor(Color.BLUE);

        SVGButton view2 = (SVGButton) findViewById(R.id.extend_button2);
        view2.setLeftSvgColor(getResources().getColorStateList(R.color.selector_image_color));
        view2.setTopSvgColor(Color.GREEN);
        view2.setRightSvgColor(Color.CYAN);
        view2.setBottomSvgColor(Color.BLUE);
        view2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_android_red,
                R.drawable.ic_android_red, R.drawable.ic_android_red, R.drawable.ic_android_red);

        // influence all compound drawables
        // view.setSvgColor(Color.BLACK);
    }
}
