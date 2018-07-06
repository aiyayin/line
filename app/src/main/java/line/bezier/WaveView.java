package line.bezier;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.yingfu.line.R;

/**
 * 波浪\(^o^)/~
 * Created by ying.fu on 2018/7/5.
 */

public class WaveView extends View {
    private Paint mPaint;

    private Path mPath;
    //一个波纹长度
    private int mWaveH = 80;
    private int mWaveW = 600;
    //波纹个数
    private int mWaveCount;
    //平移偏移量
    private int offset;

    private int mCenterY;

    //屏幕高度
    private int mScreenHeight;
    //屏幕宽度
    private int mScreenWidth;

    private int indexColor;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView, defStyleAttr, 0);
        indexColor = typedArray.getColor(R.styleable.WaveView_color, context.getResources().getColor(R.color.blue));
        typedArray.recycle();
        init();
        initAnimator();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(indexColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        mPath.setFillType(Path.FillType.EVEN_ODD);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;
        mWaveCount = mScreenWidth / mWaveW + 3;
        Log.e("ying>>>", "onSizeChanged: " + mWaveCount);
        mCenterY = mScreenHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(0, mCenterY + offset);
        for (int i = 0; i < mWaveCount; i++) {
//        quadTo方法中的四个参数分别是确定第二，第三的点的。第一个点就是path上次操作的点。
            mPath.quadTo((float) ((i - 1.25) * mWaveW) + offset, mCenterY - mWaveH, (float) ((i - 1) * mWaveW) + offset, mCenterY);
            mPath.quadTo((float) ((i - 0.75) * mWaveW) + offset, mCenterY + mWaveH, (float) ((i - 0.5) * mWaveW) + offset, mCenterY);
        }
        mPath.lineTo(mScreenWidth, mScreenHeight);
        mPath.lineTo(0, mScreenHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void initAnimator() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mWaveW);
        animator.setDuration(1500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

}
