package com.line.demo.yu

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import android.text.TextUtils
import android.util.Log
import com.line.base.util.ToolUtil
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.io.*
import java.lang.RuntimeException
import javax.xml.parsers.SAXParserFactory


/**
 *
 * @description
 * @author fuyi
 * @date 2023/9/13
 */
class PathUtil {
    companion object {
        val instance by lazy {
            PathUtil()
        }
    }

    var path = Path()
    var maxPath = Path()
    var width = 100f
    var height = 100f
    private var tempPathList = ArrayList<String>()

    // 获取路径的边界矩形
    var bounds = RectF()

    // 创建自定义的处理程序
    private val mSVGHandler by lazy {
        object : DefaultHandler() {

            @Throws(SAXException::class)
            override fun startElement(
                uri: String,
                localName: String,
                qName: String,
                attributes: Attributes,
            ) {
                if (qName.equals("path", ignoreCase = true)) {
                    val pathData = attributes.getValue("d")
                    println(pathData)
                    if (!TextUtils.isEmpty(pathData)) {
                        tempPathList.add(pathData)
                    }
                } else if (qName.equals("svg", ignoreCase = true)) {

                    val viewBox = attributes.getValue("viewBox")
                    if (viewBox != null) {
                        // Prefer viewBox
                        val coords = viewBox.split(",")
                        if (coords.size == 4) {
                            width = coords[2].toFloatOrNull() ?: -1f
                            height = coords[3].toFloatOrNull() ?: -1f
                        }
                    } else {
                        width = attributes.getValue("height").toFloatOrNull() ?: -1f
                        height = attributes.getValue("width").toFloatOrNull() ?: -1f
                    }
                    if (width < 0 || height < 0) {
                        width = 100f
                        height = 100f
                        Log.w(DYSVGParser.TAG,
                            "element '" + localName + "' does not provide its dimensions; using " + width + "x" + height)
                    }
                    Log.d(DYSVGParser.TAG,
                        "element $localName width:  $width height : $height")
                }


            }

            @Throws(SAXException::class)
            override fun endElement(uri: String, localName: String, qName: String) {
            }
        }
    }

    fun parse(context: Context) {
        parse(context, "output2.svg")
    }

    fun parse(context: Context, fileName: String) {

        try {
            // 解析SVG文件
            val inputStream = getInputStream(context, fileName) ?: return
            // 创建SAXParser实例
            val factory = SAXParserFactory.newInstance()
            val saxParser = factory.newSAXParser()

            saxParser.parse(inputStream, mSVGHandler)
            var maxPathStr = ""
            tempPathList.forEach {

                val path = DYSVGParser.parsePath(it)
                val mMatrix = Matrix()
                // 设置缩放比例：2倍缩放
                val radio = 1.0f * ToolUtil.getScreenWidth(context) / 857
                mMatrix.setScale(radio, radio)
                // 对Path对象应用缩放变换
                path.transform(mMatrix)
                path.computeBounds(bounds, true)
                if (it.length > maxPathStr.length) {
                    maxPathStr = it
                    maxPath = path
                }
                this.path.addPath(path)
            }
            Log.d(DYSVGParser.TAG,
                "parse svg end")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(DYSVGParser.TAG,
                "parse svg error  ${e.message}")
        }
    }

    private fun getInputStream(context: Context, svgFile: String): InputStream? {
        try {
            // 获取AssetManager实例
            val assetManager: AssetManager = context.assets

            // 打开文件输入流
            return assetManager.open(svgFile)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d(DYSVGParser.TAG,
                "parse svg error  ${e.message}")
        }
        return null
    }


}

/**
 * SVG解析类，Sharp里面扣出来的稍微改了改
 * https://github.com/Pixplicity/sharp
 */
object DYSVGParser {
    const val TAG = "svg"

    /**
     * Parses a single SVG path and returns it as a `android.graphics.Path` object.
     * An example path is `M250,150L150,350L350,350Z`, which draws a triangle.
     *
     * @param pathString the SVG path, see the specification [here](http://www.w3.org/TR/SVG/paths.html).
     */
    fun parsePath(pathString: String): Path {
        return doPath(pathString)
    }

