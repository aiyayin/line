package line.javafoundation.bitmask

import java.util.*
import kotlin.math.pow

/**
 * Created by ying.fu.
 * Date: 2018/8/16.
 */
class BitMask1 {
    //    题目大意：给出一个T， 和一个下限L, 上限R， 在[L, R]之间找一个数， 使得这个数与T做或运算之后的数值最大 输出这个数。
    private fun a(t: Int, l: Int, r: Int) {
        var max = l
        var maxi = t
        for (i in l..r) {
            val b = t or i
            print(" b: $b t: $t i: $i\n")
            if (max < b) {
                max = b
                maxi = i
            }
        }
        print("max: $max i: $maxi\n")
    }

    //贪心
    private fun b(t: Int, l: Int, r: Int) {
        val t_int = ArrayList<Int>()
        intToBinary(t, t_int)
        val r_int = ArrayList<Int>()
        intToBinary(r, r_int)
        getMax(t_int, r_int, l, r)
    }

    /**
     * 关键算法
     *
     * @param t_int
     * @param r_int
     * @param l
     * @param r
     */
    private fun getMax(t_int: ArrayList<Int>, r_int: ArrayList<Int>, l: Int, r: Int) {
        val result = ArrayList<Int>()
        result.addAll(r_int)
        /**
         * 依次取最高位 置为t相应位置相反的数(0,1)
         */
        for (s in r_int.indices.reversed()) {
            var t = 0
            if (s < t_int.size) t = t_int[s]
            result[s] = t xor 1
            result.toTypedArray()
            //            System.out.println(result);
            val k = binaryToInt(result)
            if (k < l || k > r) {
                /**
                 * 若置换该位置不在范围内 复原
                 */
                result[s] = r_int[s]
            }
        }
        print("""
    getMax result: ${binaryToInt(result)}
    """.trimIndent())
    }

    /**
     * int转二进制
     *
     * @param i
     * @param i_int
     */
    private fun intToBinary(i: Int, i_int: ArrayList<Int>) {
        var i = i
        while (i >= 1) {
            i_int.add(i % 2)
            i /= 2
        }
    }

    private fun binaryToInt(t_int: ArrayList<Int>): Int {
        var result = t_int[0]
        for (i in 1 until t_int.size) {
            result = (result + t_int[i] * 2.0.pow(i.toDouble())).toInt()
        }
        return result
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val bitMask1 = BitMask1()
            bitMask1.b(100, 50, 60) //59
            bitMask1.b(100, 50, 50) //50
            bitMask1.b(100, 0, 100) //27
            bitMask1.b(1, 0, 100) //100
            bitMask1.b(15, 1, 15) //1
        }
    }
}