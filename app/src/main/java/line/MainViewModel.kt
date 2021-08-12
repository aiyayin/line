package line

import androidx.lifecycle.ViewModel
import com.example.yingfu.line.R
import line.entity.ActivityItem
import line.javafoundation.tree.TreeActivity
import line.opengl.panorama.OpenGLActivity
import line.opengl.panorama.video360.GoogleVideoActivity
import line.view.anim.AnimActivity
import line.view.bezier.BezierActivity
import line.view.bookpager.BookPageActivity
import line.view.line.IndexLineActivity
import line.view.scroller.ScrollViewActivity
import line.view.svg.SVGActivity
import line.view.viewpager.ViewPagerActivity

/**
 * @author ying.fu
 * @date 2021/08/12
 * @desc
 * @tapd
 */
class MainViewModel : ViewModel() {
    fun getItemList(): MutableList<ActivityItem> {
        return mutableListOf(
            ActivityItem("Line", R.drawable.ic_line, IndexLineActivity::class.java),
            ActivityItem("ScrollView", R.drawable.ic_list, ScrollViewActivity::class.java),
            ActivityItem("Bezier", R.drawable.ic_wave, BezierActivity::class.java),
            ActivityItem("Tree", R.drawable.ic_tree, TreeActivity::class.java),
            ActivityItem("SVG", R.drawable.ic_line, SVGActivity::class.java),
            ActivityItem(
                "ViewPager",
                R.drawable.ic_list, ViewPagerActivity::class.java
            ),
//            ActivityItem(
//                "Flutter",
//                R.drawable.ic_flutter, MyFlutterActivity::class.java
//            ),
            ActivityItem("OpenGL", R.drawable.ic_vr, OpenGLActivity::class.java),
            ActivityItem("GoogleVideo", R.drawable.ic_vr, GoogleVideoActivity::class.java),
            ActivityItem("BookPage", R.drawable.ic_book, BookPageActivity::class.java),
            ActivityItem("Animation", R.drawable.ic_wave, AnimActivity::class.java)
        )
    }
}