    /**
     * This is where the hard-to-parse paths are handled.
     * Uppercase rules are absolute positions, lowercase are relative.
     * Types of path rules:
     *
     *
     *
     *  1. M/m - (x y)+ - Move to (without drawing)
     *  1. Z/z - (no params) - Close path (back to starting point)
     *  1. L/l - (x y)+ - Line to
     *  1. H/h - x+ - Horizontal ine to
     *  1. V/v - y+ - Vertical line to
     *  1. C/c - (x1 y1 x2 y2 x y)+ - Cubic bezier to
     *  1. S/s - (x2 y2 x y)+ - Smooth cubic bezier to (shorthand that assumes the x2, y2 from previous C/S is the x1, y1 of this bezier)
     *  1. Q/q - (x1 y1 x y)+ - Quadratic bezier to
     *  1. T/t - (x y)+ - Smooth quadratic bezier to (assumes previous control point is "reflection" of last one w.r.t. to current point)
     *
     *
     *
     * Numbers are separate by whitespace, comma or nothing at all (!) if they are self-delimiting, (ie. begin with a - sign)
     *
     * @param s the path string from the XML
     */
    private fun doPath(s: String): Path {
        val n = s.length
        val ph = DYParserHelper(s, 0)
        ph.skipWhitespace()
        val p = Path()
        var lastX = 0f
        var lastY = 0f
        var lastX1 = 0f
        var lastY1 = 0f
        var subPathStartX = 0f
        var subPathStartY = 0f
        var prevCmd = 0.toChar()
        while (ph.pos < n) {
            var cmd = s[ph.pos]
            when (cmd) {
                '-', '+', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                    if (prevCmd == 'm' || prevCmd == 'M') {
                        cmd = (prevCmd.code - 1).toChar()
                        break
                    } else if (prevCmd == 'c' || prevCmd == 'C') {
                        cmd = prevCmd
                        break
                    } else if (prevCmd == 'l' || prevCmd == 'L') {
                        cmd = prevCmd
                        break
                    }
                    run {
                        ph.advance()
                        prevCmd = cmd
                    }
                }
                else -> {
                    ph.advance()
                    prevCmd = cmd
                }
            }
            var wasCurve = false
            when (cmd) {
                'M', 'm' -> {
                    val x = ph.nextFloat()
                    val y = ph.nextFloat()
                    if (cmd == 'm') {
                        subPathStartX += x
                        subPathStartY += y
                        p.rMoveTo(x, y)
                        lastX += x
                        lastY += y
                    } else {
                        subPathStartX = x
                        subPathStartY = y
                        p.moveTo(x, y)
                        lastX = x
                        lastY = y
                    }
                }
                'Z', 'z' -> {
                    p.close()
                    p.moveTo(subPathStartX, subPathStartY)
                    lastX = subPathStartX
                    lastY = subPathStartY
                    lastX1 = subPathStartX
                    lastY1 = subPathStartY
                    wasCurve = true
                }
                'L', 'l' -> {
                    val x = ph.nextFloat()
                    val y = ph.nextFloat()
                    if (cmd == 'l') {
                        p.rLineTo(x, y)
                        lastX += x
                        lastY += y
                    } else {
                        p.lineTo(x, y)
                        lastX = x
                        lastY = y
                    }
                }
                'H', 'h' -> {
                    val x = ph.nextFloat()
                    if (cmd == 'h') {
                        p.rLineTo(x, 0f)
                        lastX += x
                    } else {
                        p.lineTo(x, lastY)
                        lastX = x
                    }
                }
                'V', 'v' -> {
                    val y = ph.nextFloat()
                    if (cmd == 'v') {
                        p.rLineTo(0f, y)
                        lastY += y
                    } else {
                        p.lineTo(lastX, y)
                        lastY = y
                    }
                }
                'C', 'c' -> {
                    wasCurve = true
                    var x1 = ph.nextFloat()
                    var y1 = ph.nextFloat()
                    var x2 = ph.nextFloat()
                    var y2 = ph.nextFloat()
                    var x = ph.nextFloat()
                    var y = ph.nextFloat()
                    if (cmd == 'c') {
                        x1 += lastX
                        x2 += lastX
                        x += lastX
                        y1 += lastY
                        y2 += lastY
                        y += lastY
                    }
                    p.cubicTo(x1, y1, x2, y2, x, y)
                    lastX1 = x2
                    lastY1 = y2
                    lastX = x
                    lastY = y
                }
                'S', 's' -> {
                    wasCurve = true
                    var x2 = ph.nextFloat()
                    var y2 = ph.nextFloat()
                    var x = ph.nextFloat()
                    var y = ph.nextFloat()
                    if (cmd == 's') {
                        x2 += lastX
                        x += lastX
                        y2 += lastY
                        y += lastY
                    }
                    val x1 = 2 * lastX - lastX1
                    val y1 = 2 * lastY - lastY1
                    p.cubicTo(x1, y1, x2, y2, x, y)
                    lastX1 = x2
                    lastY1 = y2
                    lastX = x
                    lastY = y
                }
                'A', 'a' -> {
                    val rx = ph.nextFloat()
                    val ry = ph.nextFloat()
                    val theta = ph.nextFloat()
                    val largeArc = ph.nextFloat().toInt()
                    val sweepArc = ph.nextFloat().toInt()
                    val x = ph.nextFloat()
                    val y = ph.nextFloat()
                    lastX = x
                    lastY = y
                }
            }
            if (!wasCurve) {
                lastX1 = lastX
                lastY1 = lastY
            }
            ph.skipWhitespace()
        }
        return p
    }
}


