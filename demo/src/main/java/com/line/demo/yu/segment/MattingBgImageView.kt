package com.line.demo.yu.segment

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


/**
 *
 * @description
 * @author fuyi
 * @date 2023/9/12
 */
class MattingBgImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    var mPath: Path? = null


    fun bindPath(path: Path) {
        this.mPath = path
        foreground = ColorDrawable(Color.parseColor("#99000000"))
        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        val path = mPath
        if (canvas != null && path != null) {
            val saveCount = canvas.save()
            canvas.clipPath(path, Region.Op.DIFFERENCE) // 根据路径裁剪画布
            super.onDraw(canvas)
            canvas.restoreToCount(saveCount)
        } else {
            super.onDraw(canvas)
        }
    }


}