package com.example.yingfu.line;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * @author YING.FU
 *         date: 2018-05-31
 */

public class TIndexLineView extends View {

    private Paint mPaint;
    private int left;
    private int width;
    private int indexColor;

    public TIndexLineView(Context context) {
        this(context, null);
    }

    public TIndexLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TIndexLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TIndexLineView, defStyleAttr, 0);
        width = typedArray.getDimensionPixelSize(R.styleable.TIndexLineView_width, dpToPx(context, 8));
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#24c77E"));
        mPaint.setStrokeWidth(dpToPx(getContext(), 2));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getMeasuredHeight() / 2;
        Log.e("ying>>>", "move: w  " + w);
        Log.e("ying>>>", "move: width  " + width);

        canvas.drawLine(left + w, w, left + width - w, w, mPaint);
    }

    public void move(int left) {
        this.left = left;
        invalidate();
    }

    public void move(int left, int width) {
        this.left = left;
        this.width = width;
        invalidate();
    }


    public int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

}
