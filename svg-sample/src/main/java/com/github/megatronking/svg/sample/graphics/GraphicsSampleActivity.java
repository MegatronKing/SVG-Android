package com.github.megatronking.svg.sample.graphics;

import com.github.megatronking.svg.sample.ListSampleActivity;
import com.github.megatronking.svg.sample.SampleData;

import java.util.ArrayList;
import java.util.List;

public class GraphicsSampleActivity extends ListSampleActivity {

    @Override
    protected List<SampleData> sampleData() {
        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("Size", SizeSampleActivity.class.getName()));
        sampleData.add(new SampleData("Tint", TintSampleActivity.class.getName()));
        sampleData.add(new SampleData("ScaleType", ScaleTypeSampleActivity.class.getName()));
        sampleData.add(new SampleData("Alpha", AlphaSampleActivity.class.getName()));
        return sampleData;
    }
}
