package com.line.demo.yu

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View

class LineChartView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val axisPaint: Paint = Paint()
    private val linePaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val pointPaint: Paint = Paint()

    private val path: Path = Path()
    private val dataPoints = intArrayOf(80, 15, 70, 99, 10, 57)
    private val labelY = "%"
    private val labelX = arrayOf("0", "1", "2", "3", "4", "5", "6")

    init {
        axisPaint.color = Color.BLACK
        axisPaint.strokeWidth = 3f

        linePaint.color = Color.BLUE
        linePaint.strokeWidth = 5f
        linePaint.strokeCap = Paint.Cap.ROUND
        linePaint.style = Paint.Style.STROKE
//        linePaint.pathEffect = CornerPathEffect(20f)

        textPaint.color = Color.BLACK
        textPaint.textSize = 30f

        pointPaint.color = Color.RED
        pointPaint.strokeWidth = 3f
        pointPaint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height
        val top = 150f
        val left = 120f
        val right = width - 100f
        val bottom = height - 50f
        val yRange = 100
        val numYTicks = 10
        val yTickSpacing = (yRange.toFloat() / numYTicks)
        val spacing = (right - left) / (dataPoints.size + 1).toFloat()
        val arrowWidth = 15f
        val textOffset = 10f


        // Draw X axis
        canvas.drawLine(left, bottom, right, bottom, axisPaint)
        // Draw Y axis
        canvas.drawLine(left, 0f, left, bottom, axisPaint)

        // Draw arrow for X axis
        canvas.drawLine(right, bottom, right - arrowWidth, bottom - arrowWidth, axisPaint)
        canvas.drawLine(right, bottom, right - arrowWidth, bottom + arrowWidth, axisPaint)

        // Draw arrow for Y axis
        canvas.drawLine(left, 0f, left - arrowWidth, arrowWidth, axisPaint)
        canvas.drawLine(left, 0f, left + arrowWidth, arrowWidth, axisPaint)

        canvas.drawText("Y轴单位", 0f, textOffset * 3, textPaint)
        canvas.drawText("X轴单位", width - 130f, height - textOffset, textPaint)

        // Draw Y axis labels and tick marks
        for (i in 1..numYTicks) {
            val value = yTickSpacing * i
            val yPos = bottom - (height - top) * value / yRange
            Log.d("yin>>", "画y坐标： yPos $yPos i： $i")

            val valueY = "${value.toInt()}${labelY}"
            val leftY = left - valueY.length * 25
            canvas.drawText(valueY, leftY, yPos + textOffset, textPaint)
//            canvas.drawLine(padding, yPos, padding + 10, yPos, axisPaint)
            canvas.drawLine(left, yPos, right, yPos, axisPaint)

        }


        for (i in dataPoints.indices) {
            val yPos = bottom - (height - top) * dataPoints[i].toFloat() / yRange
            Log.d("yin>>", "yPos $yPos")

            if (i == 0) {
                path.moveTo(left + spacing, yPos)
                // Draw X axis labels
                canvas.drawText(labelX.getOrNull(i)
                    ?: "", left - textOffset, height - textOffset, textPaint)
            } else {
                path.lineTo(left + spacing * (i + 1), yPos)

                // Draw X axis labels
                canvas.drawText(labelX.getOrNull(i)
                    ?: "", left - textOffset + spacing * i, height - textOffset, textPaint)
            }
        }

        // Draw X axis labels
        canvas.drawText(labelX.getOrNull(dataPoints.size)
            ?: "", left - textOffset + spacing * dataPoints.size, height - textOffset, textPaint)

        canvas.drawPath(path, linePaint)

        var lastYPos = 0f
        for (i in dataPoints.indices) {
            val yPos = bottom - (height - top) * dataPoints[i].toFloat() / yRange

            // Draw points
            canvas.drawCircle(left + spacing * (i + 1), yPos, 6f, pointPaint)

            // Draw tick marks on X axis
            canvas.drawLine(left + spacing * i, bottom, left + spacing * i, bottom - textOffset, axisPaint)

            // Draw each data point value


            val topText = if (yPos - lastYPos > 0 && lastYPos != 0f) {
                yPos + textOffset * 4
            } else {
                yPos - textOffset
            }
            canvas.drawText("${dataPoints[i]}${labelY}", left + spacing * (i + 1) - textOffset * 2, topText, textPaint)

            lastYPos = yPos
        }


        // Draw tick marks on X axis
        canvas.drawLine(left + spacing * dataPoints.size, bottom, left + spacing * dataPoints.size, bottom - textOffset, axisPaint)

    }
}
