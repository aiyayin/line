package line.view.line

import com.example.yingfu.line.R
import java.util.*

/**
 * @author YING.FU
 * date: 2018-05-31
 */
class IndexTop {
    private val mTopItems: MutableList<IndexTopItem> = ArrayList()
    val topItems: List<IndexTopItem>
        get() {
            if (mTopItems.size == 0) {
                for (i in 0..7) {
                    val item = IndexTopItem()
                    item.name = "第" + (i + 1) + "个"
                    item.imgDrawable = R.drawable.index_top_company
                    mTopItems.add(item)
                }
            }
            return mTopItems
        }

    inner class IndexTopItem {
        var imgDrawable = 0
        var name: String? = null

    }
}