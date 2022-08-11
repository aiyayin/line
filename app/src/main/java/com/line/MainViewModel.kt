package com.line

import androidx.lifecycle.ViewModel
import com.example.yingfu.line.R
import com.line.binder.MainAIDLActivity
import com.line.demo.html.ArticleActivity
import com.line.demo.html.ArticleRecyclerActivity
import com.line.demo.html.ArticleWebActivity
import com.line.demo.html.WebActivity
import com.line.demo.opengl.SensorActivity
import com.line.demo.opengl.panorama.OpenGLActivity
import com.line.demo.opengl.panorama.video360.GoogleVideoActivity
import com.line.base.entity.ActivityItem
import com.line.compose.TestComposeActivity
import com.line.java.TreeActivity
import com.line.view.anim.AnimActivity
import com.line.view.bezier.BezierActivity
import com.line.view.bookpager.BookPageActivity
import com.line.view.line.IndexLineActivity
import com.line.view.scroller.ScrollViewActivity
import com.line.view.scroller.ScrollWebViewActivity
import com.line.view.svg.SVGActivity
import com.line.view.viewpager.NestedViewPagerActivity
import com.line.view.viewpager.ViewPagerActivity

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
            ActivityItem("compose", R.drawable.ic_book, TestComposeActivity::class.java),
            ActivityItem("Sensor3D", R.drawable.ic_vr, SensorActivity::class.java),
            ActivityItem("AIDL", R.drawable.ic_line, MainAIDLActivity::class.java)
        )
    }
}