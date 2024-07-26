package com.line.demo.yu.round;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

public class SpacingSpan extends ReplacementSpan {

    private int mSpacing;

    public SpacingSpan(int spacing) {
        mSpacing = spacing;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return (int) (paint.measureText(text, start, end) + mSpacing * (end - start - 1));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        float dx = x;
        for (int i = start; i < end; i++) {
            String c = String.valueOf(text.charAt(i));
            canvas.drawText(c, dx, y, paint);
            dx += paint.measureText(c) + mSpacing;
        }
    }
}