package com.line.demo.yu

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import com.line.base.BaseActivity
import com.yin.lin.demo.R


class ChartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        val chat = findViewById<LineChartView>(R.id.chart)
        chat?.apply {
            pointList = mutableListOf(0f, 10f, 40.6f, 81f, 0f, 0f, 6f)
            labelX = mutableListOf("5.8", "5.9", "5.10", "5.11", "5.12", "5.13", "5.14")
        }
        val text = findViewById<StrokeTextView>(R.id.stroke_text)
        text.setTextSize(30f)
        text.typeface = Typeface.DEFAULT_BOLD
        text.scaleX = 1.2f
        text.scaleY = 1.2f
        text.setTextColor(Color.BLUE)
        val content = "文边Tex1236"
        val content2 = "主播名字\n" + "入驻斗鱼"
        text.setText(content)

    }

}