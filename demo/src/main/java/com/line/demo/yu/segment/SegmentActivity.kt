package com.line.demo.yu.segment

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import com.douyu.lib.image.DYImageLoader
import com.line.base.BaseActivity
import com.yin.lin.demo.databinding.ActivityTestImage2Binding
import tv.douyu.lib.ui.imagecroppicker.imagecropper.callback.BitmapCropCallback
import java.io.File


class SegmentActivity : BaseActivity() {

    lateinit var binding: ActivityTestImage2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        DYImageLoader.init(this)
        super.onCreate(savedInstanceState)
        binding = ActivityTestImage2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        bindImagePath()
    }


    private fun bindImagePath() {
        val pathV =
            "https://ts1.cn.mm.bing.net/th/id/R-C.f40ba86561918519b95431a5921e4f5d?rik=9AIbo9AhOYel0w&riu=http%3a%2f%2fwww.quazero.com%2fuploads%2fallimg%2f131210%2f1-131210210248.jpg&ehk=v81JiWKphT%2baLBzbhrxRkTUUwwnhJ5F2PFkm4xn4nEM%3d&risl=&pid=ImgRaw&r=0"

        binding.tvTest.apply {
            mIsScaleEnabled = true //这个打开可以进行双指放大缩小

            mIsRotateEnabled = false //这个打开可以进行双指旋转

            val outPutUri = getOutPutUri(this@SegmentActivity)
            val uri = getInPutUri(this@SegmentActivity)
            if (uri != null) {
                setImageUri(uri, outPutUri)
            }
        }




        binding.tvTest.setLongClickListener {
            binding.tvTest.cropAndSaveImage(Bitmap.CompressFormat.JPEG,
                100,
                object : BitmapCropCallback {
                    override fun onBitmapCropped(
                        resultUri: Uri,
                        offsetX: Int,
                        offsetY: Int,
                        imageWidth: Int,
                        imageHeight: Int,
                    ) {

                    }

                    override fun onCropFailure(t: Throwable) {
                    }
                })
            return@setLongClickListener true
        }

    }

    private fun getOutPutUri(context: Context): Uri? {
        var uri: Uri? = null
        try {
            val dir = "${context.cacheDir}/anchor_cover"
            val fileDir = File(dir)
            if (!fileDir.exists()) {
                fileDir.mkdir()
            }
            val f = File(dir, "crop_renwu.jpg")

            uri = Uri.fromFile(f)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return uri
    }

    private fun getInPutUri(context: Context): Uri? {
        var uri: Uri? = null
        try {
            val dir = "${context.cacheDir}/anchor_cover"
            val fileDir = File(dir)
            if (!fileDir.exists()) {
                fileDir.mkdir()
            }
            val f = File(dir, "renwu.png")
            uri = Uri.fromFile(f)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return uri
    }

}


