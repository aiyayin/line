package com.line.demo.yu.segment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class LightingAnimationView extends View {

    private final Paint mPaint = new Paint();
    private final Path mPath = new Path();
    private ValueAnimator mValueAnimator = null;
    private int mRadius = -1;
    private final Path mClipPath = new Path();
    private final RectF mRect = new RectF();
    private int[] colors = {0x00FFFFFF, 0xAAFFFFFF, 0xAAFFFFFF, 0x00FFFFFF};
    private float[] positions = {0, 0.4f, 0.5f, 1};
    private int playMode = 1;
    private int mDuration = 1600;
    private int mRepeatCount = -1;
    private float mk = 0.45f;
    private int mw = -1;


    public LightingAnimationView(Context context) {
        this(context, null);
    }

    public LightingAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LightingAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void startLightingAnimation() {
        showAnimation(getWidth(), getHeight(), mRepeatCount, mDuration);
    }

    public void startLightingAnimation(long duration) {
        showAnimation(getWidth(), getHeight(), mRepeatCount, duration);
    }

    public void startLightingAnimation(long duration, int repeatCount) {
        showAnimation(getWidth(), getHeight(), repeatCount, duration);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (playMode == 1) {
            showAnimation(widthSize, heightSize, mRepeatCount, mDuration);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mClipPath.reset();
        if (mRadius < 0) {
            mRadius = getHeight() / 2;
        }
        mRect.set(0f, 0f, getWidth(), getHeight());
        mClipPath.addRoundRect(mRect, mRadius, mRadius, Path.Direction.CW);
        canvas.clipPath(mClipPath);
        canvas.drawPath(mPath, mPaint);
    }

    private void showAnimation(int w, int h, int repeatCount, long duration) {
        mPath.moveTo(0f, 0f);
        mPath.lineTo(w, 0f);
        mPath.lineTo(w, h);
        mPath.lineTo(0f, h);
        mPath.close();
        // 斜率k
//        float k = 1f * h / w;
        float k = mk;
        if (mw < 0) {
            mw = w / 3;
        }
        // 偏移
        float offset = mw;
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
        mValueAnimator = ValueAnimator.ofFloat(0f - offset * 2, w + offset * 2);
        mValueAnimator.setRepeatCount(repeatCount);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(duration);
        mValueAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            LinearGradient mLinearGradient = new LinearGradient(
                    value,
                    k * value,
                    value + offset,
                    k * (value + offset),
                    colors,
                    positions,
                    Shader.TileMode.CLAMP
            );
            mPaint.setShader(mLinearGradient);
            invalidate();
        });
        mValueAnimator.start();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
        mValueAnimator = null;
    }

    public void setColorAndPositions(int[] colors, float[] positions) {
        if (colors.length != positions.length) {
            throw new RuntimeException("colors and positions 数组大小必须一致");
        }
        this.colors = colors;
        this.positions = positions;
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int mRadius) {
        this.mRadius = mRadius;
    }

    public float getMk() {
        return mk;
    }

    public void setMk(float mk) {
        this.mk = mk;
    }

    public int getMw() {
        return mw;
    }

    public void setMw(int mw) {
        this.mw = mw;
    }
}