/**
 * https://github.com/Pixplicity/sharp
 */
internal class DYParserHelper(private val s: CharSequence, var pos: Int) {
    private var current: Char
    private val n: Int = s.length
    private fun read(): Char {
        if (pos < n) {
            pos++
        }
        return if (pos == n) {
            '\u0000'
        } else {
            s[pos]
        }
    }

    fun skipWhitespace() {
        while (pos < n) {
            if (Character.isWhitespace(s[pos])) {
                advance()
            } else {
                break
            }
        }
    }

    fun skipNumberSeparator() {
        while (pos < n) {
            val c = s[pos]
            when (c) {
                ' ', ',', '\n', '\t' -> advance()
                else -> return
            }
        }
    }

    fun advance() {
        current = read()
    }

    /**
     * Parses the content of the buffer and converts it to a float.
     */
    fun parseFloat(): Float {
        var mant = 0
        var mantDig = 0
        var mantPos = true
        var mantRead = false
        var exp = 0
        var expDig = 0
        var expAdj = 0
        var expPos = true
        when (current) {
            '-' -> {
                mantPos = false
                current = read()
            }
            '+' -> current = read()
        }
        when (current) {
            '.' -> {}
            '0' -> {
                mantRead = true
                l@ while (true) {
                    current = read()
                    when (current) {
                        '1', '2', '3', '4', '5', '6', '7', '8', '9' -> break@l
                        '.', 'e', 'E' -> break
                        '0' -> {}
                        else -> return 0.0f
                    }
                }
                mantRead = true
                l@ while (true) {
                    if (mantDig < 9) {
                        mantDig++
                        mant = mant * 10 + (current - '0')
                    } else {
                        expAdj++
                    }
                    current = read()
                    when (current) {
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {}
                        else -> break@l
                    }
                }
            }
            '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                mantRead = true
                l@ while (true) {
                    if (mantDig < 9) {
                        mantDig++
                        mant = mant * 10 + (current - '0')
                    } else {
                        expAdj++
                    }
                    current = read()
                    when (current) {
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {}
                        else -> break@l
                    }
                }
            }
            else -> return Float.NaN
        }
        if (current == '.') {
            current = read()
             when (current) {
                'e', 'E' -> if (!mantRead) {
                    reportUnexpectedCharacterError(current)
                    return 0.0f
                }
                '0' -> {
                    if (mantDig == 0) {
                        l@ while (true) {
                            current = read()
                            expAdj--
                            when (current) {
                                '1', '2', '3', '4', '5', '6', '7', '8', '9' -> break@l
                                '0' -> {}
                                else -> {
                                    if (!mantRead) {
                                        return 0.0f
                                    }
                                    break
                                }
                            }
                        }
                    }
                    l@ while (true) {
                        if (mantDig < 9) {
                            mantDig++
                            mant = mant * 10 + (current - '0')
                            expAdj--
                        }
                        current = read()
                        when (current) {
                            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {}
                            else -> break@l
                        }
                    }
                }
                '1', '2', '3', '4', '5', '6', '7', '8', '9' -> l@ while (true) {
                    if (mantDig < 9) {
                        mantDig++
                        mant = mant * 10 + (current - '0')
                        expAdj--
                    }
                    current = read()
                    when (current) {
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {}
                        else -> break@l
                    }
                }
                else -> if (!mantRead) {
                    reportUnexpectedCharacterError(current)
                    return 0.0f
                }
            }
        }
        when (current) {
            'e', 'E' -> {
                current = read()
                when (current) {
                    '-' -> {
                        expPos = false
                        current = read()
                        when (current) {
                            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {}
                            else -> {
                                reportUnexpectedCharacterError(current)
                                return 0f
                            }
                        }
                    }
                    '+' -> {
                        current = read()
                        when (current) {
                            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {}
                            else -> {
                                reportUnexpectedCharacterError(current)
                                return 0f
                            }
                        }
                    }
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {}
                    else -> {
                        reportUnexpectedCharacterError(current)
                        return 0f
                    }
                }
                 when (current) {
                    '0' -> {
                        l@ while (true) {
                            current = read()
                            when (current) {
                                '1', '2', '3', '4', '5', '6', '7', '8', '9' -> break@l
                                '0' -> {}
                                else -> break
                            }
                        }
                        l@ while (true) {
                            if (expDig < 3) {
                                expDig++
                                exp = exp * 10 + (current - '0')
                            }
                            current = read()
                            when (current) {
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {}
                                else -> break@l
                            }
                        }
                    }
                    '1', '2', '3', '4', '5', '6', '7', '8', '9' -> l@ while (true) {
                        if (expDig < 3) {
                            expDig++
                            exp = exp * 10 + (current - '0')
                        }
                        current = read()
                        when (current) {
                            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {}
                            else -> break@l
                        }
                    }
                }
            }
            else -> {}
        }
        if (!expPos) {
            exp = -exp
        }
        exp += expAdj
        if (!mantPos) {
            mant = -mant
        }
        return buildFloat(mant, exp)
    }

    private fun reportUnexpectedCharacterError(c: Char) {
        throw RuntimeException("Unexpected char '$c'.")
    }

    init {
        current = s[pos]
    }

    fun nextFloat(): Float {
        skipWhitespace()
        val f = parseFloat()
        skipNumberSeparator()
        return f
    }

    companion object {
        /**
         * Computes a float from mantissa and exponent.
         */
        fun buildFloat(mant: Int, exp: Int): Float {
            var mant = mant
            if (exp < -125 || mant == 0) {
                return 0.0f
            }
            if (exp >= 128) {
                return if (mant > 0) Float.POSITIVE_INFINITY else Float.NEGATIVE_INFINITY
            }
            if (exp == 0) {
                return mant.toFloat()
            }
            if (mant >= 1 shl 26) {
                mant++ // round up trailing bits if they will be dropped.
            }
            return (if (exp > 0) mant * pow10[exp] else mant / pow10[-exp]).toFloat()
        }

        /**
         * Array of powers of ten. Using double instead of float gives a tiny bit more precision.
         */
        private val pow10 = DoubleArray(128)

        init {
            for (i in pow10.indices) {
                pow10[i] = Math.pow(10.0, i.toDouble())
            }
        }
    }
}