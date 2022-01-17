package com.yin.line.base.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.yin.line.base.databinding.LayoutActivityItemBinding
import com.yin.line.base.entity.ActivityItem

/**
 *
 * @author ying.fu
 * @date 2022/01/05
 * @desc
 * @tapd
 *
 */
    abstract class RecyclerItemBinder<T, BD : ViewBinding>() :
        QuickViewBindingItemBinder<T, BD>() {
    }

    class ActivityItemBinder() : RecyclerItemBinder<ActivityItem, LayoutActivityItemBinding>() {


        override fun onCreateViewBinding(
            layoutInflater: LayoutInflater,
            parent: ViewGroup,
            viewType: Int
        ): LayoutActivityItemBinding {
            return LayoutActivityItemBinding.inflate(layoutInflater, parent, false)
        }

        override fun convert(holder: QuickViewBindingItemBinder.BinderVBHolder<LayoutActivityItemBinding>, data: ActivityItem) {
            holder.viewBinding.title.text = data.name
            holder.viewBinding.icon.setImageResource(data.icon)
            holder.itemView.tag = "recyclerView${holder.adapterPosition}"
        }




    }
