package com.github.megatronking.svg.sample.extend;

import com.github.megatronking.svg.sample.ListSampleActivity;
import com.github.megatronking.svg.sample.SampleData;

import java.util.ArrayList;
import java.util.List;

public class ExtendSampleActivity extends ListSampleActivity {

    @Override
    protected List<SampleData> sampleData() {
        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("SVGView", SVGViewSampleActivity.class.getName()));
        sampleData.add(new SampleData("SVGImageView", SVGImageViewSampleActivity.class.getName()));
        sampleData.add(new SampleData("SVGTextView", SVGTextViewSampleActivity.class.getName()));
        sampleData.add(new SampleData("SVGEditText", SVGEditTextSampleActivity.class.getName()));
        sampleData.add(new SampleData("SVGButton", SVGButtonSampleActivity.class.getName()));
        sampleData.add(new SampleData("SVGImageButton", SVGImageButtonSampleActivity.class.getName()));
        return sampleData;
    }
}
