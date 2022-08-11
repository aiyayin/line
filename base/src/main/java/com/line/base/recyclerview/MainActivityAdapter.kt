package com.line.base.recyclerview

import com.chad.library.adapter.base.BaseBinderAdapter
import com.line.base.entity.ActivityItem

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
        addItemBinder(ActivityItem::class.java, ActivityItemBinder())
    }
}