package com.line.demo.yu.segment

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView


/**
 *
 * @description
 * @author fuyi
 * @date 2023/9/12
 */
class RenwuSVGImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    val mImgPaint = Paint()
    val mPathPaint = Paint()
    val pathUtil = PathUtil()
    private var animationProgress = 0f
    val mPathColors = intArrayOf(Color.BLUE, Color.parseColor("#8dFFFFFF"))
    val mPathPositions = floatArrayOf(0f, 1f)

    init {
        mImgPaint.color = Color.YELLOW
        mImgPaint.strokeWidth = 20f
        mImgPaint.style = Paint.Style.STROKE



        val shader: Shader =
            LinearGradient(0f, 0f, 1080f, 600f, mPathColors, mPathPositions, Shader.TileMode.CLAMP)
        mPathPaint.shader = shader
//        mPathPaint.color = Color.WHITE
        mPathPaint.strokeWidth = 20f
        mPathPaint.style = Paint.Style.STROKE

        pathUtil.parse(context)


        val animator = ObjectAnimator.ofFloat(0f, 1f).apply {
            duration = 5 * 1000
        }.apply {
            addUpdateListener {
                Log.d("anim value ", "---- ${it.animatedValue}")
                setAnimationProgress(it.animatedValue as Float)
            }
        }
        animator.start()
    }

    // 截取路径
    val dst = Path()
    val measure = PathMeasure()
    override fun onDraw(canvas: Canvas) {
        if (canvas != null) {
            val path = pathUtil.path
            val saveCount = canvas.save()
            canvas.clipPath(path) // 根据路径裁剪画布
            super.onDraw(canvas)
            canvas.restoreToCount(saveCount)
            canvas.drawPath(path, mImgPaint)


            dst.reset()
            // 计算截取的路径长度
            measure.setPath(path, false)
            val length: Float = measure.length
            val start = length * animationProgress
            val end = start+length*0.3f
            Log.d("anim value ", "animationProgress ---- $animationProgress  start : $start end : $end")

            measure.getSegment(start, end, dst, true)

            // 绘制路径
            canvas.drawPath(dst, mPathPaint)
        } else {
            super.onDraw(canvas)
        }
    }

    /**
     *
     * @param progress Float [android.graphics.PathMeasure.PathMeasure(android.graphics.Path, boolean)]
     */
    private fun setAnimationProgress(progress: Float) {
        animationProgress = progress
        invalidate() // 重新绘制视图
    }
}