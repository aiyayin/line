package line.scroller;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

/**
 * 悬停
 * Created by ying.fu on 2018/6/7.
 */

public class HoverLinearLayout extends ViewGroup {
    private OverScroller mOverScroller;
    private float mLastY;
    private float mMaximumVelocity;
    private float mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private int mHeight;


    public HoverLinearLayout(Context context) {
        this(context, null);
    }

    public HoverLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoverLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(l, mHeight, r, mHeight + view.getMeasuredHeight());
            mHeight += view.getMeasuredHeight();
            Log.e("ying>>>", " i : " + mHeight + "  changed : " + changed);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            measureChildren(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("ying>>>", "onSizeChanged : ");
        mHeight = 0;
    }

    private void initScroller(Context context) {
        mOverScroller = new OverScroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        obtainVelocityTracker(event);
        if (!mOverScroller.isFinished()) { //fling
            mOverScroller.abortAnimation();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e("ying>>>", " ACTION_MOVE event.getRawY(): " + event.getRawY());
//                Log.e("ying>>>", "ACTION_MOVE event.getY(): " + event.getY());
//                Log.e("ying>>>", "ACTION_MOVE mTouchSlop: " + mTouchSlop);
                if (Math.abs(event.getY() - mLastY) > mTouchSlop) {
                    scrollBy(0, -(int) (event.getY() - mLastY));
                    mLastY = event.getY();
                }

//                mOverScroller.startScroll((int) event.getX(), (int) event.getY(),0, (int) (event.getY()-mLastY));
//                mOverScroller.fling((int) event.getX(), (int) event.getY(), 0, 20, 0, 300, 0, 900);
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                Log.e("ying>>>", " ACTION_UP mHeight: " + mHeight);
                Log.e("ying>>>", " ACTION_UP getScreenHeight: " + getScreenHeight());
                Log.e("ying>>>", " ACTION_UP get: " + getMeasuredHeight());
                if (mHeight > getMeasuredHeight())
                    mOverScroller.fling(0, getScrollY(), 0, -(int) mVelocityTracker.getYVelocity(), 0, 0, 0, mHeight - getMeasuredHeight());
                else
                    mOverScroller.fling(0, getScrollY(), 0, -(int) mVelocityTracker.getYVelocity(), 0, 0, 0, 0);

                invalidate();
                releaseVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        // 先判断mScroller滚动是否完成
        if (mOverScroller.computeScrollOffset()) {
            // 这里调用View的scrollTo()完成实际的滚动
            scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());
            // 必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

    private void obtainVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

}
