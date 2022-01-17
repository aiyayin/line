package line.view.bezier;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;


import androidx.annotation.Nullable;

import com.example.yingfu.line.R;

/**
 * 波浪\(^o^)/~
 * Created by ying.fu on 2018/7/5.
 */

public class WaveView extends View {
    private Paint mPaint;

    private Path mPath;
    private Path mWaveLimitPath;
    //一个波纹长度
    private int mWaveH = 80;
    private int mWaveW = 600;
    //波纹个数
    private int mWaveCount;
    //平移偏移量
    private int offset;

    private int mCenterY;
    private int mBitmapLeft;
    private int mBitmapTop;

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
        indexColor = typedArray.getColor(R.styleable.WaveView_color, context.getResources().getColor(R.color.blue_2196f3));
        typedArray.recycle();
        init();
        initAnimator();
    }

    Bitmap bitmap;
    Matrix matrix;
    Shader bitmapShader;

    private void init() {
        mPath = new Path();
        mWaveLimitPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(indexColor);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_ya);
        matrix = new Matrix();
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Shader colorShader = new LinearGradient(0, 0, mScreenWidth, mScreenHeight, indexColor, indexColor, Shader.TileMode.REPEAT);
        ComposeShader composeShader = new ComposeShader(colorShader, bitmapShader, PorterDuff.Mode.SRC_ATOP);
        mPaint.setShader(composeShader);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;
        mWaveCount = mScreenWidth / mWaveW + 3;
        mBitmapLeft = mScreenWidth / 2 - bitmap.getWidth() / 2;
        mBitmapTop = mScreenHeight / 2 - bitmap.getHeight() / 2;
        Log.e("ying>>>", "onSizeChanged: " + mWaveCount);
        mCenterY = mScreenHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWaveLimitPath.reset();
        mPath.reset();
        matrix.setTranslate(mBitmapLeft, mBitmapTop);
        bitmapShader.setLocalMatrix(matrix);
        canvas.drawBitmap(bitmap, mBitmapLeft, mBitmapTop, mPaint);
        mPath.moveTo(0, mCenterY + offset);
        for (int i = 0; i < mWaveCount; i++) {
//        quadTo方法中的四个参数分别是确定第二，第三的点的。第一个点就是path上次操作的点。
            mPath.quadTo((float) ((i - 1.25) * mWaveW) + offset, mCenterY - mWaveH, (float) ((i - 1) * mWaveW) + offset, mCenterY);
            mPath.quadTo((float) ((i - 0.75) * mWaveW) + offset, mCenterY + mWaveH, (float) ((i - 0.5) * mWaveW) + offset, mCenterY);
        }
        mPath.lineTo(mScreenWidth, mScreenHeight);
        mPath.lineTo(0, mScreenHeight);
        mPath.close();
        mWaveLimitPath.addCircle(mScreenWidth / 2, mScreenHeight / 2, Math.min(mScreenHeight, mScreenWidth) / 2, Path.Direction.CCW);
        mWaveLimitPath.op(mPath, Path.Op.INTERSECT);//取重叠部分
        canvas.drawPath(mWaveLimitPath, mPaint);

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
