package com.github.megatron.svg.sample.extend;

import android.content.Intent;

import com.github.megatron.svg.sample.ListSampleActivity;
import com.github.megatron.svg.sample.SampleData;

import java.util.ArrayList;
import java.util.List;

public class SVGImageButtonSampleActivity extends ListSampleActivity {

    @Override
    protected List<SampleData> sampleData() {
        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("Size", SVGImageButtonSizeSampleActivity.class.getName()));
        sampleData.add(new SampleData("Color", SVGImageButtonColorSampleActivity.class.getName()));
        sampleData.add(new SampleData("Alpha", SVGImageButtonAlphaSampleActivity.class.getName()));
        sampleData.add(new SampleData("Rotation", SVGImageButtonRotationSampleActivity.class.getName()));
        return sampleData;
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra("title", getTitle() + " " + intent.getStringExtra("title"));
        super.startActivity(intent);
    }

}
