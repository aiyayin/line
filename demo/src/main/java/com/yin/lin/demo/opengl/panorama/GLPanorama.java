package com.yin.lin.demo.opengl.panorama;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yin.lin.demo.R;

public class GLPanorama extends RelativeLayout implements SensorEventListener {
    private Context mContext;
    private IViews mGlSurfaceView;
    private ImageView img;
    private float mPreviousY;
    private float mPreviousYs;
    private float mPreviousX;
    private float mPreviousXs;
    private float predegrees = 0.0F;
    private Ball mBall;
    private SensorManager mSensorManager;
    private Sensor mGyroscopeSensor;
    private static final float NS2S = 1.0E-9F;
    private float timestamp;
    private float[] angle = new float[3];
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 101:
                    GLPanorama.Sensordt info = (GLPanorama.Sensordt) msg.obj;
                    float y = info.getSensorY();
                    float x = info.getSensorX();
                    float dy = y - GLPanorama.this.mPreviousY;
                    float dx = x - GLPanorama.this.mPreviousX;
                    Ball var10000 = GLPanorama.this.mBall;
                    var10000.yAngle += dx * 2.0F;
                    var10000 = GLPanorama.this.mBall;
                    var10000.xAngle += dy * 0.5F;
                    if (GLPanorama.this.mBall.xAngle < -50.0F) {
                        GLPanorama.this.mBall.xAngle = -50.0F;
                    } else if (GLPanorama.this.mBall.xAngle > 50.0F) {
                        GLPanorama.this.mBall.xAngle = 50.0F;
                    }

                    GLPanorama.this.mPreviousY = y;
                    GLPanorama.this.mPreviousX = x;
                    GLPanorama.this.rotate();
                default:
            }
        }
    };
    private Handler mHandlers = new Handler();
    int yy = 0;

    public GLPanorama(Context context) {
        super(context);
        this.mContext = context;
        this.init();
    }

    public GLPanorama(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.init();
    }

    public GLPanorama(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.init();
    }

    private void init() {
        this.initView();
    }

    private void initView() {
        LayoutInflater.from(this.mContext).inflate(R.layout.panoramalayout, this);
        this.mGlSurfaceView = (IViews) this.findViewById(R.id.mIViews);
        this.img = (ImageView) this.findViewById(R.id.img);
        this.img.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GLPanorama.this.zero();
            }
        });
    }

    private void initSensor() {
        this.mSensorManager = (SensorManager) this.mContext.getSystemService("sensor");
        this.mGyroscopeSensor = this.mSensorManager.getDefaultSensor(4);
        this.mSensorManager.registerListener(this, this.mGyroscopeSensor, 0);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == 4) {
            if (this.timestamp != 0.0F) {
                float dT = ((float) sensorEvent.timestamp - this.timestamp) * 1.0E-9F;
                this.angle[0] += sensorEvent.values[0] * dT;
                this.angle[1] += sensorEvent.values[1] * dT;
                this.angle[2] += sensorEvent.values[2] * dT;
                float anglex = (float) Math.toDegrees((double) this.angle[0]);
                float angley = (float) Math.toDegrees((double) this.angle[1]);
                float anglez = (float) Math.toDegrees((double) this.angle[2]);
                GLPanorama.Sensordt info = new GLPanorama.Sensordt();
                info.setSensorX(angley);
                info.setSensorY(anglex);
                info.setSensorZ(anglez);
                Message msg = new Message();
                msg.what = 101;
                msg.obj = info;
                this.mHandler.sendMessage(msg);
            }

            this.timestamp = (float) sensorEvent.timestamp;
        }

    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.mSensorManager.unregisterListener(this);
        float y = event.getY();
        float x = event.getX();
        switch (event.getAction()) {
            case 1:
                this.mSensorManager.registerListener(this, this.mGyroscopeSensor, 0);
                break;
            case 2:
                float dy = y - this.mPreviousYs;
                float dx = x - this.mPreviousXs;
                this.mBall.yAngle += dx * 0.3F;
                this.mBall.xAngle += dy * 0.3F;
                if (this.mBall.xAngle < -50.0F) {
                    this.mBall.xAngle = -50.0F;
                } else if (this.mBall.xAngle > 50.0F) {
                    this.mBall.xAngle = 50.0F;
                }

                this.rotate();
        }

        this.mPreviousYs = y;
        this.mPreviousXs = x;
        return true;
    }

    public void setGLPanorama(int pimgid) {
        this.mGlSurfaceView.setEGLContextClientVersion(2);
        this.mBall = new Ball(this.mContext, pimgid);
        this.mGlSurfaceView.setRenderer(this.mBall);
        setInitBallAngle();
//        this.initSensor();
    }

    private void setInitBallAngle() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0F, 360.0F);
        valueAnimator.addUpdateListener(animation -> {
            Log.e("ying>>", "onAnimationUpdate: " + animation.getAnimatedValue());
            mBall.yAngle = (float) animation.getAnimatedValue();
//                mBall.yAngle = (float) animation.getAnimatedValue();
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initSensor();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }

    private void rotate() {
        RotateAnimation anim = new RotateAnimation(this.predegrees, -this.mBall.yAngle, 1, 0.5F, 1, 0.5F);
        anim.setDuration(200L);
        this.img.startAnimation(anim);
        this.predegrees = -this.mBall.yAngle;
    }

    private void zero() {
        this.yy = (int) ((this.mBall.yAngle - 90.0F) / 10.0F);
        this.mHandlers.post(new Runnable() {
            public void run() {
                if (GLPanorama.this.yy != 0) {
                    if (GLPanorama.this.yy > 0) {
                        GLPanorama.this.mBall.yAngle -= 10.0F;
                        GLPanorama.this.mHandlers.postDelayed(this, 16L);
                        --GLPanorama.this.yy;
                    }

                    if (GLPanorama.this.yy < 0) {
                        GLPanorama.this.mBall.yAngle += 10.0F;
                        GLPanorama.this.mHandlers.postDelayed(this, 16L);
                        ++GLPanorama.this.yy;
                    }
                } else {
                    GLPanorama.this.mBall.yAngle = 90.0F;
                }

                GLPanorama.this.mBall.xAngle = 0.0F;
            }
        });
    }

    class Sensordt {
        float sensorX;
        float sensorY;
        float sensorZ;

        Sensordt() {
        }

        float getSensorX() {
            return this.sensorX;
        }

        void setSensorX(float sensorX) {
            this.sensorX = sensorX;
        }

        float getSensorY() {
            return this.sensorY;
        }

        void setSensorY(float sensorY) {
            this.sensorY = sensorY;
        }

        float getSensorZ() {
            return this.sensorZ;
        }

        void setSensorZ(float sensorZ) {
            this.sensorZ = sensorZ;
        }
    }
}
