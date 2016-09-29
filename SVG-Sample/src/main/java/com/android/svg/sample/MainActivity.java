package com.android.svg.sample;

import android.graphics.Color;
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

        // ---------------------------------------------------

        // create a svg drawable in java code
        SVGDrawable drawable1 = new SVGDrawable(new ic_pets_black(this));
        ImageView image1 = (ImageView) findViewById(R.id.image1);
        image1.setImageDrawable(drawable1);

        // support alpha
        SVGDrawable drawable2 = new SVGDrawable(new ic_pets_black(this));
        ImageView image2 = (ImageView) findViewById(R.id.image2);
        drawable2.setAlpha(128);
        image2.setImageDrawable(drawable2);

        // support tint, change color black to red
        SVGDrawable drawable3 = new SVGDrawable(new ic_pets_black(this));
        drawable3.setTint(Color.RED);
        ImageView image3 = (ImageView) findViewById(R.id.image3);
        image3.setImageDrawable(drawable3);

        // ---------------------------------------------------

        // use a drawable resource id
        ImageView image4 = (ImageView) findViewById(R.id.image4);
        image4.setImageResource(R.drawable.ic_assignment_returned);

        // support alpha for view
        ImageView image5 = (ImageView) findViewById(R.id.image5);
        image5.setImageResource(R.drawable.ic_assignment_returned);
        image5.setAlpha(0.5f);

        // support scaleType
        ImageView image6 = (ImageView) findViewById(R.id.image6);
        image6.setImageResource(R.drawable.ic_assignment_returned);
        image6.setScaleType(ImageView.ScaleType.CENTER);
    }
}
