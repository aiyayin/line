package com.line.view.line

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import com.douyu.lib.utils.DYDensityUtils

/**
 *
 * @description
 * @author fuyi
 * @date 2024/9/3
 */
object RamboFloatTab {
    fun createCustomDrawable(context: Context, newColor: Int): LayerDrawable {
        // 定义第一个形状
        val shape1 = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setSize(3, 36)
            setColor(Color.parseColor("#00000000"))
        }

        // 定义第二个形状
        val shape2 = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setSize(36, 6)
            setColor(newColor)
            cornerRadius = DYDensityUtils.dip2px(3f).toFloat()
        }

        // 创建层列表Drawable
        val layerDrawable = LayerDrawable(arrayOf(shape1, shape2))

        // 设置每个图层的边距和位置
        val topMargin = DYDensityUtils.dip2px(22f)
        layerDrawable.setLayerInset(1, 0, topMargin, 0, 0) // Adjust as per need
        layerDrawable.setLayerSize(
            1, 36, 6
        )

        return layerDrawable
    }


}