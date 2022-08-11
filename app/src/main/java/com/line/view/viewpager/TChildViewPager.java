package com.line.view.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 解决ViewPager嵌套ViewPager嵌套滑动冲突
 * Created by chao.zheng on 2019/1/16.
 */
public class TChildViewPager extends ViewPager {
    private ViewPager mParentViewPager;
    private boolean mFlag = true;
    private float mLastX;

    public TChildViewPager(Context context) {
        super(context);
    }

    public TChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPager getViewPager() {
        return mParentViewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.mParentViewPager = viewPager;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            final float x = ev.getX();
            if (mParentViewPager != null) {
//                switch (ev.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        mParentViewPager.requestDisallowInterceptTouchEvent(true);
//                        mFlag = true;
//                        mLastX = x;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (mFlag) {
//                            if (x - mLastX > 5 && getCurrentItem() == 0) {
//                                mFlag = false;
                                mParentViewPager.requestDisallowInterceptTouchEvent(true);
//                            }
//                            if (getAdapter() != null) {
//                                if (x - mLastX < -5 && getCurrentItem() == getAdapter().getCount() - 1) {
//                                    mFlag = false;
//                                    mParentViewPager.requestDisallowInterceptTouchEvent(true);
//                                }
//                            }
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        mParentViewPager.requestDisallowInterceptTouchEvent(false);
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//                        mParentViewPager.requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
            }
            return super.dispatchTouchEvent(ev);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        }catch (Exception e){
            return false;
        }
    }
}
