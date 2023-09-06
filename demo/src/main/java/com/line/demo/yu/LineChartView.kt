package com.line.demo.yu

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.line.base.dp
import com.line.base.dpFloat

/**
 * 折线图
 * @constructor fuyi
 */
class LineChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private val dashPaint: Paint = Paint()
    private val axisPaint: Paint = Paint()
    private val linePaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val pointPaint: Paint = Paint()

    private val pathEffect = DashPathEffect(floatArrayOf(4f, 6f), 0f)

    private var textOffset = 10f
    var specialZero: Boolean = true
    var yRange = 100
    var numYTicks = 4
    var pointList: List<Float>? = null
    var labelY = "%"
    var labelX: List<String>? = null

    init {

        val colorGray = Color.parseColor("#6f6f6f")
        val orange = Color.parseColor("#FF4823")

        dashPaint.color = colorGray
        dashPaint.strokeWidth = 3f
        dashPaint.style = Paint.Style.STROKE
        dashPaint.isAntiAlias = true
        dashPaint.pathEffect = DashPathEffect(floatArrayOf(6f, 6f), 0f)

        axisPaint.color = colorGray
        axisPaint.strokeWidth = 2f

        linePaint.color = orange
        linePaint.strokeWidth = 5f
        linePaint.strokeCap = Paint.Cap.ROUND
        linePaint.style = Paint.Style.STROKE

        textPaint.color = colorGray
        textPaint.textSize = 10.dpFloat()
        textOffset = textPaint.measureText("1")

        pointPaint.color = orange
        pointPaint.style = Paint.Style.FILL
    }

    /**
     * 设置线颜色
     * @param color Int
     */
    fun setLineColor(color: Int) {
        linePaint.color = color
    }

    /**
     * 设置点颜色
     * @param color Int
     */
    fun setPointColor(color: Int) {
        pointPaint.color = color
    }

    /**
     * 设置轴颜色
     * @param color Int
     */
    fun setLabelColor(color: Int) {
        textPaint.color = color
        axisPaint.color = color
        dashPaint.color = color
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height
        val top = 20.dp()
        val margin = 8.5.dp()
        val left = textOffset * 4 + margin
        val right = width - 2.dpFloat()
        val bottom = height - 20.dpFloat()

        val yTickSpacing = (yRange.toFloat() / numYTicks)


        // Draw X axis
        canvas.drawLine(left, bottom, right + 2, bottom, axisPaint)

        // Draw Y axis labels and tick marks
        axisPaint.pathEffect = pathEffect
        for (i in 0..numYTicks) {
            val value = yTickSpacing * i
            val yPos = bottom - (bottom - top) * value / yRange

            if (i == 0) {
                val valueY = "${value.toInt()}"
                val leftY = left - valueY.length * textOffset - margin
                canvas.drawText(valueY, leftY, yPos + textOffset, textPaint)
            } else {
                val valueY = "${value.toInt()}${labelY}"
                val leftY = left - valueY.length * textOffset - margin
                canvas.drawText(valueY, leftY, yPos + textOffset, textPaint)
                canvas.drawLine(left, yPos, right, yPos, axisPaint)
            }
        }
        axisPaint.pathEffect = null

        val labelXSize = labelX?.size ?: 7
        val spacing = (right - left) / labelXSize.toFloat()


        for (i in 0..labelXSize) {
            val xPos = left + spacing * i
            // Draw tick marks on X axis
            canvas.drawLine(
                xPos,
                bottom,
                xPos,
                bottom + textOffset / 2,
                axisPaint
            )

            if (labelX?.isNotEmpty() == true && i < labelXSize) {
                // Draw X axis labels
                val valueX = "${labelX?.getOrNull(i)}"
                val leftX = xPos - valueX.length / 2f * textOffset + spacing / 2f
                canvas.drawText(
                    valueX, leftX, height - textOffset, textPaint
                )
            }
        }

        pointList?.let { list ->
            var shouldDash = false
            var lastPosX = left + spacing / 2f
            var lastPosY = bottom

            for (i in list.indices) {
                val yPos = bottom - (bottom - top) * list[i] / yRange
                val xPos = left + spacing * i + spacing / 2f
                val isZero = specialZero && list[i] == 0f
                if (isZero) {
                    shouldDash = true
                        canvas.drawLine(lastPosX, lastPosY, xPos, yPos, dashPaint)
                } else {
                    if (shouldDash) {
                        canvas.drawLine(lastPosX, lastPosY, xPos, yPos, dashPaint)
                        shouldDash = false
                    } else {
                        if (i != 0) {
                            canvas.drawLine(lastPosX, lastPosY, xPos, yPos, linePaint)
                        }
                    }
                }
                lastPosX = xPos
                lastPosY = yPos
            }

            for (i in list.indices) {
                val isZero = specialZero && list[i] == 0f
                if (!isZero) {
                    val yPos = bottom - (bottom - top) * list[i] / yRange
                    val xPos = left + spacing * i + spacing / 2f
                    canvas.drawCircle(xPos, yPos, 8f, pointPaint)
                    // Draw points
                    canvas.drawText(
                        "${list[i]}${labelY}",
                        xPos - textOffset * 2,
                        yPos - textOffset * 2,
                        textPaint
                    )
                }
            }
        }
    }
}
