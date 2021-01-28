package line.view.bezier

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.example.yingfu.line.R
import kotlin.math.abs

/**
 * Created by ying.fu.
 * Date: 2020/4/27.
 */
class CirclePathAnimView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var mAnimatorValue = 0f
    private var mPathMeasure: PathMeasure = PathMeasure()
    private val mDevPath: Path
    private val mPaint: Paint
    private var mValueAnimator: ValueAnimator
    private val airplayBitmap: Bitmap
    private var circlePath = Path()
    private var matrixVar = Matrix()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        var radius = 0
        radius = if (width >= height) {
            height / 2 - height / 8
        } else {
            width / 2 - width / 8
        }
        //绘制path
        //先画圆的path，但是这个圆只是用来计算
        circlePath.reset()
        circlePath.addCircle(width / 2.toFloat(), height / 2.toFloat(), radius.toFloat(), Path.Direction.CW)


        //计算圆的path的长度
        mPathMeasure.setPath(circlePath, true)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val length = mPathMeasure.length
        val stop = length * mAnimatorValue
        //在0到0.5以前，起点不变，0.5到1，起点开始向终点靠拢。
        val start = (stop - (0.5 - abs(mAnimatorValue - 0.5)) * length).toFloat()
        Log.e("CirclePathAnimView", "length: $length stop: $stop start: $start")
        mDevPath.reset()
        mPathMeasure.getSegment(start, stop, mDevPath, true)
        canvas.drawPath(mDevPath, mPaint)
        matrixVar.reset()
        mPathMeasure.getMatrix(stop, matrixVar, PathMeasure.POSITION_MATRIX_FLAG or PathMeasure.TANGENT_MATRIX_FLAG)
        matrixVar.preTranslate(-airplayBitmap.width / 2.toFloat(), -airplayBitmap.height / 2.toFloat())
        canvas.drawBitmap(airplayBitmap, matrixVar, mPaint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mValueAnimator.cancel()
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null) //关闭硬件加速
        //初始化画笔
        mPaint = Paint()
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = resources.getDimension(R.dimen.mtrl_calendar_month_horizontal_padding)
        mPaint.color = resources.getColor(R.color.colorPrimary)

        //飞机图片
        airplayBitmap = BitmapFactory.decodeResource(resources, R.drawable.fly)

        //画真正显示的path
        mDevPath = Path()


        //开始动画，当然当前动画你可以单独写成一个方法
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f)
        mValueAnimator.interpolator = AccelerateDecelerateInterpolator()
        mValueAnimator.duration = 3000
        mValueAnimator.repeatCount = -1
        mValueAnimator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { valueAnimator ->
            mAnimatorValue = valueAnimator.animatedValue as Float
            invalidate()
        })
        mValueAnimator.start()
    }
}