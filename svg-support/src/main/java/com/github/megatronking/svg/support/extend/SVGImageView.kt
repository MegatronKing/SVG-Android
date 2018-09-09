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

package com.github.megatronking.svg.support.extend

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView

import com.github.megatronking.svg.support.R
import com.github.megatronking.svg.support.SVGDrawable

/**
 * Support width, height, alpha, tint color for svg images.<br></br>
 *
 * @author Megatron King
 * @since 2016/10/10 19:11
 */
open class SVGImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr) {

    private var mSvgColor: ColorStateList? = null
    private var mSvgAlpha: Float = 0.toFloat()
    private var mSvgWidth: Int = 0
    private var mSvgHeight: Int = 0
    private var mSvgRotation: Float = 0.toFloat()
    private var mSvgScale : Int = 0

    var svgScale: Int
        get() = mSvgScale
        set(scale) {
            this.mSvgHeight = scale
            this.mSvgWidth = scale
            this.mSvgScale = scale
            resetImageDrawable()
        }

    var svgColor: ColorStateList?
        get() = mSvgColor
        set(svgColor) {
            this.mSvgColor = svgColor
            resetImageDrawable()
        }

    var svgWidth: Int
        get() = mSvgWidth
        set(width) {
            this.mSvgWidth = width
            resetImageDrawable()
        }

    var svgHeight: Int
        get() = mSvgHeight
        set(height) {
            this.mSvgHeight = height
            resetImageDrawable()
        }

    var svgAlpha: Float
        get() = mSvgAlpha
        set(alpha) {
            this.mSvgAlpha = alpha
            resetImageDrawable()
        }

    var svgRotation: Float
        get() = mSvgRotation
        set(rotation) {
            this.mSvgRotation = rotation
            resetImageDrawable()
        }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SVGView)
        mSvgColor = a.getColorStateList(R.styleable.SVGView_svgColor)
        mSvgAlpha = a.getFloat(R.styleable.SVGView_svgAlpha, 1.0f)
        mSvgWidth = a.getDimensionPixelSize(R.styleable.SVGView_svgWidth, -1)
        mSvgHeight = a.getDimensionPixelSize(R.styleable.SVGView_svgHeight, -1)
        mSvgRotation = a.getFloat(R.styleable.SVGView_svgRotation, 0f) % 360
        a.recycle()
        resetImageDrawable()
    }

    fun setSvgColor(color: Int) {
        svgColor = ColorStateList.valueOf(color)
    }

    fun setSvgSize(width: Int, height: Int) {
        this.mSvgWidth = width
        this.mSvgHeight = height
        resetImageDrawable()
    }

    private fun resetImageDrawable() {
        val drawable = drawable
        val isNeedReset = drawable != null && (drawable.intrinsicWidth != mSvgWidth || drawable.intrinsicHeight != mSvgHeight)
        resetDrawable(drawable)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && isNeedReset) {
            super.setImageDrawable(null)
            super.setImageDrawable(drawable)
        } else {
            invalidate()
        }
    }

    private fun resetDrawable(drawable: Drawable?) {
        if (drawable != null && drawable is SVGDrawable) {
            drawable.mutate()
            drawable.setTintList(mSvgColor)
            if (mSvgAlpha > 0 && mSvgAlpha <= 1.0f) {
                drawable.alpha = (mSvgAlpha * 0xFF).toInt()
            }
            if (mSvgWidth > 0) {
                drawable.setWidth(mSvgWidth)
            }
            if (mSvgHeight > 0) {
                drawable.setHeight(mSvgHeight)
            }
            if (mSvgRotation != 0f) {
                drawable.rotation = mSvgRotation
            }
        }
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        resetImageDrawable()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        resetImageDrawable()
    }
}
