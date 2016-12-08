package com.github.megatronking.svg.sample.extend;

import android.content.Intent;

import com.github.megatronking.svg.sample.ListSampleActivity;
import com.github.megatronking.svg.sample.SampleData;

import java.util.ArrayList;
import java.util.List;

public class SVGEditTextSampleActivity extends ListSampleActivity {

    @Override
    protected List<SampleData> sampleData() {
        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("Size", SVGEditTextSizeSampleActivity.class.getName()));
        sampleData.add(new SampleData("Color", SVGEditTextColorSampleActivity.class.getName()));
        sampleData.add(new SampleData("Alpha", SVGEditTextAlphaSampleActivity.class.getName()));
        sampleData.add(new SampleData("Rotation", SVGEditTextRotationSampleActivity.class.getName()));
        return sampleData;
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra("title", getTitle() + " " + intent.getStringExtra("title"));
        super.startActivity(intent);
    }

}
