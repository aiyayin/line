package com.line.base.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseMultiItemAdapter

/**
 *
 * @author ying.fu
 * @date 2021/10/29
 * @desc
 * @tapd
 *
 */
class MainActivityAdapter(list: List<Any>) : BaseMultiItemAdapter<Any>(list) {

    init {
        addItemType(0, ActivityItemBinder() as OnMultiItemAdapterListener<Any, RecyclerView.ViewHolder>)
    }
}