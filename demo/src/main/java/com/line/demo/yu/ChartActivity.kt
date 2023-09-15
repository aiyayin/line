package com.line.demo.yu

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.customview.widget.ViewDragHelper
import com.line.base.BaseActivity
import com.yin.lin.demo.R
import com.yin.lin.demo.databinding.ActivityChartBinding


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

    private fun bindText() {
        val text2 = findViewById<TextView>(R.id.text_2)

        try {
            //设备兼容性问题
            TextViewCompat.setAutoSizeTextTypeUniformWithPresetSizes(text2,
                intArrayOf(8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18),
                TypedValue.COMPLEX_UNIT_SP)
        } catch (e: Exception) {

        }

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
        text4.scaleX = 1.2f
        text4.scaleY = 1.2f

        text4.setText("测试一下字体=自身缩放=有scaleX=测测测测测=")
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
            setPadding(20, 20 - size, 20, 20 - size)
//        text.setLineSpacing(-20f, 0.6f)
//        text.setLetterSpacing(2f)
            val content = "文边Tex1236"
            val content2 = "主播名字"
            text = content2
        }
    }

}