package com.line.view.anim

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.IntDef
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.yingfu.line.R
import kotlin.math.abs

/**
 * Created by ying.fu.
 * Date: 2019/10/15.
 */
class CountView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var countNumber = 0

    private var mPaint: Paint = Paint()
    private var mCountPaint: Paint= Paint()
    private var mCountBgPaint: Paint= Paint()
    private var mLinePaint: Paint= Paint()
    private fun init() {
        mPaint = Paint()
        mPaint.color = resources.getColor(R.color.main_color_avocado)
        mPaint.isAntiAlias = true
        mCountPaint = Paint()
        mCountPaint.color = resources.getColor(R.color.main_color_avocado)
        mCountPaint.isAntiAlias = true
        mCountBgPaint = Paint()
        mCountBgPaint.color = Color.WHITE
        mCountBgPaint.isAntiAlias = true
        mLinePaint = Paint()
        mLinePaint.color = Color.WHITE
        mLinePaint.isAntiAlias = true
    }

    private var startX = 0f
    private var offsetX = 0f
    private var endX = 0f
    private var heightVar = 0
    private var lineWidth = 0f
    private var textStartX = 0f
    private var oval = RectF()
    private var animX = SpringAnimation(this, DynamicAnimation.TRANSLATION_X, 0f)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        heightVar = measuredHeight
        mPaint.strokeWidth = heightVar.toFloat()
        mPaint.strokeCap = Paint.Cap.ROUND
        mCountPaint.textSize = heightVar / 1.5f
        lineWidth = heightVar / 16f
        mLinePaint.strokeWidth = lineWidth
        mLinePaint.strokeCap = Paint.Cap.ROUND
        oval.set(0f, 0f, heightVar.toFloat(), heightVar.toFloat())
        startX = (measuredWidth / 2 - heightVar).toFloat()
        endX = startX + heightVar * 2
        animX.spring.stiffness = SpringForce.STIFFNESS_LOW
        animX.spring.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        animX.addUpdateListener { _, value, velocity ->
            Log.e("CountView>>>", "onAnimationUpdate: value: $value\nvelocity: $velocity")
            offsetX = value
            postInvalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.drawLine(startX, measuredHeight / 2.toFloat(), endX, measuredHeight / 2.toFloat(), mPaint)
        val lineWidth = 30
        canvas.drawLine(startX - lineWidth, heightVar / 2f, startX + lineWidth, heightVar / 2f, mLinePaint)
        canvas.drawLine(startX + 2f * heightVar - lineWidth, heightVar / 2f, startX + 2f * heightVar + lineWidth, heightVar / 2f, mLinePaint)
        canvas.drawLine(startX + 2f * heightVar, heightVar / 2 - lineWidth.toFloat(), startX + 2f * heightVar, heightVar / 2 + lineWidth.toFloat(), mLinePaint)
        oval.left = startX + offsetX + measuredHeight * 0.5f
        oval.right = startX + offsetX + measuredHeight * 1.5f
        canvas.drawOval(oval, mCountBgPaint)
        textStartX = (heightVar - mCountPaint.measureText(countNumber.toString())) / 2
        canvas.drawText(countNumber.toString(), startX + offsetX + measuredHeight / 2f + textStartX, heightVar - textStartX, mCountPaint)
        canvas.restore()
    }

    @IntDef(OUT, LEFT, MIDDLE, RIGHT)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class TouchRect

    private var lastActionIsDown = false
    private var downX = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                animX.cancel()
                val x = event.x
                downX = x
                if (getTouchRect(x) == LEFT || getTouchRect(x) == RIGHT) {
                    lastActionIsDown = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = event.x - downX
                if (abs(moveX) >= 1) {
                    lastActionIsDown = false
                    offsetX = if (Math.abs(moveX) > heightVar) if (moveX > 0) heightVar.toFloat() else (-heightVar).toFloat() else moveX
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                val upX = event.x - downX
                if (event.x < startX + 0.5f * heightVar) {
                    if (Math.abs(upX) <= 1) {
                        if (lastActionIsDown) {
                            addCountNumber(false)
                            lastActionIsDown = false
                        }
                    } else {
                        addCountNumber(false)
                        startViewAnimation()
                    }
                } else if (event.x > startX + 1.5f * heightVar) {
                    if (abs(upX) < 1) {
                        if (lastActionIsDown) {
                            addCountNumber(true)
                            lastActionIsDown = false
                        }
                    } else {
                        addCountNumber(true)
                        startViewAnimation()
                    }
                }
            }
        }
        return true
    }

    private fun startViewAnimation() {
        animX.setStartValue(measuredHeight / 2.toFloat())
        animX.start()
    }

    private fun addCountNumber(b: Boolean) {
        countNumber = if (b) countNumber + 1 else if (countNumber > 0) countNumber - 1 else countNumber
        invalidate()
    }

    @TouchRect
    private fun getTouchRect(x: Float): Int {
        @TouchRect var touchRect = OUT
        if (x > startX - 0.5f * heightVar && x < startX + 0.5f * heightVar) {
            touchRect = LEFT
        } else if (x > startX + 0.5f * heightVar && x < startX + 1.5f * heightVar) {
            touchRect = MIDDLE
        } else if (x > startX + 1.5f * heightVar && x < startX + 2.0f * heightVar) {
            touchRect = RIGHT
        }
        return touchRect
    }

    companion object {
        private const val LEFT = 1
        private const val MIDDLE = 2
        private const val RIGHT = 3
        private const val OUT = 0
    }

    init {
        init()
    }
}