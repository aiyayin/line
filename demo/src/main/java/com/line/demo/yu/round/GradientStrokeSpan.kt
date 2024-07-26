package com.line.demo.yu.round

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance
import android.text.TextPaint
import android.graphics.Shader
import android.text.style.ReplacementSpan
import android.graphics.Paint.FontMetricsInt

class GradientStrokeSpan() : CharacterStyle(),
    UpdateAppearance {
     var mStrokeWidth = 20f
    var mGradientColors: IntArray = intArrayOf(Color.BLUE, Color.RED)


    override fun updateDrawState(paint: TextPaint) {
        paint.setStyle(Paint.Style.STROKE)
        paint.strokeWidth = mStrokeWidth
        val gradient: LinearGradient = LinearGradient(
            0f, 0f, paint.measureText("Sample Text"), 0f,
            mGradientColors, null, Shader.TileMode.CLAMP
        )
        paint.shader = gradient
    }
}