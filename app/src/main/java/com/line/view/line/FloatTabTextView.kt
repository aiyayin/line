package com.line.view.line

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.douyu.lib.utils.DYDensityUtils

/**
 *
 * @description
 * @author fuyi
 * @date 2024/9/3
 */
class FloatTabTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private val horizontalRect = RectF()
    private val verticalRect = RectF()
    var indicatorColor = Color.parseColor("#123456")
        set(value) {
            field = value
            invalidate()
        }

    private val indicatorWidth = DYDensityUtils.dip2px(12f).toFloat()
    private val indicatorHeight = DYDensityUtils.dip2px(3f).toFloat()
    private val bottomOffset = DYDensityUtils.dip2px(3f).toFloat()

    // 圆角矩形参数
    private val cornerRadius = indicatorHeight / 2
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // 底部居中的圆角横线
        horizontalRect.left = (w - indicatorWidth) / 2  // 左边界
        horizontalRect.top = h - indicatorHeight - bottomOffset
        horizontalRect.right = (w + indicatorWidth) / 2
        horizontalRect.bottom = h.toFloat() - bottomOffset

        // 右侧竖直居中的线
        verticalRect.left = w - indicatorHeight            // 左边界
        verticalRect.top = (h - indicatorWidth) / 2      // 上边界
        verticalRect.right = w.toFloat()         // 右边界
        verticalRect.bottom = (h + indicatorWidth) / 2        // 下边界

    }

    override fun onDraw(canvas: Canvas) {
        if (isSelected) {
            paint.color = indicatorColor
            canvas.drawRoundRect(horizontalRect, cornerRadius, cornerRadius, paint)
        }
        paint.color = Color.BLUE
        canvas.drawRect(verticalRect, paint)
        super.onDraw(canvas)
    }

}