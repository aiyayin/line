package line.view.recyclerview

import com.chad.library.adapter.base.BaseBinderAdapter

/**
 *
 * @author ying.fu
 * @date 2021/10/29
 * @desc
 * @tapd
 *
 */
class MainActivityAdapter(list: MutableList<Any>? = null) : BaseBinderAdapter(list) {

    init {
        addItemBinder(ActivityItemBinder())
        addItemBinder(WebViewItemBinder())
    }
}