package line.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;

import line.LineApplication;

/**
 * 工具类
 * Created by ying.fu on 2018/7/5.
 */

public class ToolUtil {
    public static int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }


    /**
     * px转dip
     *
     * @param px
     * @return
     */
    public static int pxToDip(int px, Context context) {
        float dip = px / context.getResources().getDisplayMetrics().density + 0.5f;
        return (int) dip;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        float dip = context.getResources().getDisplayMetrics().widthPixels;
        return (int) dip;
    }


    public static void toast(String msg) {
        Toast.makeText(LineApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastLone(String msg) {
        Toast.makeText(LineApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
