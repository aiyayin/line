package line.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.binder.QuickViewBindingItemBinder
import com.example.yingfu.line.databinding.LayoutActivityItemBinding
import com.example.yingfu.line.databinding.LayoutWebviewItemBinding
import line.entity.ActivityItem
import line.entity.WebViewItem

/**
 *
 * @author ying.fu
 * @date 2021/10/29
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

    override fun convert(holder: BinderVBHolder<LayoutActivityItemBinding>, data: ActivityItem) {
        holder.viewBinding.title.text = data.name
        holder.viewBinding.icon.setImageResource(data.icon)
        holder.itemView.tag = "recyclerView${holder.adapterPosition}"
    }




}

class WebViewItemBinder() : RecyclerItemBinder<WebViewItem, LayoutWebviewItemBinding>() {
    override fun convert(holder: BinderVBHolder<LayoutWebviewItemBinding>, data: WebViewItem) {
        holder.viewBinding.webView.loadUrl("file:///android_asset/article/article3.html")

    }

    override fun onCreateViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LayoutWebviewItemBinding {
        return LayoutWebviewItemBinding.inflate(layoutInflater, parent, false)
    }

}