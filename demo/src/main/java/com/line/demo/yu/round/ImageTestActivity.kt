package com.line.demo.yu.round

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import com.douyu.lib.image.DYImageLoader
import com.line.base.BaseActivity
import com.yin.lin.demo.R
import com.yin.lin.demo.databinding.ActivityTestImageBinding


class ImageTestActivity : BaseActivity() {

    lateinit var binding: ActivityTestImageBinding
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

//        val roundingParams = RoundingParams.fromCornersRadius(10.dp().toFloat())
//        val builder = GenericDraweeHierarchyBuilder(resources)
//        builder.actualImageScaleType = ScalingUtils.ScaleType.FIT_CENTER
//        builder.setRoundingParams(roundingParams)
//
//        binding.tvTest.hierarchy = builder.build()
//        val uri: Uri = Uri.parse(pathV)
//        binding.tvTest.setImageURI(uri)

// 加载图像并应用 fitCenter 和圆角转换
//        Glide.with(this)
//            .load(pathV)
//            .transform(FitCenter(), RoundCornersTransformation(
//                this,
//                10.dp(),
//                RoundCornersTransformation.CornerType.ALL
//            ))
//        .into(binding.tvTest)

        binding.tvTest.setImageResource(R.drawable.renwu2)




        binding.tvTest.setOnLongClickListener {
            getCurrentBitmap()
            return@setOnLongClickListener true
        }

    }

    private fun getCurrentBitmap() {
        binding.tvTest.apply {
            val currentBitmap: Bitmap =
                (drawable as? BitmapDrawable)?.bitmap ?: return


            val rect: RectF = this.getDisplayRect()

            Log.d("image",
                "111 rect.left : ${rect.left} rect.right : ${rect.right} rect.top : ${rect.top} rect.bottom : ${rect.bottom}")
            val rectTarget = RectF()

            rectTarget.top = Math.max(0f, rect.top)
            rectTarget.left = Math.max(0f, rect.left)
            rectTarget.right = Math.min(this.width.toFloat(), rect.right)
            rectTarget.bottom = Math.min(this.height.toFloat(), rect.bottom)
            Log.d("image",
                "222 rect.left : ${rectTarget.left} rect.right : ${rectTarget.right} rect.top : ${rectTarget.top} rect.bottom : ${rectTarget.bottom}")

            Log.d("image",
                "222 rectTarget.width().toInt() : ${
                    rectTarget.width().toInt()
                }  rectTarget.height().toInt() : ${rectTarget.height().toInt()}")
            val matrix = Matrix()
            this.getDisplayMatrix(matrix)
            matrix.postTranslate(-rectTarget.left, -rectTarget.top)
            val transformedBitmap = Bitmap.createBitmap(rectTarget.width().toInt(),
                rectTarget.height().toInt(),
                Bitmap.Config.ARGB_8888)
            val canvas = Canvas(transformedBitmap)
            canvas.drawBitmap(currentBitmap, matrix, null)
            Log.d("image",
                "333 rect.left : ${rect.left} rect.right : ${rect.right} rect.top : ${rect.top} rect.bottom : ${rect.bottom}")

        }
    }


}
