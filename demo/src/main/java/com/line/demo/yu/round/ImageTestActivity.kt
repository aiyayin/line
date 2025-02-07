package com.line.demo.yu.round

import android.graphics.Bitmap
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Matrix
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.douyu.lib.image.DYImageLoader
import com.line.base.BaseActivity
import com.line.base.dp
import com.yin.lin.demo.databinding.ActivityTestImageBinding


class ImageTestActivity : BaseActivity() {

    lateinit var binding: ActivityTestImageBinding
    val camera = Camera()
    val matrix = Matrix()
    override fun onCreate(savedInstanceState: Bundle?) {
        DYImageLoader.init(this)
        super.onCreate(savedInstanceState)
        binding = ActivityTestImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindImagePath()
    }

    private fun bindImagePath() {
        val pathV =
            "https://ts1.cn.mm.bing.net/th/id/R-C.f40ba86561918519b95431a5921e4f5d?rik=9AIbo9AhOYel0w&riu=http%3a%2f%2fwww.quazero.com%2fuploads%2fallimg%2f131210%2f1-131210210248.jpg&ehk=v81JiWKphT%2baLBzbhrxRkTUUwwnhJ5F2PFkm4xn4nEM%3d&risl=&pid=ImgRaw&r=0"
        val path2 =
            "https://ts1.cn.mm.bing.net/th/id/R-C.66d7b796377883a92aad65b283ef1f84?rik=sQ%2fKoYAcr%2bOwsw&riu=http%3a%2f%2fwww.quazero.com%2fuploads%2fallimg%2f140305%2f1-140305131415.jpg&ehk=Hxl%2fQ9pbEiuuybrGWTEPJOhvrFK9C3vyCcWicooXfNE%3d&risl=&pid=ImgRaw&r=0"
//        DYImageLoader.getInstance().setImageURL(this,binding.tvTest, pathV)


        binding.btnTest.setOnClickListener {
            binding.tvTest.apply {
                rotationY += 10f
            }
        }

        binding.btnBitmap.setOnClickListener {
            binding.tvResult.setImageBitmap(createBitmapFromViewWithRotation2(binding.tvTest))
        }

        binding.tvTest.postDelayed({
            binding.tvResult.translationX = -binding.tvTest.measuredWidth.toFloat()
            binding.tvResult.translationY = -binding.tvTest.measuredHeight.toFloat()
            Log.i(
                "yin>>",
                "postDelayed view.measuredWidth : ${binding.tvTest.measuredWidth}  ${binding.tvTest.measuredHeight} "
            )

        }, 3000)
//        val roundingParams = RoundingParams.fromCornersRadius(10.dp().toFloat())
//        val builder = GenericDraweeHierarchyBuilder(resources)
//        builder.actualImageScaleType = ScalingUtils.ScaleType.FIT_CENTER
//        builder.setRoundingParams(roundingParams)
//
//        binding.tvTest.hierarchy = builder.build()
//        val uri: Uri = Uri.parse(pathV)
//        binding.tvTest.setImageURI(uri)

// 加载图像并应用 fitCenter 和圆角转换
        Glide.with(this)
            .load(pathV)
            .transform(
                FitCenter(), RoundCornersTransformation(
                    this,
                    10.dp(),
                    RoundCornersTransformation.CornerType.ALL
                )
            )
            .into(binding.tvTest)

//        binding.tvTest.visibility = View.INVISIBLE
//        binding.tvTest.setImageResource(R.drawable.renwu2)
//        binding.tvTest.setOnLongClickListener {
//            getCurrentBitmap()
//            return@setOnLongClickListener true
//        }

    }


    fun createBitmapFromViewWithRotation2(view: View): Bitmap? {
        // 保存原始的旋转角度并重置为0以进行测量和布局
        val originalRotationY = view.rotationY
        view.rotationY = 0f

        view.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)

        // 计算新Bitmap的尺寸，考虑旋转可能带来的外形变化
        val bitmapWidth = view.measuredWidth * 3
        val bitmapHeight = view.measuredHeight * 3

        // 移动画布，使旋转中心居中
        val centerX = view.measuredWidth / 2f
        val centerY = view.measuredHeight / 2f

        // 创建一个更大的Bitmap来容纳旋转后的视图
        val bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // 设置3D旋转
        val camera = Camera()
        camera.save()

        // 为了让所有内容都能显示全，这里保留透视效果但不进行z轴过度平移
        camera.rotateY(originalRotationY)

        canvas.save()


        canvas.translate(bitmapWidth / 2f, bitmapHeight / 2f)

        // 应用相机变换到画布上
        camera.applyToCanvas(canvas)

        // 恢复相机的状态
        camera.restore()

        // 将画布移动回去
        canvas.translate(-centerX, -centerY)

        canvas.scale(view.scaleX, view.scaleY)

        // 绘制视图到画布上
        view.draw(canvas)

        // 恢复画布的状态
        canvas.restore()

        // 恢复视图的原始旋转角度
        view.rotationY = originalRotationY

//        // 裁剪或缩放到原始视图大小（可选）
//        val finalBitmap = Bitmap.createBitmap(bitmap, (bitmapWidth - view.measuredWidth) / 2, (bitmapHeight - view.measuredHeight) / 2,
//                view.measuredWidth, view.measuredHeight)
        Log.i(
            "yin>>",
            "view.measuredWidth : ${view.measuredWidth}  ${view.measuredHeight}  bitmap: $bitmapWidth   $bitmapHeight"
        )
        Log.i("yin>>", "result : ${bitmap.width}  ${bitmap.height}")
        return bitmap
    }


}
