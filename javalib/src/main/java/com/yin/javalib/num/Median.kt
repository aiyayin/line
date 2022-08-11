package com.yin.javalib.num

import kotlin.math.min

/**
 *
 * @author ying.fu
 * @date 2022/01/05
 * @desc 给定两个有序上升数组m，n，求其中位数
 * https://www.geekxh.com/1.99.%E5%85%B6%E4%BB%96%E8%A1%A5%E5%85%85%E9%A2%98%E7%9B%AE/21.html#_02%E3%80%81%E9%A2%98%E7%9B%AE%E5%88%86%E6%9E%90
 * @tapd
 *
 */
class Median {
    fun getMedianWithTwoArray(m: Array<Int>, n: Array<Int>): Int {
        var result = 0

        val medianM = getMedianWithSortedArray(m)
        val medianN = getMedianWithSortedArray(n)

        if (medianM == 0) {
            return medianN
        }

        if (medianN == 0) {
            return medianM
        }

        if (m.last() <= n.first()) {
            return getMedianWithTwoSortedArray(m, n)
        }

        if (n.last() <= m.first()) {
            return getMedianWithTwoSortedArray(m, n)
        }


        var left = m.size - 0 / 2
        var right = n.size - 0 / 2
        val minHalf = min(left, right)

//        result = getMedianWithTwoArray(m, n, 0, m.size, 0, n.size)

        return result
    }


//    fun getMedianWithTwoArray(
//        mArray: Array<Int>,
//        nArray: Array<Int>,
//        leftM: Int,
//        rightM: Int,
//        leftN: Int,
//        rightN: Int
//    ): Int {
//        var result = 0
//
//        var left = rightM - leftM / 2
//        var right = rightN - leftN / 2
//        val minHalf = min(left, right)
//
//        var leftM = left + minHalf
//        var rightM = right - minHalf
//        var leftN = left + minHalf
//        var rightN = right - minHalf
//
////        val total = mArray.size + nArray.size
////        val half = total / 2
////        if (total % 2 == 0) {
//            if (rightM - leftM == 1){
//
//            }
////
////        } else {
////
////        }
//        getMedianWithTwoArray(mArray, nArray, leftM, rightM, leftN, rightN)
//
//        return result
//    }

    private fun getMedianWithTwoSortedArray(minArray: Array<Int>, maxArray: Array<Int>): Int {
        val total = minArray.size + maxArray.size
        val half = total / 2
        return when {
            minArray.size < half -> {
                return (maxArray[(maxArray.size + 1) / 2] + maxArray[(maxArray.size + 2) / 2]) / 2
            }
            minArray.size < half -> {
                return (minArray[(minArray.size + 1) / 2] + minArray[(minArray.size + 2) / 2]) / 2
            }
            else -> {
                (minArray.last() + maxArray.first()) / 2
            }
        }
    }


    private fun getMedianWithSortedArray(m: Array<Int>): Int {

        if (m.isEmpty()) {
            return 0
        }

        return (m[(m.size + 1) / 2] + m[(m.size + 2) / 2]) / 2

    }


    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val len1 = nums1.size
        val len2 = nums2.size
        val total = len1 + len2
        val left = (total + 1) / 2
        val right = (total + 2) / 2
        return (findK(nums1, 0, nums2, 0, left) + findK(nums1, 0, nums2, 0, right)) / 2.0
    }

    //找到两个数组中第k小的元素
    fun findK(nums1: IntArray, i: Int, nums2: IntArray, j: Int, k: Int): Int {
        if (i >= nums1.size) return nums2[j + k - 1]
        if (j >= nums2.size) return nums1[i + k - 1]
        if (k == 1) {
            return Math.min(nums1[i], nums2[j])
        }
        //计算出每次要比较的两个数的值，来决定 "删除"" 哪边的元素
        val mid1 = if (i + k / 2 - 1 < nums1.size) nums1[i + k / 2 - 1] else Int.MAX_VALUE
        val mid2 = if (j + k / 2 - 1 < nums2.size) nums2[j + k / 2 - 1] else Int.MAX_VALUE
        //通过递归的方式，来模拟删除掉前K/2个元素
        return if (mid1 < mid2) {
            findK(nums1, i + k / 2, nums2, j, k - k / 2)
        } else findK(nums1, i, nums2, j + k / 2, k - k / 2)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val median = Median()
            val result = median.findMedianSortedArrays(
                arrayOf(1, 3, 5, 7, 9).toIntArray(),
                arrayOf(6, 7, 8, 9, 10, 11).toIntArray()
            )
            println("result : $result")
        }
    }
}