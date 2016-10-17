package com.github.megatron.svg.sample.graphics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.megatron.svg.sample.R;

public class SizeSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_size_sample);
        setTitle(getIntent().getStringExtra("title"));
    }
}
