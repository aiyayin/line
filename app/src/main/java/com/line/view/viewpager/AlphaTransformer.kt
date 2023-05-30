package com.line.view.viewpager

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager

class AlphaTransformer : ViewPager.PageTransformer {

    companion object {
        private const val MIN_ALPHA = 0.4f
    }

    override fun transformPage(page: View, positionTemp: Float) {
        val parent = page.parent
        var leftOffset = 0f
        if (parent is ViewPager && page.width > 0) {
            leftOffset = parent.paddingLeft * 1.0f / page.width * 1.0f
        }
        val position = positionTemp - leftOffset
        //position为0时也是一种异常情况,对于这种情况直接设置alpha+scaleY为默认值
        if (position == 0f) {
            page.alpha = 1.0f
        } else if (position < -1 || position > 1) {
            page.alpha = MIN_ALPHA
        } else {
            //不透明->半透明
            if (position < 0) { //(-1, 0)
                page.alpha = MIN_ALPHA + (1.0f + position) * (1.0f - MIN_ALPHA)
            } else { //(0,1]
                //半透明->不透明
                page.alpha = MIN_ALPHA + (1.0f - position) * (1.0f - MIN_ALPHA)
            }
        }

        Log.d(
            "cover_banner",
            "onPageSelected page $page leftOffset $leftOffset position $position page.alpha : ${page.alpha}"
        )


    }
}