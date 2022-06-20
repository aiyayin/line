package line

import androidx.lifecycle.ViewModel
import com.example.yingfu.line.R
import com.yin.lin.demo.html.ArticleActivity
import com.yin.lin.demo.html.ArticleRecyclerActivity
import com.yin.lin.demo.html.ArticleWebActivity
import com.yin.lin.demo.html.WebActivity
import com.yin.lin.demo.opengl.SensorActivity
import com.yin.lin.demo.opengl.panorama.OpenGLActivity
import com.yin.lin.demo.opengl.panorama.video360.GoogleVideoActivity
import com.yin.line.base.entity.ActivityItem
import com.yin.line.javabase.tree.TreeActivity
import line.compose.TestActivity
import line.view.anim.AnimActivity
import line.view.bezier.BezierActivity
import line.view.bookpager.BookPageActivity
import line.view.line.IndexLineActivity
import line.view.scroller.ScrollViewActivity
import line.view.scroller.ScrollWebViewActivity
import line.view.svg.SVGActivity
import line.view.viewpager.NestedViewPagerActivity
import line.view.viewpager.ViewPagerActivity

/**
 * @author ying.fu
 * @date 2021/08/12
 * @desc
 * @tapd
 */
class MainViewModel : ViewModel() {
    fun getItemList(): MutableList<Any> {
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
            ActivityItem(
                "NestedViewPager",
                R.drawable.ic_list, NestedViewPagerActivity::class.java
            ),
//            ActivityItem(
//                "Flutter",
//                R.drawable.ic_flutter, MyFlutterActivity::class.java
//            ),
            ActivityItem("OpenGL", R.drawable.ic_vr, OpenGLActivity::class.java),
            ActivityItem("GoogleVideo", R.drawable.ic_vr, GoogleVideoActivity::class.java),
            ActivityItem("BookPage", R.drawable.ic_book, BookPageActivity::class.java),
            ActivityItem("Animation", R.drawable.ic_wave, AnimActivity::class.java),
            ActivityItem("Article", R.drawable.ic_book, ArticleActivity::class.java),
            ActivityItem(
                "ArticleRecycler",
                R.drawable.ic_book,
                ArticleRecyclerActivity::class.java
            ),
            ActivityItem("ArticleWeb", R.drawable.ic_book, ArticleWebActivity::class.java),
            ActivityItem("Web", R.drawable.ic_book, WebActivity::class.java),
            ActivityItem("ScrollWeb", R.drawable.ic_book, ScrollWebViewActivity::class.java),
            ActivityItem("compose", R.drawable.ic_book, TestActivity::class.java),
            ActivityItem("Sensor3D", R.drawable.ic_vr, SensorActivity::class.java)
        )
    }
}