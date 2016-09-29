package com.android.svg.sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.svg.sample.drawables.ic_pets_black;
import com.android.svg.support.SVGDrawable;

public class MainActivity extends AppCompatActivity {

    public static int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a svg drawable in java code
        SVGDrawable drawable = new SVGDrawable(new ic_pets_black(this));
        // support alpha
        drawable.setAlpha(128);
        // support tint, change color black to red
        drawable.setTint(Color.RED);
        ImageView image1 = (ImageView) findViewById(R.id.image1);
        image1.setImageDrawable(drawable);

        ImageView image2 = (ImageView) findViewById(R.id.image2);
        image2.setImageResource(R.drawable.ic_assignment_returned);

        if (a < 1) {
            image1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    a ++;
                }
            }, 2000);
        }
    }
}
