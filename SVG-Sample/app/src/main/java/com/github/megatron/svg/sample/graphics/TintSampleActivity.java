package com.github.megatron.svg.sample.graphics;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.android.svg.support.SVGDrawable;
import com.github.megatron.svg.sample.R;

public class TintSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics_tint_sample);
        setTitle(getIntent().getStringExtra("title"));

        ImageView imageView = (ImageView) findViewById(R.id.tint_sample_image);
        final Drawable drawable = imageView.getDrawable();
        // must mutate
        drawable.mutate();

        final SeekBar seekBarAlpha = (SeekBar) findViewById(R.id.tint_sample_seekbar_alpha);
        final SeekBar seekBarRed = (SeekBar) findViewById(R.id.tint_sample_seekbar_red);
        final SeekBar seekBarGreen = (SeekBar) findViewById(R.id.tint_sample_seekbar_green);
        final SeekBar seekBarBlue = (SeekBar) findViewById(R.id.tint_sample_seekbar_blue);

        SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setDrawableTint((SVGDrawable) drawable, seekBarAlpha.getProgress(), seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        seekBarAlpha.setMax(255);
        seekBarAlpha.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        seekBarAlpha.setProgress(255);

        seekBarRed.setMax(255);
        seekBarRed.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        seekBarRed.setProgress(0);

        seekBarGreen.setMax(255);
        seekBarGreen.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        seekBarGreen.setProgress(0);

        seekBarBlue.setMax(255);
        seekBarBlue.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        seekBarBlue.setProgress(0);
    }

    private void setDrawableTint(SVGDrawable drawable, int alpha, int red, int green, int blue) {
        drawable.setTint(Color.argb(alpha, red, green, blue));
    }
}
