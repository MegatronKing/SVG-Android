/*
 * Copyright (C) 2017, Megatron King
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.megatronking.svg.support.vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * A view shown vector paths.
 *
 * @author Megatron King
 * @since 2017/2/08 11:08
 */
public class VectorView extends View {

    public static final int INVALID_PATH_INDEX = -1;

    private static final RectF RECTF = new RectF();

    private Path[] mPaths;
    private Paint[] mPathPaints;

    private Point mClickPoint;

    private OnPathClickListener mPathClickListener;
    private OnPathTouchListener mPathTouchListener;

    public VectorView(Context context) {
        super(context);
    }

    public VectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setVector(String vector) {
        setVectorArrays(new String[]{vector});
    }

    public void setVectorArrays(List<String> vectors) {
        String[] vectorArrays = new String[vectors.size()];
        vectors.toArray(vectorArrays);
        setVectorArrays(vectorArrays);
    }

    public void setVectorArrays(String[] vectors) {
        if (vectors == null) {
            return;
        }
        mPathPaints = new Paint[vectors.length];
        mPaths = new Path[vectors.length];
        for (int i = 0; i < vectors.length; i++) {
            mPaths[i] = VectorPathParser.createPathFromPathData(vectors[i]);
            mPathPaints[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        requestLayout();
        invalidate();
    }

    public void setColor(int color) {
        if (mPathPaints != null) {
            for (Paint paint : mPathPaints) {
                paint.setColor(color);
            }
            invalidate();
        }
    }

    public void setColor(int index, int color) {
        if (mPathPaints != null && index >= 0 && index < mPathPaints.length) {
            mPathPaints[index].setColor(color);
            invalidate();
        }
    }

    public void setOnPathClickListener(OnPathClickListener pathClickListener) {
        this.mPathClickListener = pathClickListener;
        setClickable(true);
    }

    public void setOnPathTouchListener(OnPathTouchListener pathTouchListener) {
        this.mPathTouchListener = pathTouchListener;
    }

    public int findPathIndexByPoint(Point point) {
        return findPathIndexByPoint(point.x, point.y);
    }

    public int findPathIndexByPoint(int x, int y) {
        Region region = new Region();
        for (int i = 0; i < mPaths.length; i++) {
            mPaths[i].computeBounds(RECTF, true);
            region.setPath(mPaths[i], new Region((int)RECTF.left,(int)RECTF.top,(int)RECTF.right,(int)RECTF.bottom));
            if (region.contains(x, y)) {
                return i;
            }
        }
        return INVALID_PATH_INDEX;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mPaths != null && mPathTouchListener != null) {
            int index = findPathIndexByPoint((int)event.getX(), (int)event.getY());
            if (index != INVALID_PATH_INDEX) {
                mPathTouchListener.onTouch(this, mPaths[index], index, event);
            }
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mClickPoint = new Point();
                mClickPoint.x = (int)event.getX();
                mClickPoint.y = (int)event.getY() + getScrollY();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mPaths != null) {
            int left = getMeasuredWidth();
            int right = 0;
            int top = getMeasuredHeight();
            int bottom = 0;
            for (Path path: mPaths) {
                path.computeBounds(RECTF, true);
                left = Math.min(left, (int)RECTF.left);
                right = Math.max(right, (int)RECTF.right);
                top = Math.min(top, (int)RECTF.top);
                bottom = Math.max(bottom, (int)RECTF.bottom);
                setMeasuredDimension(right + left, top + bottom);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPaths != null) {
            for (int i = 0; i < mPaths.length; i++) {
                canvas.drawPath(mPaths[i], mPathPaints[i]);
            }
        }
    }

    @Override
    public boolean performClick() {
        if (mClickPoint != null && mPaths != null && mPathClickListener != null) {
            int index = findPathIndexByPoint(mClickPoint);
            if (index != INVALID_PATH_INDEX) {
                mPathClickListener.onClick(this, mPaths[index], index, mClickPoint);
            }
        }
        return true;
    }

    public interface OnPathClickListener {

        void onClick(View view, Path path, int index, Point clickPoint);

    }

    public interface OnPathTouchListener {

        void onTouch(View view, Path path, int index, MotionEvent event);

    }
}
