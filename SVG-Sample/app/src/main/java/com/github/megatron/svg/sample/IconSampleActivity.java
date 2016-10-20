package com.github.megatron.svg.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class IconSampleActivity extends AppCompatActivity {

    private static final Integer[] ICONS = new Integer[] {
            R.drawable.ic_svg_01, R.drawable.ic_svg_02, R.drawable.ic_svg_03,
            R.drawable.ic_svg_04, R.drawable.ic_svg_05, R.drawable.ic_svg_06,
            R.drawable.ic_svg_07, R.drawable.ic_svg_08, R.drawable.ic_svg_09,
            R.drawable.ic_svg_10, R.drawable.ic_svg_11, R.drawable.ic_svg_12,
            R.drawable.ic_sample_01, R.drawable.ic_sample_02,
            R.drawable.ic_sample_03, R.drawable.ic_sample_04, R.drawable.ic_sample_05,
            R.drawable.ic_sample_06, R.drawable.ic_sample_07, R.drawable.ic_sample_08,
            R.drawable.ic_sample_09, R.drawable.ic_sample_10, R.drawable.ic_sample_11,
            R.drawable.ic_sample_12, R.drawable.ic_sample_13, R.drawable.ic_sample_14,
            R.drawable.ic_sample_15, R.drawable.ic_sample_16, R.drawable.ic_sample_17,
            R.drawable.ic_sample_18, R.drawable.ic_sample_19, R.drawable.ic_sample_20
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_sample);
        setTitle(getIntent().getStringExtra("title"));

        GridView gridView = (GridView) findViewById(R.id.icon_grid);
        gridView.setAdapter(new ArrayAdapter<Integer>(this, R.layout.item_icon, ICONS) {

            @NonNull
            @Override
            public View getView(int position, android.view.View convertView, @NonNull ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(IconSampleActivity.this).inflate(R.layout.item_icon, parent, false);
                    holder = new ViewHolder();
                    holder.imageview = (ImageView)(convertView.findViewById(R.id.item_image));
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.imageview.setImageResource(ICONS[position]);
                return convertView;
            }
        });
    }

    private class ViewHolder {

        private ImageView imageview;

    }
}