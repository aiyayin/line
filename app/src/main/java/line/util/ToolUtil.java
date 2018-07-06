package line.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * 工具类
 * Created by ying.fu on 2018/7/5.
 */

public class ToolUtil {
    public static int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
