package com.android.svg.sample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(R.drawable.ic_android_red);

        SharedPreferences sp = getSharedPreferences("image", Context.MODE_APPEND);
        String allTime = sp.getString("time", "");
        if (!allTime.isEmpty()) {
            String[] times = allTime.split(";");
            long allCost = 0;
            for (String time : times) {
                allCost += Long.parseLong(time);
            }
            ((TextView)findViewById(R.id.text)).setText(times.length + " 次，平均耗时 " + (allCost / times.length));
        }
    }
}
