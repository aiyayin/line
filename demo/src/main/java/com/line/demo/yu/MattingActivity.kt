package com.line.demo.yu

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.customview.widget.ViewDragHelper
import com.line.base.BaseActivity
import com.line.base.util.ToolUtil.alphaShow
import com.yin.lin.demo.R
import com.yin.lin.demo.databinding.ActivityChartBinding
import com.yin.lin.demo.databinding.ActivityMattingBinding


class MattingActivity : BaseActivity() {

    lateinit var binding: ActivityMattingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMattingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PathUtil.instance.parse(this)

        bindImagePath()
    }

    private fun bindImagePath() {
        binding.dragLayout.setDragTargetView(binding.ivRenwuAmin)
        binding.ivRenwuBg.postDelayed({
            binding.ivRenwuAmin.alphaShow()
            binding.ivRenwuAmin.bindPath(PathUtil.instance.path, PathUtil.instance.bounds)
            binding.ivRenwuBg.bindPath(PathUtil.instance.path)
            Log.d("svg", "---- it.postDelayed")
        }, 2000)

    }


}