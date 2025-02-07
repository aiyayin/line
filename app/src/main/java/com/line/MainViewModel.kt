package com.line

import androidx.lifecycle.ViewModel
import com.example.yingfu.line.R
import com.line.base.entity.ActivityItem
import com.line.compose.TestComposeActivity
import com.line.demo.yu.chart.ChartActivity
import com.line.demo.yu.round.ImageTestActivity
import com.line.demo.yu.scale.ScaleActivity
import com.line.demo.yu.segment.SegmentActivity
import com.line.demo.yu.segment.MattingActivity
import com.line.view.line.IndexLineActivity
import com.line.view.viewpager.ViewPagerActivity

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
//            ActivityItem("ScrollView", R.drawable.ic_list, ScrollViewActivity::class.java),
//            ActivityItem("Bezier", R.drawable.ic_wave, BezierActivity::class.java),
//            ActivityItem("Tree", R.drawable.ic_tree, TreeActivity::class.java),
//            ActivityItem("SVG", R.drawable.ic_line, SVGActivity::class.java),
//            ActivityItem(
//                "ViewPager",
//                R.drawable.ic_list, ViewPagerActivity::class.java
//            ),
//            ActivityItem(
//                "NestedViewPager",
//                R.drawable.ic_list, NestedViewPagerActivity::class.java
//            ),
////            ActivityItem(
////                "Flutter",
////                R.drawable.ic_flutter, MyFlutterActivity::class.java
////            ),
//            ActivityItem("OpenGL", R.drawable.ic_vr, OpenGLActivity::class.java),
//            ActivityItem("GoogleVideo", R.drawable.ic_vr, GoogleVideoActivity::class.java),
//            ActivityItem("BookPage", R.drawable.ic_book, BookPageActivity::class.java),
//            ActivityItem("Animation", R.drawable.ic_wave, AnimActivity::class.java),
//            ActivityItem("Article", R.drawable.ic_book, ArticleActivity::class.java),
//            ActivityItem(
//                "ArticleRecycler",
//                R.drawable.ic_book,
//                ArticleRecyclerActivity::class.java
//            ),
//            ActivityItem("ArticleWeb", R.drawable.ic_book, ArticleWebActivity::class.java),
//            ActivityItem("Web", R.drawable.ic_book, WebActivity::class.java),
//            ActivityItem("ScrollWeb", R.drawable.ic_book, ScrollWebViewActivity::class.java),
            ActivityItem("compose", R.drawable.ic_book, TestComposeActivity::class.java),
//            ActivityItem("Sensor3D", R.drawable.ic_vr, SensorActivity::class.java),
//            ActivityItem("AIDL", R.drawable.ic_line, MainAIDLActivity::class.java),
            ActivityItem("Chart", R.drawable.ic_list, ChartActivity::class.java),
            ActivityItem("Matting", R.drawable.ic_list, MattingActivity::class.java),
            ActivityItem("Image", R.drawable.ic_list, ImageTestActivity::class.java),
            ActivityItem("Image2", R.drawable.ic_list, SegmentActivity::class.java),
            ActivityItem("viewpager", R.drawable.ic_list,ViewPagerActivity::class.java),
            ActivityItem("scale", R.drawable.ic_list, ScaleActivity::class.java),
        )
    }
}