package com.android.svg.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.svg.sample.drawables.ic_pets_black;
import com.android.svg.support.SVGDrawable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a svg drawable in java code
        SVGDrawable drawable = new SVGDrawable(new ic_pets_black(this));
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageDrawable(drawable);
    }
}
