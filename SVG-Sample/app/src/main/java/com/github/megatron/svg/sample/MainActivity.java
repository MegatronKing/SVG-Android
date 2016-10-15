package com.github.megatron.svg.sample;

import com.github.megatron.svg.sample.extend.ExtendSampleActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListSampleActivity {

    @Override
    protected List<SampleData> sampleData() {
        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("Size Samples", SizeSampleActivity.class.getName()));
        sampleData.add(new SampleData("Tint Samples", TintSampleActivity.class.getName()));
        sampleData.add(new SampleData("ScaleType Samples", ScaleTypeSampleActivity.class.getName()));
        sampleData.add(new SampleData("Alpha Samples", AlphaSampleActivity.class.getName()));
        sampleData.add(new SampleData("Animation Samples", AnimationDrawableSampleActivity.class.getName()));
        sampleData.add(new SampleData("Extend Samples", ExtendSampleActivity.class.getName()));
        return sampleData;
    }
}
