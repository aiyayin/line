package line.javafoundation.file

import android.content.Context
import android.util.Log
import java.io.*


/**
 *
 * Created by ying.fu.
 * Date: 2019/1/14.
 */
class file {
    val name: String = "a.txt"
    fun readAssetsFile(context: Context): String? {
        try {
            var inputStream = context.assets.open(name)
            val stringBuffer = StringBuffer()
            var length = 0
            val buffer = ByteArray(1024)

            while ({
                        length = inputStream.read(buffer)
                        length
                    }() != -1) {
                stringBuffer.append(String(buffer, 0, length))
            }
            inputStream.close()
            return stringBuffer.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun readFile(context: Context): String? {
        try {
            var inputStream = FileInputStream(File(context.cacheDir, name))
            val stringBuffer = StringBuffer()
            var length = 0
            val buffer = ByteArray(1024)

            while ({
                        length = inputStream.read(buffer)
                        length
                    }() != -1) {
                stringBuffer.append(String(buffer, 0, length))
            }
            inputStream.close()
            return stringBuffer.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun writeFile(context: Context) {
        Log.e("ying>>>", "context.cacheDir : " + context.cacheDir)
        val cacheDir = context.cacheDir
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        val file = File(context.cacheDir, name)
        if (!file.exists()) {
            file.createNewFile()
        }
        val raf: RandomAccessFile = RandomAccessFile(file, "rw")
        try {
            //如果为追加则在原来的基础上继续写文件
            raf.seek(file.length())
            raf.write("是吧，是的！".toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
                raf.close()
        }

//        val s = readFile(context)
//        val writer = OutputStreamWriter(
//                FileOutputStream(file), "UTF-8")
//        writer.use()
//        {
//            val str = s + "是吧，是的！"
//            it.write(str)
//            it.flush ()
//        }
    }


}

