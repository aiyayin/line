package com.line.demo.yu

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import kotlin.math.sqrt

/**
 * 外边缘支持流光动画的ViewGroup
 *
 * @author zhonghua@douyu.tv
 */
class OutlineLightStreamContainer @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    /*
    attr.xml

    <declare-styleable name="OutlineLightStreamContainer">
    <!--边框宽度-->
    <attr name="olsc_borderWidth" format="dimension" />
    <!--边框圆角-->
    <attr name="olsc_cornerRadius" format="dimension" />
    <!--渐变颜色1-->
    <attr name="olsc_outlineColor1" format="color" />
    <!--渐变颜色2-->
    <attr name="olsc_outlineColor2" format="color" />
    <!--光圈执行速度,单位毫秒-->
    <attr name="olsc_duration" format="integer" />
    </declare-styleable>
    */

    //自定义属性，目前不太希望暴露给外部动态修改，只支持xml中定义；因为如果放开修改有一些已经被懒加载的对象需要重新创建，比较麻烦，暂时没这种诉求
    private val mBorderWidth: Float
    private val mCornerRadius: Float
    private val mDuration: Int
    private val mOutlineColor1: Int
    private val mOutlineColor2: Int
    val pathUtil = PathUtil()

    //构造
    init {
        //解析自定义属性

            mBorderWidth = 5.dp()
            mCornerRadius = 5.dp()
            mDuration = 3000
            mOutlineColor1 =  Color.YELLOW
            mOutlineColor2 = Color.RED

        //设置view圆角
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                // 设置圆角率为
                outline.setRoundRect(0, 0, view.width, view.height, mCornerRadius)
            }
        }
        clipToOutline = true

        pathUtil.parse(context)

    }

    //画笔
    private val paint by lazy {
        //画笔抗锯齿
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            //以视图中心为原点，两个色值交替扫描渐变，注意起始颜色与终止颜色要一致才不会有边缘突变的问题
            shader = SweepGradient(
                    width / 2f,
                    height / 2f,
                    intArrayOf(mOutlineColor1, mOutlineColor2, mOutlineColor1, mOutlineColor2, mOutlineColor1),
                    null
            )
        }
    }
//
//    //中间要镂空的部分
//    private val path by lazy {
//        Path().apply {
//            val left = mBorderWidth
//            val top = mBorderWidth
//            val right = width - mBorderWidth
//            val bottom = height - mBorderWidth
//            val rectF = RectF(left, top, right, bottom)
//            addRoundRect(rectF, mCornerRadius, mCornerRadius, Path.Direction.CCW)
//        }
//    }

    //使用animator来改变“rotateAngle”这个属性的值，从0-360无限循环，本质上是画布旋转的角度
    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "rotateAngle", 0f, 360f).apply {
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            repeatMode = ObjectAnimator.RESTART
            duration = mDuration.toLong()
        }.apply {
            addUpdateListener {
                Log.d("anim value ", "---- ${it.animatedValue}")
            }
        }
    }

    //注意这个成员变量的值是通过 ObjectAnimator 来修改的
    private var rotateAngle = 0f
        set(value) {
            field = value
            invalidate()
        }

    //绘制内容与宽高相关，所以在这个回调中开始动画
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        animator.start()
    }

    override fun dispatchDraw(canvas: Canvas) {
        //中心点
        val cx = width / 2f
        val cy = height / 2f
        //外切圆半径，即对角线的一半
        val radius = sqrt((width * width + height * height).toDouble()) / 2f
        canvas.withSave {
            //扣掉中间的部分，使用这个废弃的方法，没有API限制
            @Suppress("DEPRECATION")
            canvas.clipPath(pathUtil.path, Region.Op.DIFFERENCE)
            //画布沿着View中心不停地旋转
            canvas.rotate(rotateAngle, cx, cy)
            //绘制一个带渐变色的内容
            canvas.drawCircle(cx, cy, radius.toFloat(), paint)
        }
        //最后绘制子View
        super.dispatchDraw(canvas)
    }


    //方便看清楚save和restore之间到底做了啥
    private fun Canvas.withSave(runnable: () -> Unit) {
        val save = this.save()
        runnable.invoke()
        this.restoreToCount(save)
    }

    //dp转px工具方法，避免依赖外部
    private fun dip2px(dpValue: Float) = (dpValue * context.resources.displayMetrics.density + 0.5f)

    //数字dp单位工具方法，避免依赖外部
    private fun Number.dp() = dip2px(this.toFloat())
}