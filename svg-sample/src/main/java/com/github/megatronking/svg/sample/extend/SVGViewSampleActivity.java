package com.github.megatronking.svg.sample.extend;

import android.content.Intent;

import com.github.megatronking.svg.sample.ListSampleActivity;
import com.github.megatronking.svg.sample.SampleData;

import java.util.ArrayList;
import java.util.List;

public class SVGViewSampleActivity extends ListSampleActivity {

    @Override
    protected List<SampleData> sampleData() {
        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("Color", SVGViewColorSampleActivity.class.getName()));
        sampleData.add(new SampleData("Alpha", SVGViewAlphaSampleActivity.class.getName()));
        sampleData.add(new SampleData("Rotation", SVGViewRotationSampleActivity.class.getName()));
        return sampleData;
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra("title", getTitle() + " " + intent.getStringExtra("title"));
        super.startActivity(intent);
    }

}
