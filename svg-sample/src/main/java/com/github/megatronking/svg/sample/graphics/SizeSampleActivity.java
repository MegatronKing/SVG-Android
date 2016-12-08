package com.github.megatronking.svg.sample.graphics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.megatronking.svg.sample.R;


public class SizeSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_size_sample);
        setTitle(getIntent().getStringExtra("title"));

        Toast.makeText(this, "click to scale image!", Toast.LENGTH_LONG).show();

        final ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.LayoutParams lp = imageView.getLayoutParams();
                lp.width = imageView.getMeasuredWidth() + 100;
                lp.height = imageView.getMeasuredHeight() + 100;
                imageView.setLayoutParams(lp);
            }
        });
    }
}
