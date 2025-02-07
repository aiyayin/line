package com.line.base.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.yin.line.base.databinding.LayoutActivityItemBinding
import com.yin.line.base.databinding.LayoutTextItemBinding
import com.line.base.entity.ActivityItem
import com.line.base.entity.TextItem

/**
 *
 * @author ying.fu
 * @date 2022/01/05
 * @desc
 * @tapd
 *
 */
abstract class RecyclerItemBinder<T, BD : ViewBinding>() :
    BaseMultiItemAdapter.OnMultiItemAdapterListener<T, QuickViewHolder> {

    private var binding: BD? = null

    override fun onBind(holder: QuickViewHolder, position: Int, item: T?) {
        item ?: return
        binding?.apply {
            convert(holder, this, item)
        }
    }

    override fun onCreate(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
        val viewBinding =
            onCreateViewBinding(LayoutInflater.from(parent.getContext()), parent, viewType)
        binding = viewBinding
        return QuickViewHolder(viewBinding.root)
    }

    abstract fun onCreateViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BD


    abstract fun convert(holder: QuickViewHolder, viewBinding: BD, data: T)
}

class ActivityItemBinder() : RecyclerItemBinder<ActivityItem, LayoutActivityItemBinding>() {


    override fun onCreateViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutActivityItemBinding {
        return LayoutActivityItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun convert(
        holder: QuickViewHolder,
        viewBinding: LayoutActivityItemBinding,
        data: ActivityItem
    ) {
        viewBinding.title.text = data.name
        viewBinding.icon.setImageResource(data.icon)
        holder.itemView.tag = "recyclerView${holder.adapterPosition}"
    }



}

class TextItemBinder() : RecyclerItemBinder<TextItem, LayoutTextItemBinding>() {


    override fun onCreateViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutTextItemBinding {
        return LayoutTextItemBinding.inflate(layoutInflater, parent, false)
    }

    override fun convert(
        holder: QuickViewHolder,
        viewBinding: LayoutTextItemBinding,
        data: TextItem
    ) {
       viewBinding.tvTitle.text = data.content
    }


}
