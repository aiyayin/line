package com.line.view.line

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet

class ClipLeftFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : com.google.android.material.tabs.TabLayout(context, attrs, defStyleAttr) {
    private val mOffsetLeft = 200f
    private val mOffsetBottom = 30f
    private val path = Path()


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val width = w.toFloat()
        val height = h.toFloat()
        // 重置路径并重新定义裁剪区域
        path.reset();

        // 定义路径，左边上面裁剪，底部预留 10dp 不裁剪
        path.moveTo(mOffsetLeft, 0f)
        path.lineTo(width, 0f)
        path.lineTo(width, height)
        path.lineTo(0f, height)
        path.lineTo(0f, height - mOffsetBottom)
        path.lineTo(mOffsetLeft, height - mOffsetBottom)

        path.close();
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (mOffsetLeft > 0) {
            // 保存当前画布状态
            val saveCount = canvas.save()

            // 裁剪掉左侧 40dp
            canvas.clipPath(path)

            // 调用父类的方法继续绘制子 View
            super.dispatchDraw(canvas)

            // 恢复画布到之前的保存状态
            canvas.restoreToCount(saveCount)
        } else {
            super.dispatchDraw(canvas)
        }
    }
}
