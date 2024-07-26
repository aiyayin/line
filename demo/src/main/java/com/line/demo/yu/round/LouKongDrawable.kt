package com.line.demo.yu.round

import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.annotation.NonNull


/**
 *
 * @description 中间镂空背景
 * @author fuyi
 * @date 2023/9/7
 */
class LouKongDrawable() : ColorDrawable(Color.parseColor("#80123456")) {
    val srcPaint = Paint()
    val srcPath = Path()

    init {
        srcPath.addRoundRect(10f, 10f, 400f, 100f, 10f, 10f, Path.Direction.CW)

    }

    override fun draw(@NonNull canvas: Canvas) {
        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        val saveCount = canvas.saveLayer(0f,
            0f,
            canvas.width.toFloat(),
            canvas.height.toFloat(),
            srcPaint,
            Canvas.ALL_SAVE_FLAG)

        //dst 绘制目标图层
        super.draw(canvas)

        //设置混合模式
        srcPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        //src 绘制源图
        canvas.drawPath(srcPath, srcPaint)
        //清除混合模式
        srcPaint.setXfermode(null)
        //还原画布
        canvas.restoreToCount(saveCount)
    }


}