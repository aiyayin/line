package com.line.demo.yu

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView


/**
 * 带描边的textView
 *
 * @author zhonghua
 * @date 2019/5/8
 */
class StrokeTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatTextView(context, attrs, defStyleAttr) {
    private var mGradientColors = intArrayOf(Color.BLUE, Color.GREEN, Color.BLACK)

    fun setGradientColors(gradientColors: IntArray) {
        mGradientColors = gradientColors

    }

      var outlineTextView: TextView = TextView(context)

    init {
        init()
    }

    /**
     * init
     */
    fun init() {
        setPadding(20,0,0,0)
        val paint = outlineTextView.paint
        paint.strokeWidth = 12f // 描边宽度
        paint.style = Paint.Style.FILL_AND_STROKE
//        outlineTextView.setTextColor(Color.BLUE) // 描边颜色
        outlineTextView.gravity = gravity
        outlineTextView.typeface = Typeface.DEFAULT_BOLD
        outlineTextView.setPadding(18,2,0,0)

        val gradient =
            LinearGradient(0f, 0f, 0f, 300f, mGradientColors, null, Shader.TileMode.CLAMP)
        outlineTextView.paint.shader = gradient

    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams) {
        super.setLayoutParams(params)
        outlineTextView.layoutParams = params
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        outlineTextView.setTextSize(unit, size)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        outlineTextView.setPadding(left, top, right, bottom)
    }

//    override fun setTextColor(color: Int) {
//        super.setTextColor(color)
//        if (color == Color.BLUE) {
//            outlineTextView.setTextColor(Color.BLACK) //白色字体要用黑色描边
//        } else {
//            outlineTextView.setTextColor(Color.BLUE) //否则用白色描边
//        }
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 设置轮廓文字
        val outlineText = outlineTextView.text
        if (outlineText == null || outlineText != this.text) {
            outlineTextView.text = text
            postInvalidate()
        }
        outlineTextView.measure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        outlineTextView.layout(left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        outlineTextView.draw(canvas)
        super.onDraw(canvas)
    }
}