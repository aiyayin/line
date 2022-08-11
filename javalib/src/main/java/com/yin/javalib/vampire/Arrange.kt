package com.yin.javalib.vampire

/**
 * Created by ying.fu.
 * Date: 2018/8/10.
 */
object Arrange {
    @JvmStatic
    fun main(args: Array<String>) {
        val chs = charArrayOf('a', 'b', 'c')
        arrange(chs, 0, chs.size)
    }

    fun arrange(chs: CharArray, start: Int, len: Int) {
        if (start == len - 1) {
            for (i in chs.indices) print(chs[i])
            println()
            return
        }
        for (i in start until len) {
            var temp = chs[start]
            chs[start] = chs[i]
            chs[i] = temp
            arrange(chs, start + 1, len)
            temp = chs[start]
            chs[start] = chs[i]
            chs[i] = temp
        }
    }
}