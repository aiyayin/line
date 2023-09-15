package com.line.demo.yu;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.customview.widget.ViewDragHelper;

/**
 * 描述：
 *
 * @author：yuanmin
 * @date: 2017/10/1 16:34
 * 邮箱：yuanmin@douyu.tv
 */

public class DragLayout extends RelativeLayout {

    private ViewDragHelper mDragger;

    private View mDragView;

    private Point initPointPosition = new Point();

    private boolean isFirst;


    private boolean mNeedHandleTouch = false;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return  top;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getHeight();
            }


            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                initPointPosition.x = left;
                initPointPosition.y = top;
            }

            @Override
            public void onViewDragStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (null == mDragView) {
            return;
        }
        if (!isFirst) {
            initPointPosition.x = 0;
            initPointPosition.y = 0;
            isFirst = true;
        }
        if (View.VISIBLE == mDragView.getVisibility()
                && (initPointPosition.x != 0 || initPointPosition.y != 0)) {
//            mDragView.layout(initPointPosition.x,initPointPosition.y,
//                    initPointPosition.x+mDragView.getMeasuredWidth(),
//                    initPointPosition.y+mDragView.getMeasuredHeight());
            LayoutParams lp = (LayoutParams) mDragView.getLayoutParams();
            if (lp.leftMargin != initPointPosition.x || lp.topMargin != initPointPosition.y - getPaddingTop()) {
                lp.addRule(ALIGN_PARENT_RIGHT, 0);
                lp.leftMargin = initPointPosition.x;
                lp.topMargin = initPointPosition.y - getPaddingTop();
                lp.rightMargin = 0;
                lp.bottomMargin = 0;
                mDragView.setLayoutParams(lp);
            }
        }
    }

    /**
     * @param view 需要拖拽的view
     */
    public void setDragTargetView(View view) {
        mDragView = view;
    }


}
