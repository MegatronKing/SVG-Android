package com.github.megatronking.svg.sample.graphics;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.github.megatronking.svg.sample.R;


public class AlphaSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_alpha_sample);
        setTitle(getIntent().getStringExtra("title"));

        ImageView imageView = (ImageView) findViewById(R.id.alpha_sample_image);
        final Drawable drawable = imageView.getDrawable();
        // must mutate
        drawable.mutate();

        final SeekBar seekBar = (SeekBar) findViewById(R.id.alpha_sample_seekbar);
        seekBar.setMax(255);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawable.setAlpha(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(50);
    }
}
