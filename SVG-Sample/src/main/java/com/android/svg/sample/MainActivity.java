package com.android.svg.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("Size Samples", SizeSampleActivity.class.getName()));
        sampleData.add(new SampleData("Tint Samples", TintSampleActivity.class.getName()));
        sampleData.add(new SampleData("ScaleType Samples", ScaleTypeSampleActivity.class.getName()));
        sampleData.add(new SampleData("Alpha Samples", AlphaSampleActivity.class.getName()));
        sampleData.add(new SampleData("Extend Samples", ExtendSampleActivity.class.getName()));

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, sampleData));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClassName(getPackageName(), sampleData.get(position).page);
                intent.putExtra("title", sampleData.get(position).title);
                startActivity(intent);
            }
        });
    }
}
