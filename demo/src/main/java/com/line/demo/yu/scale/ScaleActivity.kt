package com.line.demo.yu.scale

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.line.base.BaseActivity
import com.yin.lin.demo.databinding.ActivityScaleBinding

/**
 *
 * @description
 * @author fuyi
 * @date 2024/7/26
 */
class ScaleActivity : BaseActivity() {
    private lateinit var binding: ActivityScaleBinding
    private var llTextPivotY = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScaleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llText.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View?,
                                        left: Int,
                                        top: Int,
                                        right: Int,
                                        bottom: Int,
                                        oldLeft: Int,
                                        oldTop: Int,
                                        oldRight: Int,
                                        oldBottom: Int) {
                if (bottom != oldBottom || left != oldLeft) {

                }
            }
        })



        binding.llText.post {
            llTextPivotY = binding.llText.height.toFloat()
            bindScale()
        }
        var index = 1
        binding.tvAdd.setOnClickListener {
            index += 1
            val textView = TextView(this)
            textView.gravity = Gravity.CENTER
            textView.text = "$index"
            textView.setBackgroundColor(Color.parseColor("#4d567878"))
            val lp = LinearLayout.LayoutParams(100, 100)
            lp.topMargin = 20
            lp.bottomMargin = 20
            binding.llText.addView(textView, lp)

            llTextPivotY += 140
            bindScale()

        }


        binding.tvRemove.setOnClickListener {
            binding.llText.removeAllViews()
            llTextPivotY = 0f
        }

        binding.text2.setOnClickListener {
            it.visibility = View.GONE
        }
        binding.text3.setOnClickListener {
            it.visibility = View.GONE
        }
    }

    private fun bindScale() {
        binding.llText.apply {
            this.pivotX = width.toFloat()
            this.pivotY = if (llTextPivotY == 0f) height.toFloat() else llTextPivotY
            scaleX = 1.38f
            scaleY = 1.38f
            Log.d("yin", "yin>> height: ${this.height} pivotX: ${this.pivotX}   left: ${this.left} view.bottom: ${binding.llText.bottom}")
        }
    }

}