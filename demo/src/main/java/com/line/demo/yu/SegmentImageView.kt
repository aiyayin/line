package com.line.demo.yu

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatImageView


/**
 *
 * @description
 * @author fuyi
 * @date 2023/9/12
 *
 */
class SegmentImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    private val mImgPaint = Paint()
    private val mPathPaint = Paint()
    private var mPath: Path? = null

    private val mLightPaint = Paint()
    private var animatorSet: AnimatorSet? = null
    private var colors = intArrayOf(0x00FFFFFF, -0x55000001, -0x55000001, 0x00FFFFFF)
    private var positions = floatArrayOf(0f, 0.4f, 0.5f, 1f)

    private val mPathColors = intArrayOf(Color.RED, Color.parseColor("#8dFFFFFF"))
    private val mPathPositions = floatArrayOf(0f, 1f)
    private val mDuration = 2 * 1000L
    private val mRepeatCount = 0
    private var mk = 0.45f
    private var mw = -1


    // 截取路径
    private val dst = Path()
    private val topPath = Path()
    private val bottomPath = Path()
    private val measure = PathMeasure()

    // 获取路径的边界矩形
    private var bounds = RectF()

    private var animationProgress = 0f

    private var moveDistance = 0f
    private var lineHeight = 20f

    init {
        mImgPaint.color = Color.YELLOW
        mImgPaint.strokeWidth = 20f
        mImgPaint.style = Paint.Style.STROKE


        mPathPaint.color = Color.parseColor("#FFFFFF")
        mPathPaint.strokeWidth = 20f
        mPathPaint.style = Paint.Style.STROKE

        Log.d(DYSVGParser.TAG,
            "svg image anim init")


        // 设置起点
        topPath.moveTo(0f, 0f)
        topPath.cubicTo(100f, 10f, 10f, 300f, bounds.width(), bounds.height())
        val mMatrix = Matrix()
        mMatrix.setTranslate(bounds.left, bounds.top)
        topPath.transform(mMatrix)

        // 设置起点
        bottomPath.moveTo(bounds.width(), bounds.height())
        bottomPath.cubicTo(100f, 100f, 600f, 60f, 0f, 0f)
    }


    override fun onDraw(canvas: Canvas?) {
        val path = mPath
        if (canvas != null && path != null) {
            Log.d("svg", "path not null ")

            val saveCount = canvas.save()

            canvas.clipPath(path) // 根据路径裁剪画布
            super.onDraw(canvas)


            if (animationProgress != 1f) {
                canvas.drawPath(topPath, mPathPaint)
                canvas.drawPath(bottomPath, mPathPaint)
            }

            canvas.drawRect(bounds, mLightPaint)

            canvas.restoreToCount(saveCount)

            canvas.drawPath(path, mImgPaint)

            // 绘制路径
            canvas.drawPath(dst, mPathPaint)

        } else {
            Log.d("svg", "path is null ")
            super.onDraw(canvas)
        }
    }


    private fun setAnimationProgress(progress: Float) {
        animationProgress = progress
        dst.reset()
        measure.setPath(PathUtil.instance.maxPath, false)
        val length: Float = measure.length
        val start = length * animationProgress
        val end = start + length * 0.3f
        Log.d("svg", "svg animationProgress ---- $animationProgress  start : $start end : $end")
        Log.d("svg", "svg  animationProgress ----bounds top ${bounds.top}  left ${bounds.left} ")

        measure.getSegment(start, end, dst, true)

        val animationProgress = animationProgress

        val mMatrix = Matrix()
        val speed = 20f
        val left = speed * animationProgress
        val top = -speed * animationProgress
        mMatrix.setTranslate(left,
            top)
        Log.d("svg", "svg  animationProgress ----bounds setTranslate top $top  left $left ")
        topPath.transform(mMatrix)
        mMatrix.reset()
        mMatrix.setTranslate(-speed * animationProgress,
            speed * animationProgress)
        bottomPath.transform(mMatrix)
        invalidate() // 重新绘制视图
    }


    private fun showAnimation(w: Int) {

        // 斜率k
//        float k = 1f * h / w;
        val k = mk
        if (mw < 0) {
            mw = w / 3
        }
        // 偏移
        val offset = mw.toFloat()
        animatorSet?.cancel()
        val mValueAnimator = ValueAnimator.ofFloat(0f - offset * 2, w + offset * 2)
        mValueAnimator?.let {
            it.repeatCount = mRepeatCount
            it.interpolator = LinearInterpolator()
            it.duration = mDuration
            it.addUpdateListener { animation: ValueAnimator ->
                val value = animation.animatedValue as Float
                Log.d("svg", "--value-- $value  k : $k")

                val mLinearGradient = LinearGradient(
                    value,
                    k * value,
                    value + offset,
                    k * (value + offset),
                    colors,
                    positions,
                    Shader.TileMode.CLAMP
                )
                mLightPaint.shader = mLinearGradient
                invalidate()
            }
        }

        moveDistance = 0f
        lineHeight = bounds.bottom * 0.8f // 设置线段长度的比例


        // 创建动画对象，设置属性动画的起始值和结束值
        val animator3 = ValueAnimator.ofFloat(0f, bounds.right)
        animator3.duration = mDuration
        // 监听动画的更新事件
        animator3.addUpdateListener { animation ->
            moveDistance = animation.animatedValue as Float
            Log.d("svg", "---moveDistance - $moveDistance")

            invalidate() // 重绘界面
        }

        val animator = ObjectAnimator.ofFloat(0f, 1f)
        animator.let {
            it.repeatCount = mRepeatCount
            it.interpolator = LinearInterpolator()
            it.duration = mDuration
            it.addUpdateListener {
                Log.d("svg", "---- ${it.animatedValue}")
                setAnimationProgress(it.animatedValue as Float)
            }
        }
        animatorSet = AnimatorSet()
        animatorSet?.apply {
            playTogether(animator, mValueAnimator, animator3)
            start()
        }
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorSet?.cancel()
        animatorSet = null
    }

    fun bindPath(path: Path, bounds: RectF) {
        this.mPath = path
        this.bounds =bounds
        if (measuredWidth <= 0) {
            post {
                showAnimation(measuredWidth)
            }
        } else {
            showAnimation(measuredWidth)
        }
    }


}