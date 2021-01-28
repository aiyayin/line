package line.view.viewpager;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by YING.FU.
 * Date: 2018/8/29.
 * 触摸事件分发给 viewpager
 */

public class TFrameLayout extends LinearLayout {
    public TFrameLayout(@NonNull Context context) {
        super(context);
    }

    public TFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getChildAt(0).onTouchEvent(event);
        return true;
    }
}
