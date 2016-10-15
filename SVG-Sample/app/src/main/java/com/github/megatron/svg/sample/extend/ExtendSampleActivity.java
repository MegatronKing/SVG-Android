package com.github.megatron.svg.sample.extend;

import com.github.megatron.svg.sample.ListSampleActivity;
import com.github.megatron.svg.sample.SampleData;

import java.util.ArrayList;
import java.util.List;

public class ExtendSampleActivity extends ListSampleActivity {

    @Override
    protected List<SampleData> sampleData() {
        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("SVGView", ExtendViewSampleActivity.class.getName()));
        sampleData.add(new SampleData("SVGImageView", ExtendImageViewSampleActivity.class.getName()));
        sampleData.add(new SampleData("SVGTextView", ExtendTextViewSampleActivity.class.getName()));
        sampleData.add(new SampleData("SVGEditText", ExtendEditTextSampleActivity.class.getName()));
        sampleData.add(new SampleData("SVGButton", ExtendButtonSampleActivity.class.getName()));
        sampleData.add(new SampleData("SVGImageButton", ExtendImageButtonSampleActivity.class.getName()));
        return sampleData;
    }
}
