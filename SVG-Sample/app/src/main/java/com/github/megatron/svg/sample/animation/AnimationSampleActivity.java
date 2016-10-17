package com.github.megatron.svg.sample.animation;

import com.github.megatron.svg.sample.ListSampleActivity;
import com.github.megatron.svg.sample.SampleData;

import java.util.ArrayList;
import java.util.List;

public class AnimationSampleActivity extends ListSampleActivity {

    @Override
    protected List<SampleData> sampleData() {
        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("AnimationDrawable", AnimationDrawableSampleActivity.class.getName()));
        return sampleData;
    }
}
