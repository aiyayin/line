package line.view.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

/**
 * 悬停
 * Created by ying.fu on 2018/6/7.
 */

public class HoverLinearLayout2 extends LinearLayout implements NestedScrollingChild {
    private OverScroller mOverScroller;
    private float mLastY;
    private float mMaximumVelocity;
    private float mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private NestedScrollingChildHelper mHelper;


    public HoverLinearLayout2(Context context) {
        this(context, null);
    }

    public HoverLinearLayout2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoverLinearLayout2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScroller(context);
    }

    int height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
//        //第二次测量，对高度没有任何限制，那么测量出来的就是完全展示内容所需要的高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private void initScroller(Context context) {
        mHelper = new NestedScrollingChildHelper(this);
        mHelper.setNestedScrollingEnabled(true);
        mOverScroller = new OverScroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    private final int[] offset = new int[2]; //偏移量
    private final int[] consumed = new int[2]; //消费
    int showHeight = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int showHeight;
        obtainVelocityTracker(event);
        if (!mOverScroller.isFinished()) { //fling
            mOverScroller.abortAnimation();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getParent() instanceof MyNestedScrollParent) {
                    MyNestedScrollParent parent = (MyNestedScrollParent) getParent();
                    showHeight = height + parent.getImgHeight();
                    this.showHeight = showHeight;
                }
                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getY() - mLastY) > mTouchSlop) {
                    int dy = (int) (event.getY() - mLastY);
                    if (startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL)
                            && dispatchNestedPreScroll(0, dy, consumed, offset))
                        //如果找到了支持嵌套滑动的父类,父类进行了一系列的滑动
                    {
                        //获取滑动距离
                        int remain = dy - consumed[1];
                        scrollBy(0, -remain);
                    } else {
                        scrollBy(0, -dy);
                    }
                    mLastY = event.getY();
                }

                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                if (startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL)
                        && dispatchNestedPreFling(0, event.getY())) {
                    if (getMeasuredHeight() - getScrollY() > this.showHeight) {
                        mOverScroller.fling(0, getScrollY(), 0, -(int) mVelocityTracker.getYVelocity(),
                                0, 0, 0, getMeasuredHeight() - this.showHeight);
                    }
                }
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
            int dy = mOverScroller.getCurrY();
            if (startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL)
                    && dispatchNestedPreScroll(0, dy, consumed, offset)) //如果找到了支持嵌套滑动的父类,父类进行了一系列的滑动
            {
                //获取滑动距离
                int remain = dy - consumed[1];
                if (remain != 0) {
                    scrollTo(0, remain);
                }

            } else {
                scrollTo(0, dy);
            }
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


    //限制滚动范围
    @Override
    public void scrollTo(int x, int y) {
        int maxY = getMeasuredHeight() - showHeight;
        if (y > maxY) {
            y = maxY;
        }
        if (y < 0) {
            y = 0;
        }
        super.scrollTo(x, y);
    }

    //实现一下接口
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mHelper.dispatchNestedPreFling(velocityX, velocityY);
    }
}
