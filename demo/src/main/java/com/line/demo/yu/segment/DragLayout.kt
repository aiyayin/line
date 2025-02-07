package com.line.demo.yu.segment

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.customview.widget.ViewDragHelper
import com.line.base.dp
import com.yin.lin.demo.R

/**
 * 描述：
 *
 * @author：yuanmin
 * @date: 2017/10/1 16:34
 * 邮箱：yuanmin@douyu.tv
 */
class DragLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {
    private var mDragger: ViewDragHelper? = null
    private var mDragView: View? = null
    private val initPointPosition = Point()
    private var isFirst = false

    init {
        init()
        setBackgroundColor(Color.BLUE)

        postDelayed({
            scaleX = 1.2f
            scaleY = 1.2f

//            postDelayed({
//                scaleX = 1.6f
//                scaleY = 1.6f
//            },5000)
        }, 2000)
    }

    /**
     * 初始化
     */
    private fun init() {
        mDragger = ViewDragHelper.create(this, 1.0f, object : ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {
                return true
            }

            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                return left
            }

            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
                return top
            }

            override fun getViewHorizontalDragRange(child: View): Int {
                return width
            }

            override fun getViewVerticalDragRange(child: View): Int {
                return height
            }

            override fun onViewPositionChanged(
                changedView: View,
                left: Int,
                top: Int,
                dx: Int,
                dy: Int,
            ) {
                initPointPosition.x = left
                initPointPosition.y = top
            }

            override fun onViewDragStateChanged(state: Int) {}
        })
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return mDragger!!.shouldInterceptTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDragger!!.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (mDragger!!.continueSettling(true)) {
            invalidate()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (null == mDragView) {
            return
        }
        if (!isFirst) {
            initPointPosition.x = 0
            initPointPosition.y = 0
            isFirst = true
        }
        if (VISIBLE == mDragView!!.visibility
            && (initPointPosition.x != 0 || initPointPosition.y != 0)
        ) {
//            mDragView.layout(initPointPosition.x,initPointPosition.y,
//                    initPointPosition.x+mDragView.getMeasuredWidth(),
//                    initPointPosition.y+mDragView.getMeasuredHeight());
            val lp = mDragView!!.layoutParams as LayoutParams
            if (lp.leftMargin != initPointPosition.x || lp.topMargin != initPointPosition.y - paddingTop) {
                lp.addRule(ALIGN_PARENT_RIGHT, 0)
                lp.leftMargin = initPointPosition.x
                lp.topMargin = initPointPosition.y - paddingTop
                lp.rightMargin = 0
                lp.bottomMargin = 0
                mDragView!!.layoutParams = lp
            }
        }
    }

    /**
     * @param view 需要拖拽的view
     */
    fun setDragTargetView(view: View?) {
        mDragView = view
    }


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        setColor(Color.RED)
        style = Paint.Style.STROKE
        strokeWidth = 2.dp().toFloat()

    }

    private val rect = RectF()
    private val mMatrix = Matrix()
    private val mMatrix2 = Matrix()
    private val path = Path()
    private var dp10 = 20.dp().toFloat()
    private var mDeleteDrawable: BitmapDrawable =
        (ContextCompat.getDrawable(context, R.drawable.ic_cover_close) as BitmapDrawable).apply {
        }




    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        val scaleSize = scaleX
        val childTargetScaleX = 1 / scaleSize
        paint.strokeWidth = 2.dp().toFloat() * childTargetScaleX
       val dp10 = 20.dp().toFloat() * childTargetScaleX
        val height = measuredHeight.toFloat()
        val width = measuredWidth.toFloat()
        Log.d("cover_item", "draw: height $height  dp10 $dp10   scaleX $scaleSize  $childTargetScaleX")
        if (height > 0) {

            val toInt = dp10 / 2f
            rect.set(toInt, toInt, width - toInt, height - toInt)
            path.reset()
            path.addRect(rect, Path.Direction.CCW)
            canvas?.drawPath(path, paint)


            mMatrix.reset()
            mMatrix.setScale(childTargetScaleX, childTargetScaleX)
            mMatrix.postTranslate(0f, 0f)
            canvas?.drawBitmap(mDeleteDrawable.bitmap, mMatrix, null)

            mMatrix.postTranslate(width - dp10, 0f)
            canvas?.drawBitmap(mDeleteDrawable.bitmap, mMatrix, null)

            mMatrix.postTranslate(0f, height - dp10)
            canvas?.drawBitmap(mDeleteDrawable.bitmap, mMatrix, null)


            mMatrix.postTranslate(-width + dp10, 0f)
            canvas?.drawBitmap(mDeleteDrawable.bitmap, mMatrix, null)

        }
    }
}