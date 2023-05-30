package com.line.base

import com.line.base.util.ToolUtil


/**
 * 专门用来包裹一些历史逻辑代码
 */
fun runSomeOldCode(oldCode: () -> Unit) {
    oldCode.invoke()
}

/**
 * 快速dp化
 */
fun Number.dp(): Int {
    return ToolUtil.dpToPx(this.toFloat())
}

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Number.dpFloat(): Float {
    val scale = LineApplication.context.resources.displayMetrics.density
    return this.toFloat() * scale + 0.5f
}