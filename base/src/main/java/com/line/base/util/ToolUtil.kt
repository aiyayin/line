package com.line.base.util

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import com.line.base.LineApplication

/**
 * 工具类
 * Created by ying.fu on 2018/7/5.
 */
object ToolUtil {
    @JvmStatic
    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }

    /**
     * px转dip
     *
     * @param px
     * @return
     */
    fun pxToDip(px: Int, context: Context): Int {
        val dip = px / context.resources.displayMetrics.density + 0.5f
        return dip.toInt()
    }

    /**
     * 获取屏幕宽度
     */
    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        val dip = context.resources.displayMetrics.widthPixels.toFloat()
        return dip.toInt()
    }

    @JvmStatic
    fun toast(msg: String?) {
        Toast.makeText(LineApplication.context, msg, Toast.LENGTH_SHORT).show()
    }

    fun toastLone(msg: String?) {
        Toast.makeText(LineApplication.context, msg, Toast.LENGTH_LONG).show()
    }
}