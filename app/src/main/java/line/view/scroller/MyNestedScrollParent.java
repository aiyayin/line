package line.view.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;

/**
 * Created by wangjitao on 2017/2/14 0014.
 * E-Mail：543441727@qq.com
 * 嵌套滑动机制父View
 */

public class MyNestedScrollParent extends LinearLayout implements NestedScrollingParent {
    private ImageView img;
    private TextView tv;
    private HoverLinearLayout2 myNestedScrollChild;
    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    private int imgHeight;
    private int tvHeight;
//    private OverScroller mOverScroller;
//    private float mMaximumVelocity;
//    private float mTouchSlop;
//    private VelocityTracker mVelocityTracker;

    public MyNestedScrollParent(Context context) {
        super(context);
    }

    public MyNestedScrollParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
//        initScroller(context);
    }

    private void init() {
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }


//    private void initScroller(Context context) {
//        mOverScroller = new OverScroller(context);
////        mVelocityTracker = VelocityTracker.obtain();
////        final ViewConfiguration configuration = ViewConfiguration.get(context);
////        mTouchSlop = configuration.getScaledTouchSlop();
////        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
//    }

    //获取子view
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        img = (ImageView) getChildAt(0);
        tv = (TextView) getChildAt(1);
        myNestedScrollChild = (HoverLinearLayout2) getChildAt(2);
        img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (imgHeight <= 0) {
                    imgHeight = img.getMeasuredHeight();
                }
            }
        });
        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (tvHeight <= 0) {
                    tvHeight = tv.getMeasuredHeight();
                }
            }
        });
    }

    //在此可以判断参数target是哪一个子view以及滚动的方向，然后决定是否要配合其进行嵌套滚动
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (target instanceof HoverLinearLayout2) {
            return true;
        }
        return false;
    }


    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mNestedScrollingParentHelper.onStopNestedScroll(target);
    }

    //先于child滚动
    //前3个为输入参数，最后一个是输出参数
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (showImg(dy) || hideImg(dy)) {//如果需要显示或隐藏图片，即需要自己(parent)滚动
            scrollBy(0, -dy);//滚动
            consumed[1] = dy;//告诉child我消费了多少
        }
    }

    //后于child滚动
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }


    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (target instanceof HoverLinearLayout2) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        if (target instanceof HoverLinearLayout2) {
            return true;
        }
        return false;
    }
//
//    @Override
//    public void computeScroll() {
//        // 先判断mScroller滚动是否完成
//        if (mOverScroller.computeScrollOffset()) {
//            // 这里调用View的scrollTo()完成实际的滚动
////            scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());
//            int dy = mOverScroller.getCurrY();
//            scrollTo(0, dy);
//            // 必须调用该方法，否则不一定能看到滚动效果
//            postInvalidate();
//        }
//        super.computeScroll();
//    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    //下拉的时候是否要向下滚动以显示图片
    public boolean showImg(int dy) {
        if (dy > 0) {
            if (getScrollY() > 0 && myNestedScrollChild.getScrollY() == 0) {
                return true;
            }
        }

        return false;
    }

    //上拉的时候，是否要向上滚动，隐藏图片
    public boolean hideImg(int dy) {
        if (dy < 0) {
            if (getScrollY() < imgHeight) {
                return true;
            }
        }
        return false;
    }

    //scrollBy内部会调用scrollTo
    //限制滚动范围
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > imgHeight) {
            y = imgHeight;
        }

        super.scrollTo(x, y);
    }

    public int getImgHeight() {
        return imgHeight;
    }

}
