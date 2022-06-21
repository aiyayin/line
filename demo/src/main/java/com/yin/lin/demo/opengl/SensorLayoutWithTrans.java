package com.yin.lin.demo.opengl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class SensorLayoutWithTrans extends FrameLayout implements SensorEventListener {
    private final SensorManager mSensorManager;
    private float[] mAccelerateValues;
    private float[] mMagneticValues;
    /**
     * 偏移量，第一次获取到的角度
     */
    private int firstDegreeX = 0;
    /**
     * 最大偏移量
     */
    private static final int MAX_OFFSET = 45;
    /**
     * 最小移动距离
     */
    private static final float MIN_MOVE_DISTANCE = 0.5f;

    private static final float mDegreeYMin = -80;
    private static final float mDegreeYMax = 80;
    private static final float mDegreeXMin = -80;
    private static final float mDegreeXMax = 80;
    private static final float MOVE_DISTANCE_X = (mDegreeYMax - mDegreeYMin) * 3f;
    private static final float MOVE_DISTANCE_Y = (mDegreeXMax - mDegreeXMin) * 3f;
    private final float[] values = new float[3];
    private final float[] R = new float[9];
    private View childView;

    /**
     * 可滑动距离总距离
     */
    private int totalScrollVertical = 300;
    private int totalScrollHorizontal = 300;


    private float lastDegreeX = 0;
    private float lastDegreeY = 0;

    private float finalScrollX = 0;
    private float finalScrollY = 0;

    private Runnable action;


    private float lastTranX = 0;
    private float lastTranY = 0;

    public SensorLayoutWithTrans(@NonNull Context context) {
        this(context, null);
    }

    public SensorLayoutWithTrans(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SensorLayoutWithTrans(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        action = new Runnable() {
            @Override
            public void run() {
//                childView.scrollTo((int) finalScrollX, (int) finalScrollY);
                childView.setTranslationX(finalScrollX);
                childView.setTranslationY(-finalScrollY);
            }
        };
    }

    static final float ALPHA = 0.2f;

    /**
     * @param input  input
     * @param output output
     * @return 过滤抖动
     */
    protected float[] lowPass(float[] input, float[] output) {
        if (output == null)
            return input;
        for (int i = 0; i < input.length; i++) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }

    private long startTime;
    private long timestamp;
    private int count;
    private float timeConstant = 0.18f;

    public float[] filter(float[] values, float[] output) {
        if (output == null)
            return values;
        // Initialize the start time.
        if (startTime == 0) {
            startTime = System.nanoTime();
        }

        timestamp = System.nanoTime();

        // Find the sample period (between updates) and convert from
        // nanoseconds to seconds. Note that the sensor delivery rates can
        // individually vary by a relatively large time frame, so we use an
        // averaging technique with the number of sensor updates to
        // determine the delivery rate.
        float dt = 1 / (count++ / ((timestamp - startTime) / 1000000000.0f));

        float alpha = timeConstant / (timeConstant + dt);

        output[0] = alpha * output[0] + (1 - alpha) * values[0];
        output[1] = alpha * output[1] + (1 - alpha) * values[1];
        output[2] = alpha * output[2] + (1 - alpha) * values[2];

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
        float degreeX = (float) Math.toDegrees(values[1]);
        // y轴的偏转角度
        float degreeY = (float) Math.toDegrees(values[2]);
        //过滤抖动
        if ((degreeX == 0 && degreeY == 0) || (Math.abs(lastDegreeY - degreeY) < MIN_MOVE_DISTANCE && Math.abs(lastDegreeX - degreeX) < MIN_MOVE_DISTANCE)) {
            return;
        }

        lastDegreeX = degreeX;
        lastDegreeY = degreeY;
        if (firstDegreeX == 0) {
            firstDegreeX = (int) Math.min(Math.abs(degreeX), MAX_OFFSET);
        }
        float scrollX = 0;
        float scrollY = 0;

        if (degreeX < mDegreeXMin || degreeX > mDegreeXMax) {
            return;
        }

        if (degreeY <= 0 && degreeY > mDegreeYMin) {
            scrollX = (degreeY / Math.abs(mDegreeYMin) * MOVE_DISTANCE_X);
            scrollX = Math.max(-totalScrollHorizontal / 2f, scrollX);
        } else if (degreeY > 0 && degreeY < mDegreeYMax) {
            scrollX = (degreeY / Math.abs(mDegreeYMax) * MOVE_DISTANCE_X);
            scrollX = Math.min(totalScrollHorizontal / 2f, scrollX);
        }

        degreeX = degreeX + firstDegreeX;
        if (degreeX <= 0 && degreeX > mDegreeXMin) {
            scrollY = (degreeX / Math.abs(mDegreeXMin) * MOVE_DISTANCE_Y);
            scrollY = Math.max(-totalScrollVertical / 2f, scrollY);
        } else if (degreeX > 0 && degreeX < mDegreeXMax) {
            scrollY = (degreeX / Math.abs(mDegreeXMax) * MOVE_DISTANCE_Y);
            scrollY = Math.min(totalScrollVertical / 2f, scrollY);
        }

        if (scrollX != 0 || scrollY != 0) {
            finalScrollX = scrollX;
            finalScrollY = scrollY;
            if (action != null) {
                childView.post(action);
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    public void unregister() {
        mSensorManager.unregisterListener(this);
        if (childView != null) {
            childView.removeCallbacks(action);
        }
    }

    public void register() {
        if (mSensorManager != null) {
            Sensor accelerateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            // 地磁场传感器
            Sensor magneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            mSensorManager.registerListener(this, accelerateSensor, SensorManager.SENSOR_DELAY_GAME);
            mSensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
            childView = getChildAt(0);
            if (childView != null) {
                lastTranX = childView.getTranslationX();
                lastTranY = childView.getTranslationY();
                Log.e("yin>> ", "lastTranX : " + lastTranX + " lastTranY : " + lastTranY);
            }
        }
    }

    public void setTotalScrollVertical(int totalScrollVertical) {
        this.totalScrollVertical = totalScrollVertical;
    }


    public void setTotalScrollHorizontal(int totalScrollHorizontal) {
        this.totalScrollHorizontal = totalScrollHorizontal;
    }

    @IntDef({DIRECTION_LEFT, DIRECTION_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    public @interface ADirection {

    }

    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = -1;
}