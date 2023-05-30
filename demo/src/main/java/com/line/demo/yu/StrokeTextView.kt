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
    private var mGradientColors = intArrayOf(Color.GREEN, Color.GRAY)

    fun setStrokeGradientColors(gradientColors: IntArray) {
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
//        setPadding(20, 0, 20, 0)
        val paint = outlineTextView.paint
        paint.strokeWidth = 20f // 描边宽度
        paint.style = Paint.Style.STROKE
        outlineTextView.gravity = gravity
        outlineTextView.setTextColor(Color.RED)
        outlineTextView.typeface = Typeface.DEFAULT_BOLD
//        outlineTextView.setPadding(18, 2, 20, 0)


//        setLineSpacing(0f, 0.8f)
//        outlineTextView.setLineSpacing(0f, 0.8f)

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

    fun setStrokeColor(color: Int) {
        outlineTextView.setTextColor(color)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
//        post {
//            val fontMetrics = paint?.fontMetrics
//            val ascent = fontMetrics?.ascent ?: 0f
//            val descent = fontMetrics?.descent ?: 0f
//            val textHeight: Float = Math.abs(ascent) + Math.abs(descent)
//            val gradientH = textHeight * lineSpacingMultiplier*1.6f
//            if (textHeight != 0f) {
//                val gradient =
//                    LinearGradient(0f, 0f, 0f, gradientH, mGradientColors, null, Shader.TileMode.CLAMP)
//                outlineTextView.paint.shader = gradient
//            }
//            val typeface = Typeface.createFromAsset(context.getAssets(), "fonts/test.ttf")
//            setTypeface(typeface)
//            outlineTextView.setTypeface(typeface)
//            invalidate()
//        }
    }

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