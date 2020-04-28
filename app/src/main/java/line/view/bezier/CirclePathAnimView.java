package line.view.bezier;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.yingfu.line.R;

import androidx.annotation.Nullable;

/**
 * Created by ying.fu.
 * Date: 2020/4/27.
 */
public class CirclePathAnimView extends View {

    private float mAnimatorValue;
    private PathMeasure mPathMeasure;
    private Path mDevPath;
    private Paint mPaint;
    private ValueAnimator mValueAnimator;
    private Bitmap airplayBitmap;

    public CirclePathAnimView(Context context) {
        this(context, null);
    }

    public CirclePathAnimView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePathAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_SOFTWARE, null);//关闭硬件加速
        //初始化画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.mtrl_calendar_month_horizontal_padding));
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));

        //飞机图片
        airplayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fly);

        //画真正显示的path
        mDevPath = new Path();


        //开始动画，当然当前动画你可以单独写成一个方法
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.setDuration(3000);
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int radius = 0;
        if (width >= height) {
            radius = height / 2 - height / 8;
        } else {
            radius = width / 2 - width / 8;
        }
        //绘制path
        //先画圆的path，但是这个圆只是用来计算
        Path circlePath = new Path();
        circlePath.addCircle(width / 2, height / 2, radius, Path.Direction.CW);


        //计算圆的path的长度
        mPathMeasure = new PathMeasure(circlePath, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float length = mPathMeasure.getLength();
        float stop = length * mAnimatorValue;
        //在0到0.5以前，起点不变，0.5到1，起点开始向终点靠拢。
        float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * length));
        Log.e("CirclePathAnimView", "length: " + length + " stop: " + stop + " start: " + start);
        mDevPath.reset();
        mPathMeasure.getSegment(start, stop, mDevPath, true);
        canvas.drawPath(mDevPath, mPaint);

        Matrix matrix = new Matrix();
        mPathMeasure.getMatrix(stop, matrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        matrix.preTranslate(-airplayBitmap.getWidth() / 2, -airplayBitmap.getHeight() / 2);

        canvas.drawBitmap(airplayBitmap, matrix, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mValueAnimator.cancel();
        mValueAnimator = null;
    }
}