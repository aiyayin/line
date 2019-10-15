package line.view.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.yingfu.line.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

/**
 * Created by ying.fu.
 * Date: 2019/10/15.
 */
public class CountView extends View {
    private int countNumber = 0;

    public int getCountNumber() {
        return countNumber;
    }


    private Paint mPaint;
    private Paint mCountPaint;
    private Paint mCountBgPaint;
    private Paint mLinePaint;

    public CountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.main_color_avocado));
        mPaint.setAntiAlias(true);
        mCountPaint = new Paint();
        mCountPaint.setColor(getResources().getColor(R.color.main_color_avocado));
        mCountPaint.setAntiAlias(true);
        mCountBgPaint = new Paint();
        mCountBgPaint.setColor(Color.WHITE);
        mCountBgPaint.setAntiAlias(true);
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setAntiAlias(true);

    }

    float startX;
    float offestX;
    float endX;
    int height;
    float lineWidth;
    float textStartX;
    RectF oval;
    SpringAnimation animX;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        mPaint.setStrokeWidth(height);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mCountPaint.setTextSize(height / 1.5f);
        lineWidth = height / 16f;
        mLinePaint.setStrokeWidth(lineWidth);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        oval = new RectF(0, 0, height, height);
        startX = (int) (getMeasuredWidth() / 2 - height);
        endX = startX + height * 2;
        animX = new SpringAnimation(this, DynamicAnimation.TRANSLATION_X, 0);
        animX.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
        animX.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        animX.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                Log.e("CountView>>>", "onAnimationUpdate: value: " + value + "\nvelocity: " + velocity);
                offestX = value;
                postInvalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawLine(startX, getMeasuredHeight() / 2, endX, getMeasuredHeight() / 2, mPaint);
        int lineWidth = 30;
        canvas.drawLine(startX - lineWidth, height / 2f, startX + lineWidth, height / 2f, mLinePaint);
        canvas.drawLine(startX + 2f * height - lineWidth, height / 2f, startX + 2f * height + lineWidth, height / 2f, mLinePaint);
        canvas.drawLine(startX + 2f * height, height / 2 - lineWidth, startX + 2f * height, height / 2 + lineWidth, mLinePaint);
        oval.left = startX + offestX + getMeasuredHeight() * 0.5f;
        oval.right = startX + offestX + getMeasuredHeight() * 1.5F;
        canvas.drawOval(oval, mCountBgPaint);
        textStartX = (height - mCountPaint.measureText(String.valueOf(countNumber))) / 2;
        canvas.drawText(String.valueOf(countNumber), startX + offestX + getMeasuredHeight() / 2f + textStartX, height - textStartX, mCountPaint);
        canvas.restore();
    }

    @IntDef({OUT, LEFT, MIDDLE, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TouchRect {
    }

    private static final int LEFT = 1;
    private static final int MIDDLE = 2;
    private static final int RIGHT = 3;
    private static final int OUT = 0;

    private boolean lastActionIsDown;
    private float downX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                animX.cancel();
                float x = event.getX();
                downX = x;
                if (getTouchRect(x) == LEFT || getTouchRect(x) == RIGHT) {
                    lastActionIsDown = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX() - downX;
                if (Math.abs(moveX) >= 1) {
                    lastActionIsDown = false;
                    offestX = Math.abs(moveX) > height ? moveX > 0 ? height : -height : moveX;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX() - downX;
                if (event.getX() < startX + 0.5f * height) {
                    if (Math.abs(upX) <= 1) {
                        if (lastActionIsDown) {
                            addCountNumber(false);
                            lastActionIsDown = false;
                        }
                    } else {
                        addCountNumber(false);
                        startViewAnimation();
                    }
                } else if (event.getX() > startX + 1.5f * height) {
                    if (Math.abs(upX) < 1) {
                        if (lastActionIsDown) {
                            addCountNumber(true);
                            lastActionIsDown = false;
                        }
                    } else {
                        addCountNumber(true);
                        startViewAnimation();
                    }
                }

                break;
        }
        return true;

    }

    private void startViewAnimation() {
        animX.setStartValue(getMeasuredHeight() / 2);
        animX.start();
    }


    private void addCountNumber(boolean b) {
        countNumber = b ? countNumber + 1 : countNumber > 0 ? countNumber - 1 : countNumber;
        invalidate();
    }


    private @TouchRect
    int getTouchRect(float x) {
        @TouchRect int touchRect = OUT;
        if (x > startX - 0.5f * height && x < startX + 0.5f * height) {
            touchRect = LEFT;
        } else if (x > startX + 0.5f * height && x < startX + 1.5f * height) {
            touchRect = MIDDLE;
        } else if (x > startX + 1.5f * height && x < startX + 2.0f * height) {
            touchRect = RIGHT;
        }
        return touchRect;
    }
}
