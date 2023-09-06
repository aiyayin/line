package com.line.demo.yu

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.SpannableString
import android.text.Spanned
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.line.base.BaseActivity
import com.yin.lin.demo.R


class ChartActivity : BaseActivity() {

    private val REQUEST_MANAGE_EXTERNAL_STORAGE_PERMISSION = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        val chat = findViewById<LineChartView>(R.id.chart)
        chat?.apply {
            pointList = mutableListOf(0f, 10f, 40.6f, 81f, 0f, 0f, 6f)
            labelX = mutableListOf("5.8", "5.9", "5.10", "5.11", "5.12", "5.13", "5.14")
        }
        val text = findViewById<StrokeTextView>(R.id.stroke_text)

//        requestManageExternalStoragePermission()
//
//        val projection = arrayOf(
//            MediaStore.Files.FileColumns._ID,
//            MediaStore.Files.FileColumns.DISPLAY_NAME,
//            MediaStore.Files.FileColumns.DATA
//        )
//
//
//        val selection = "${MediaStore.Files.FileColumns.DATA} like ? and ${MediaStore.Files.FileColumns.MIME_TYPE} = ?"
//        val selectionArgs = arrayOf("%/DCIM/MyFonts/%", "font/ttf")
//
//        val sortOrder = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"
//
//        val queryUri = MediaStore.Files.getContentUri("external")
//        contentResolver.query(queryUri, projection, selection, selectionArgs, sortOrder)?.use { cursor ->
//            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
//            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
//            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
//
//            while (cursor.moveToNext()) {
//                val id = cursor.getLong(idColumn)
//                val name = cursor.getString(nameColumn)
//                val path = cursor.getString(pathColumn)
//                if (path.contains("douyu")) {
//                    val typeface =Typeface.createFromFile(path)
//                    text.setStrokeTypeFace(typeface,false)
//                }
//            }
//        }
        text.setTextColor(Color.BLUE)
        val size = 56
        text.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            size.toFloat()
        )
        text.setPadding(20, 20-size, 20, 20-size)
//        text.setLineSpacing(-20f, 0.6f)
//        text.setLetterSpacing(2f)
        val content = "文边Tex1236"
        val content2 = "主播名字"
        text.setText(content2)

    }

    private fun requestManageExternalStoragePermission() {
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        intent.data = Uri.parse("package:" + packageName)

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_MANAGE_EXTERNAL_STORAGE_PERMISSION)
        } else {
            // 设备不支持 ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_MANAGE_EXTERNAL_STORAGE_PERMISSION) {
            if (Environment.isExternalStorageManager()) {
                // 已授予 MANAGE_EXTERNAL_STORAGE 权限
            } else {
                // 未授予 MANAGE_EXTERNAL_STORAGE 权限
            }
        }
    }
}