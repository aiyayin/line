package line


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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yingfu.line.R
import line.entity.ActivityItem
import line.flutter.MyFlutterActivity
import line.javafoundation.tree.TreeActivity
//import line.opengl.panorama.GoogleVRActivity
import line.opengl.panorama.OpenGLActivity
import line.opengl.panorama.video360.GoogleVideoActivity
import line.view.anim.AnimActivity
import line.view.bezier.BezierActivity
import line.view.bookpager.BookPageActivity
import line.view.line.IndexLineActivity
import line.view.scroller.ScrollViewActivity
import line.view.svg.SVGActivity
import line.view.viewpager.ViewPagerActivity
import java.util.*

class MainActivity : BaseActivity(), View.OnClickListener {
    var activityItems: ArrayList<ActivityItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE_PERMISSION_ID)
        }
        activityItems.add(ActivityItem("Line", R.drawable.ic_line, IndexLineActivity::class.java))
        activityItems.add(ActivityItem("ScrollView", R.drawable.ic_list, ScrollViewActivity::class.java))
        activityItems.add(ActivityItem("Bezier", R.drawable.ic_wave, BezierActivity::class.java))
        activityItems.add(ActivityItem("Tree", R.drawable.ic_tree, TreeActivity::class.java))
        activityItems.add(ActivityItem("SVG", R.drawable.ic_line, SVGActivity::class.java))
        activityItems.add(ActivityItem("ViewPager",
                R.drawable.ic_list, ViewPagerActivity::class.java))
        activityItems.add(ActivityItem("Flutter",
                R.drawable.ic_flutter, MyFlutterActivity::class.java))
        activityItems.add(ActivityItem("OpenGL", R.drawable.ic_vr, OpenGLActivity::class.java))
//        activityItems.add(ActivityItem("GoogleVR", R.drawable.ic_vr, GoogleVRActivity::class.java))
        activityItems.add(ActivityItem("GoogleVideo", R.drawable.ic_vr, GoogleVideoActivity::class.java))
        activityItems.add(ActivityItem("BookPage", R.drawable.ic_book, BookPageActivity::class.java))
        activityItems.add(ActivityItem("Animation", R.drawable.ic_wave, AnimActivity::class.java))

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = object : RecyclerView.Adapter<ActivityViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
                return ActivityViewHolder(LayoutInflater.from(this@MainActivity).inflate(R.layout.main_activity_item, parent, false))
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
