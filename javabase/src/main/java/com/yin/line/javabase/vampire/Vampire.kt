package line.javafoundation.vampire

import java.util.*

/**
 * Created by ying.fu.
 * Date: 2018/8/10.
 */
class Vampire {
    private var resultHelper: MutableList<Int> = ArrayList()
    fun printVampire() {
        for (t in 1001..9998) {
            val first = t / 1000
            val second = (t - first * 1000) / 100
            val third = (t - first * 1000 - second * 100) / 10
            val fourth = t - first * 1000 - second * 100 - third * 10
            val ints = LinkedList<Int>()
            ints.add(first)
            ints.add(second)
            ints.add(third)
            ints.add(fourth)
            for (j in 0..3) {
                for (i in j + 1..3) {
                    ints.clear()
                    ints.add(first)
                    ints.add(second)
                    ints.add(third)
                    ints.add(fourth)
                    val a = ints[i] * 10 + ints[j]
                    val b = ints[j] * 10 + ints[i]
                    ints.removeAt(i)
                    ints.removeAt(j)
                    val c = ints[0] * 10 + ints[1]
                    val d = ints[1] * 10 + ints[0]
                    printResult(a, c, t)
                    printResult(a, d, t)
                    printResult(b, c, t)
                    printResult(b, d, t)
                }
            }
        }
    }

    private fun printResult(a: Int, c: Int, t: Int) {
        if (a * c == t) {
            for (i in resultHelper) {
                if (i == a || i == c) return
            }
            print("printVampire:  $a * $c=$t\n")
            resultHelper.add(a)
            resultHelper.add(c)
        }
    }

    fun printVampire(numberLength: Int, divideLength: Int) {
        for (number in (Math.pow(10.0, numberLength - 1.toDouble()) + 1).toInt() until (Math.pow(10.0, numberLength.toDouble()) - 1).toInt()) {
            val chars = number.toString().toCharArray()
            combine(chars, divideLength, number)
        }
    }

    private fun combine(data: CharArray, divideLength: Int, number: Int) {
        val resultdata = ArrayList<String>()
        combineN(data, resultdata, 0)
        for (item in resultdata) {
            val chars = item.toCharArray()
            var ck = 0
            var cend = 0
            for (i in chars.indices) {
                if (i < divideLength) ck = (ck + (chars[i] - '0') * Math.pow(10.0, divideLength - i - 1.toDouble())).toInt() else cend = (cend + (chars[i] - '0') * Math.pow(10.0, chars.size - i - 1.toDouble())).toInt()
            }
            printResult(ck, cend, number)
        }
    }

    private fun combineN(data: CharArray, resultdata: ArrayList<String>, start: Int) {
        if (start >= data.size) return
        resultdata.add(String(data))
        for (i in start until data.size) {
            if (check(data, start, i)) {
                change(data, start, i)
                combineN(data, resultdata, start + 1)
                change(data, start, i)
            }
        }
    }

    private fun change(chs: CharArray, start: Int, i: Int) {
        val temp = chs[start]
        chs[start] = chs[i]
        chs[i] = temp
    }

    private fun check(chs: CharArray, start: Int, i: Int): Boolean {
        return chs[start] != chs[i]
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val vampire = Vampire()
            vampire.printVampire(4, 2)
        }
    }
}