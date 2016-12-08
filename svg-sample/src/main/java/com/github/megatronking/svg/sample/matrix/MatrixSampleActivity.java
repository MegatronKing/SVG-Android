package com.github.megatronking.svg.sample.matrix;


import com.github.megatronking.svg.sample.ListSampleActivity;
import com.github.megatronking.svg.sample.SampleData;

import java.util.ArrayList;
import java.util.List;

public class MatrixSampleActivity extends ListSampleActivity {

    @Override
    protected List<SampleData> sampleData() {
        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("Translation", TranslationSampleActivity.class.getName()));
        sampleData.add(new SampleData("Rotation", RotationSampleActivity.class.getName()));
        sampleData.add(new SampleData("Scale", ScaleSampleActivity.class.getName()));
        return sampleData;
    }
}
