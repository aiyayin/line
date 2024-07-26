package com.line.demo.yu.segment

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.annotation.FloatRange

/**
 *
 * @description
 * @author fuyi
 * @date 2023/10/19
 */


private fun blurBitmap(
    context: Context,
    image: Bitmap,
    @FloatRange(from = 1.0, to = 25.0) blurRadius: Float,
): Bitmap? {

    // 创建RenderScript内核对象
    val rs = RenderScript.create(context)
    // 创建一个模糊效果的RenderScript的工具对象，第二个参数Element相当于一种像素处理的算法，高斯模糊的话用这个就好
    val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

    // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
    // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
    val input = Allocation.createFromBitmap(rs, image)
    // 创建相同类型的Allocation对象用来输出
    val type = input.type
    val output = Allocation.createTyped(rs, type)

    // 设置渲染的模糊程度, 25f是最大模糊度
    blurScript.setRadius(blurRadius)
    // 设置blurScript对象的输入内存
    blurScript.setInput(input)
    // 将输出数据保存到输出内存中
    blurScript.forEach(output)
    // 将数据填充到bitmap中
    output.copyTo(image)

    // 销毁它们释放内存
    input.destroy()
    output.destroy()
    blurScript.destroy()
    rs.destroy()
    //        type.destroy();
    return image
}