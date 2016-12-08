package com.github.megatronking.svg.sample;

import android.app.Application;

import com.github.megatronking.svg.sample.drawables.SVGLoader;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SVGLoader.load(this);
    }
}
