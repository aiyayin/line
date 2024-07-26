package com.line.demo.yu

import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.line.base.BaseActivity
import com.yin.lin.demo.R
import com.yin.lin.demo.databinding.ActivityChartBinding
import kotlin.math.roundToInt


class ChartActivity : BaseActivity() {

    lateinit var binding: ActivityChartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindChart()
        bindStrokeText()
        bindText()
        PathUtil.instance.parse(this)

    }

    private val originalRect = Rect()
    private var tempLocation = IntArray(2)

    private fun bindText() {
        val text2 = findViewById<TextView>(R.id.text_2)

//        try {
//            //设备兼容性问题
//            TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(text2,
//                intArrayOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18),
//                TypedValue.COMPLEX_UNIT_SP)
//        } catch (e: Exception) {
//
//        }

        text2.setText("测试一下字体=自身缩放")

        val text3 = findViewById<TextView>(R.id.text_3)

        try {
            //设备兼容性问题
            TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(text3,
                intArrayOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18),
                TypedValue.COMPLEX_UNIT_SP)
        } catch (e: Exception) {

        }

        text3.setText("测试一下字体=自身缩放=无scaleX=测测测测测=")

        val text4 = findViewById<TextView>(R.id.text_4)
        try {
            //设备兼容性问题
            TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(text4,
                intArrayOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18),
                TypedValue.COMPLEX_UNIT_SP)
        } catch (e: Exception) {

        }
        text4.setText("测试一下字体=自身缩放=有scaleX=测测测测测=")

        text4.setBackgroundDrawable(RoundedDrawable.fromBitmapDrawable(this,
            ContextCompat.getDrawable(this,R.drawable.ic_cover_close) as BitmapDrawable?))

        text4?.postDelayed({
            Log.d("drag",
                "handleDrag:  getDrawingRect111 left : ${text4.left}  top : ${text4.top}  right : ${text4.right}  bottom : ${text4.bottom}  ")

            text4.scaleX = 1.5f
            text4.scaleY = 1.5f

            text4.getDrawingRect(originalRect)
            Log.d("drag",
                "handleDrag:  getDrawingRect222 left : ${originalRect.left}  top : ${originalRect.top}  right : ${originalRect.right}  bottom : ${originalRect.bottom}  ")
            text4.getGlobalVisibleRect(originalRect)
            Log.d("drag",
                "handleDrag: getLocalVisibleRect222  left : ${originalRect.left}  top : ${originalRect.top}  right : ${originalRect.right}  bottom : ${originalRect.bottom}  ")

//            text4.translationX = 30f

            text4.getLocationOnScreen(tempLocation)
            Log.d("drag",
                "handleDrag: getLocationOnScreen333  left : ${tempLocation[0]}  top : ${tempLocation[1]}  right : ${tempLocation[0] + (text4.measuredWidth * text4.scaleX).toInt()} ")


            val view = text4
            // 获取视图的缩放因子
            val scaleX = view.scaleX
            val scaleY = view.scaleY


            val scaledLeft = (view.left / scaleX).toInt()
            val scaledTop = (view.top / scaleY).toInt()
            val scaledRight = scaledLeft + view.measuredWidth * scaleX
            val scaledBottom = scaledTop + view.measuredHeight * scaleY

            Log.d("drag",
                "handleDrag: getLocalVisibleRect444  scaledLeft : $scaledLeft  scaledTop : $scaledTop  scaledRight : $scaledRight scaledBottom : $scaledBottom ")
            val tttop = view.top
            val llleft = view.left - scaledLeft+5
            view.layout(llleft,
                tttop, llleft +view.measuredWidth, tttop +view.measuredHeight)

        }, 3000)

    }

    private fun bindChart() {
        binding.chart.apply {
            pointList = mutableListOf(0f, 10f, 40.6f, 81f, 0f, 0f, 6f)
            labelX = mutableListOf("5.8", "5.9", "5.10", "5.11", "5.12", "5.13", "5.14")
        }
    }

    private fun bindStrokeText() {
        binding.strokeText.apply {
            setTextColor(Color.BLUE)
            val size = 56
            setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                size.toFloat()
            )
            setPadding(20, 20, 20, 20)
//        text.setLineSpacing(-20f, 0.6f)
//        text.setLetterSpacing(2f)
            val content = "文边Tex1236"
            val content2 = "=主播名字=主播名字==主播名字=主播名字==主播名字=主播名字="
            text = content2


            post {
                // 获取视图宽度
                val viewWidth: Int = width
                // 计算最大可显示字符数
                val maxCharacters: Float = viewWidth / paint.measureText("我")
                // 获取文本长度
                val textLength: Int = text.length
                if (textLength > maxCharacters) {
                    // 计算缩放比例
                    val scale = maxCharacters / textLength

                    // 根据缩放比例设置字体大小
                    val scaledSize: Float = (textSize * scale).coerceAtLeast(30f)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledSize)
                }
            }
        }
    }

}