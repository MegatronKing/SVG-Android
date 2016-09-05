package com.android.svg.sample;

import android.app.Application;

import com.android.svg.sample.drawables.SVGLoader;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SVGLoader.load(this);
    }
}
