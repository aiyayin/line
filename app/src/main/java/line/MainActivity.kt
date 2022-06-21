package line


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Process
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yingfu.line.R
import com.yin.line.base.BaseActivity
import com.yin.line.base.entity.ActivityItem
import com.yin.line.base.recyclerview.MainActivityAdapter


class MainActivity : BaseActivity() {
    lateinit var activityItems: MutableList<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val pid = Process.myPid()
        Log.d("yin>>", "MainActivity 当前进程ID为：$pid")

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_PERMISSION_ID
            )
        }
        val mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        activityItems = mainViewModel.getItemList()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        val mainActivityAdapter = MainActivityAdapter(activityItems)
        mainActivityAdapter.setOnItemClickListener { adapter, view, position ->
            if (view.tag is String) {
                val tag = view.tag as String
                val length = "recyclerView".length
                if (tag.contains("recyclerView") && tag.length > length) {
                    try {
                        val position = Integer.parseInt(tag.substring(length, tag.length))
                        val item = activityItems[position]
                        if (item is ActivityItem) {
                            val intent = Intent()
                            intent.setClass(this@MainActivity, item.targetActivity)
                            startActivity(intent)
                        }

                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                    }

                }
            }
        }

        recyclerView.adapter = mainActivityAdapter
        val wifiManger: WifiManager? = ContextCompat.getSystemService(
            this,
            WifiManager::class.java
        )

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_ID) {
            if (results.isNotEmpty() && results[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }


    companion object {
        private const val READ_EXTERNAL_STORAGE_PERMISSION_ID = 1
    }


}


