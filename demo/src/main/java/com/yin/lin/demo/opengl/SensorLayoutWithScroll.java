package com.yin.lin.demo.opengl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class SensorLayoutWithScroll extends FrameLayout implements SensorEventListener {
    private final SensorManager mSensorManager;
    private float[] mAccelerateValues;
    private float[] mMagneticValues;
    /**
     * 偏移量，第一次获取到的角度
     */
    private double firstDegreeX = 0;
    /**
     * 最大偏移量
     */
    private static final double MAX_OFFSET = 30;
    /**
     * 最小移动距离
     */
    private static final double MIN_MOVE_DISTANCE = 2;
    private static final double MIN_MOVE_DISTANCE_X = 2;

    private static final double mDegreeYMin = -50;
    private static final double mDegreeYMax = 50;
    private static final double mDegreeXMin = -90;
    private static final double mDegreeXMax = 90;
    private static final double MOVE_DISTANCE_X = (mDegreeYMax - mDegreeYMin) * 5d;
    private static final double MOVE_DISTANCE_Y = (mDegreeXMax - mDegreeXMin) * 5d;
    private int mDirection = 1;
    private final float[] values = new float[3];
    private final float[] R = new float[9];
    private View childView;

    /**
     * 可滑动距离总距离
     */
    private double totalScrollVertical = 400;
    private double totalScrollHorizontal = 200;


    private double lastScrollX = 0;
    private double lastScrollY = 0;
    private final Scroller mScroller;

    public SensorLayoutWithScroll(@NonNull Context context) {
        this(context, null);
    }

    public SensorLayoutWithScroll(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SensorLayoutWithScroll(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mScroller = new Scroller(context);
    }

    static final float ALPHA = 0.25f;

    protected float[] lowPass(float[] input, float[] output) {
        if (output == null)
            return input;
        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (childView == null) {
            childView = getChildAt(0);
        }
        if (childView == null)
            return;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAccelerateValues = lowPass(event.values.clone(), mAccelerateValues);
            mMagneticValues = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mMagneticValues = lowPass(event.values.clone(), mMagneticValues);
        }

        if (mMagneticValues != null && mAccelerateValues != null)
            SensorManager.getRotationMatrix(R, null, mAccelerateValues, mMagneticValues);
        SensorManager.getOrientation(R, values);
        // x轴的偏转角度
        double degreeX = (float) Math.toDegrees(values[1]);
        // y轴的偏转角度
        double degreeY = (float) Math.toDegrees(values[2]);

        if (degreeX == 0 && degreeY == 0) {
            return;
        }
        if (firstDegreeX == 0) {
            firstDegreeX = Math.min(Math.abs(degreeX), MAX_OFFSET);
        }
        int scrollX = mScroller.getFinalX();
        int scrollY = mScroller.getFinalY();
        if (degreeY <= 0 && degreeY > mDegreeYMin) {
            scrollX = (int) (degreeY / Math.abs(mDegreeYMin) * MOVE_DISTANCE_X * mDirection);
            scrollX = (int) Math.max(-totalScrollHorizontal / 2, scrollX);
        } else if (degreeY > 0 && degreeY < mDegreeYMax) {
            scrollX = (int) (degreeY / Math.abs(mDegreeYMax) * MOVE_DISTANCE_X * mDirection);
            scrollX = (int) Math.min(totalScrollHorizontal / 2, scrollX);
        }

        degreeX = degreeX + firstDegreeX;
        if (degreeX <= 0 && degreeX > mDegreeXMin) {
            scrollY = (int) (degreeX / Math.abs(mDegreeXMin) * MOVE_DISTANCE_Y * mDirection);
            scrollY = (int) Math.max(-totalScrollVertical / 2, scrollY);
        } else if (degreeX > 0 && degreeX < mDegreeXMax) {
            scrollY = (int) (degreeX / Math.abs(mDegreeXMax) * MOVE_DISTANCE_Y * mDirection);
            scrollY = (int) Math.min(totalScrollVertical / 2, scrollY);

        }
//        if (scrollX != 0 && Math.abs(lastScrollX - scrollX) > MIN_MOVE_DISTANCE_X) {
//            childView.setTranslationX(scrollX);
//            lastScrollX = scrollX;
//        }
//
//        if (scrollY != 0 && Math.abs(lastScrollY - scrollY) > MIN_MOVE_DISTANCE) {
//            childView.setTranslationY(-scrollY);
//            lastScrollY = scrollY;
//        }
//        if (scrollX != 0 && Math.abs(lastScrollX - scrollX) > MIN_MOVE_DISTANCE_X && scrollY != 0 && Math.abs(lastScrollY - scrollY) > MIN_MOVE_DISTANCE) {
            smoothScroll((int) scrollX, (int) scrollY);
//            lastScrollY = scrollY;
//            lastScrollX = scrollX;

//        }
    }

    public void smoothScroll(int destX, int destY) {
        int scrollY = getScrollY();
        int delta = destY - scrollY;
        mScroller.startScroll(destX, scrollY, 0, delta, 100);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    public void unregister() {
        mSensorManager.unregisterListener(this);
    }

    public void register() {
        if (mSensorManager != null) {
            Sensor accelerateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            // 地磁场传感器
            Sensor magneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener(this, accelerateSensor, SensorManager.SENSOR_DELAY_UI);
            mSensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI);
            childView = getChildAt(0);
        }
    }

    public void setTotalScrollVertical(double totalScrollVertical) {
        this.totalScrollVertical = totalScrollVertical;
    }


    public void setTotalScrollHorizontal(double totalScrollHorizontal) {
        this.totalScrollHorizontal = totalScrollHorizontal;
    }

    public void setDirection(@ADirection int direction) {
        mDirection = direction;
    }

    @IntDef({DIRECTION_LEFT, DIRECTION_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    public @interface ADirection {

    }

    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = -1;
}