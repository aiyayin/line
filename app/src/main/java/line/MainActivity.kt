package line


//import line.opengl.panorama.GoogleVRActivity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yingfu.line.R
import line.entity.ActivityItem
import java.util.*

class MainActivity : BaseActivity(), View.OnClickListener {
    lateinit var activityItems: List<ActivityItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
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
        recyclerView.adapter = object : RecyclerView.Adapter<ActivityViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
                return ActivityViewHolder(
                    LayoutInflater.from(this@MainActivity)
                        .inflate(R.layout.main_activity_item, parent, false)
                )
            }

            override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
                val item = activityItems[position]
                holder.tvTitle.text = item.name
                holder.ivIcon.setImageResource(item.icon)
                holder.itemView.tag = "recyclerView$position"
                holder.itemView.setOnClickListener(this@MainActivity)
            }

            override fun getItemCount(): Int {
                return activityItems.size
            }
        }


    }

    override fun onClick(v: View) {
        if (v.tag is String) {
            val tag = v.tag as String
            val length = "recyclerView".length
            if (tag.contains("recyclerView") && tag.length > length) {
                try {
                    val position = Integer.parseInt(tag.substring(length, tag.length))
                    val item = activityItems[position]
                    val intent = Intent()
                    intent.setClass(this, item.targetActivity)
                    startActivity(intent)
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }

            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, results: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_ID) {
            if (results.isNotEmpty() && results[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    inner class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.title)
        var ivIcon: ImageView = itemView.findViewById(R.id.icon)

    }

    companion object {
        private const val READ_EXTERNAL_STORAGE_PERMISSION_ID = 1
    }


}
