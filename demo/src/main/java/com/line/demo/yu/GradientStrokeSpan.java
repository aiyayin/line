package com.line.demo.yu;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

public class GradientStrokeSpan extends CharacterStyle implements UpdateAppearance {

    private float mStrokeWidth = 3f;
    private int[] mGradientColors;

    public GradientStrokeSpan(float strokeWidth, int[] gradientColors) {
        this.mStrokeWidth = strokeWidth;
        this.mGradientColors = gradientColors;
    }

    @Override
    public void updateDrawState(TextPaint paint) {
//        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mStrokeWidth);
        LinearGradient gradient = new LinearGradient(0, 0, paint.measureText("Sample Text"), 0,
                mGradientColors, null, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
    }



}
