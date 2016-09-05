package com.android.svg.sample;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Megatron King on 2016/9/5.
 */
public class TestImageView extends ImageView {

    private long decodeCost;

    public TestImageView(Context context) {
        super(context);
    }

    public TestImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageResource(int resId) {
        long time = System.nanoTime();
        super.setImageResource(resId);
        decodeCost = System.nanoTime() - time;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            super.onDraw(canvas);
            return;
        }
        SharedPreferences sp = getContext().getSharedPreferences("image", Context.MODE_APPEND);
        String allTime = sp.getString("time", "");
        long time = System.nanoTime();
        super.onDraw(canvas);
        Log.i("test", "cost " + (System.nanoTime() - time));
        long cost = System.nanoTime() - time + decodeCost;
        if (allTime.isEmpty()) {
            allTime += cost;
        } else {
            allTime += (";" + cost);
        }
        sp.edit().putString("time", allTime).commit();
    }
}
