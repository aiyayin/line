package com.line.demo.yu.segment

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.line.base.util.ToolUtil
import com.yin.lin.demo.R


/**
 *
 * @description
 * @author fuyi
 * @date 2023/9/12
 */
class RenwuImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    val mImgPaint = Paint()
    val path = Path()
    var bitmap: Bitmap? = null

    init {
//        val colorMatrix = ColorMatrix(floatArrayOf(
//            1f, 0f, 0f, 0f, 0f,  // 红色通道
//            0f, 1f, 0f, 0f, 0f,  // 绿色通道
//            0f, 0f, 1f, 0f, 0f,  // 蓝色通道
//            0f, 0f, 0f, 0.5f, 0f // 透明度通道
//        ))
//
//        val colorFilter: ColorFilter = ColorMatrixColorFilter(colorMatrix)
//        mImgPaint.setColorFilter(colorFilter)
        mImgPaint.color = Color.YELLOW
        mImgPaint.strokeWidth = 3f
        mImgPaint.style = Paint.Style.STROKE

        val inputBitmap = BitmapFactory.decodeResource(resources, R.drawable.mask)
        bitmap = inputBitmap
        val width = inputBitmap.width
        val height = inputBitmap.height

        val grayBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(grayBitmap)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f) // 将图片转换为灰度图像

        val filter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = filter
        canvas.drawBitmap(inputBitmap, 0f, 0f, paint)
        var startX = -1f
        var startY = -1f
        for (x in 0 until width) {
            for (y in 0 until height) {
                val pixel = grayBitmap.getPixel(x, y)
                val gray = Color.red(pixel) // 获取灰度值
                if (x > 0 && y > 0 && x < width - 1 && y < height - 1) {
                    val pixelX1Y1 = grayBitmap.getPixel(x - 1, y - 1)
                    val pixelX1Y2 = grayBitmap.getPixel(x - 1, y + 1)
                    val pixelX2Y1 = grayBitmap.getPixel(x + 1, y - 1)
                    val pixelX2Y2 = grayBitmap.getPixel(x + 1, y + 1)
                    val gradientX =
                        (pixelX2Y1 and 0xFF) + (pixelX2Y2 and 0xFF) - ((pixelX1Y1 and 0xFF) + (pixelX1Y2 and 0xFF))
                    val gradientY =
                        (pixelX1Y2 and 0xFF) + (pixelX2Y2 and 0xFF) - ((pixelX1Y1 and 0xFF) + (pixelX2Y1 and 0xFF))
                    val gradientMagnitude =
                        Math.sqrt((gradientX * gradientX + gradientY * gradientY).toDouble())
                            .toInt()
                    if (gradientMagnitude > 128) {
                        if (startX == -1f && startY == -1f) { // 记录第一个边缘点的坐标
                            startX = x.toFloat()
                            startY = y.toFloat()
                            path.moveTo(startX, startY)
                        }
                        // 边缘点添加到Path
                        path.lineTo(x.toFloat(), y.toFloat())
//                        edgeCanvas.drawPoint(x.toFloat(), y.toFloat(), edgePaint)
                    }
                }
            }
        }
        path.lineTo(startX, startY) // 移动到第一个边缘点作为起始点
        path.close() // 闭合Path

        val mMatrix = Matrix()
        // 设置缩放比例：2倍缩放
        val radio = 1.0f * ToolUtil.getScreenWidth(context) / width
        mMatrix.setScale(radio, radio)
        // 对Path对象应用缩放变换
        path.transform(mMatrix)

    }

    override fun onDraw(canvas: Canvas) {
        if (canvas != null) {
            val saveCount = canvas.save()
            canvas.clipPath(path) // 根据路径裁剪画布
            super.onDraw(canvas)
            canvas.restoreToCount(saveCount)
            canvas.drawPath(path, mImgPaint)
        } else {
            super.onDraw(canvas)
        }
    }

 }