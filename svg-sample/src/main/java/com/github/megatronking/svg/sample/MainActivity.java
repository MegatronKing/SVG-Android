package com.github.megatronking.svg.sample;

import com.github.megatronking.svg.sample.animation.AnimationSampleActivity;
import com.github.megatronking.svg.sample.extend.ExtendSampleActivity;
import com.github.megatronking.svg.sample.graphics.GraphicsSampleActivity;
import com.github.megatronking.svg.sample.matrix.MatrixSampleActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListSampleActivity {

    @Override
    protected List<SampleData> sampleData() {
        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("Icon Samples", IconSampleActivity.class.getName()));
        sampleData.add(new SampleData("Graphics Samples", GraphicsSampleActivity.class.getName()));
        sampleData.add(new SampleData("Matrix Samples", MatrixSampleActivity.class.getName()));
        sampleData.add(new SampleData("Animation Samples", AnimationSampleActivity.class.getName()));
        sampleData.add(new SampleData("Extend Samples", ExtendSampleActivity.class.getName()));
        return sampleData;
    }
}
