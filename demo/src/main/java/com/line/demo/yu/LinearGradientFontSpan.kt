package com.line.demo.yu

import android.graphics.*
import android.graphics.Paint.FontMetricsInt
import android.text.style.ReplacementSpan

/**
 * @ClassName: LinearGradientFontSpan
 * @Description: 文字渐变的Span类
 * @Author: ZL
 * @CreateDate: 2019/09/23 09:58
 */
class LinearGradientFontSpan : ReplacementSpan {
    // 文字宽度
    private var mSize = 0

    var mGradientColors: IntArray = intArrayOf(Color.BLUE, Color.RED)


    // 是否左右渐变
    private var isLeftToRight = true
    private var mSpacing = 10
    fun setmSpacing(mSpacing: Int) {
        this.mSpacing = mSpacing
    }

    constructor() {}

    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: FontMetricsInt?
    ): Int {
        mSize = paint.measureText(text, start, end).toInt()
        return mSize
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        // 修改y1的值从上到下渐变， 修改x1的值从左到右渐变
        val lg: LinearGradient
        if (isLeftToRight) {
            lg = LinearGradient(
                0f, 0f, 100f, 0f,
                mGradientColors,null,
                Shader.TileMode.REPEAT
            )
        } else {
            lg = LinearGradient(
                0f, 0f, 0f, 200f,
                mGradientColors,null,
                Shader.TileMode.REPEAT
            )
        }
        paint.strokeWidth = 20f
        paint.shader = lg
//        var dx = x
//        for (i in start until end) {
//            val c = text[i].toString()
//            canvas.drawText(c, dx, y.toFloat(), paint)
//            dx += paint.measureText(c) + mSpacing
//        }
        canvas.drawText(text, start, end, x, y.toFloat(), paint) //绘制文字

    }

    fun setLeftToRight(leftToRight: Boolean) {
        isLeftToRight = leftToRight
    }
}