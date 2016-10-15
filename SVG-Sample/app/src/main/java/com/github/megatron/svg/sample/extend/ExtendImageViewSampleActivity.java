package com.github.megatron.svg.sample.extend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.megatron.svg.sample.R;
import com.github.megatron.svg.sample.SampleData;

import java.util.ArrayList;
import java.util.List;

public class ExtendImageViewSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle(getIntent().getStringExtra("title"));

        final List<SampleData> sampleData = new ArrayList<>();
        sampleData.add(new SampleData("Size", ExtendImageViewSizeSimpleActivity.class.getName()));
        sampleData.add(new SampleData("ImageView", ExtendImageViewSampleActivity.class.getName()));
        sampleData.add(new SampleData("TextView", ExtendTextViewSampleActivity.class.getName()));
        sampleData.add(new SampleData("EditText", ExtendEditTextSampleActivity.class.getName()));
        sampleData.add(new SampleData("Button", ExtendButtonSampleActivity.class.getName()));
        sampleData.add(new SampleData("ImageButton", ExtendImageButtonSampleActivity.class.getName()));

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
