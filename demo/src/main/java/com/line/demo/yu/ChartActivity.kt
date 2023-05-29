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

        val text = findViewById<StrokeTextView>(R.id.stroke_text)
        text.setTextSize(40f)
        text.typeface = Typeface.DEFAULT_BOLD
        text.scaleX = 1.2f
        text.scaleY = 1.2f
        text.setTextColor(Color.RED)
        val content = "描1边\nTextView"
        text.setText(content)

    }

}