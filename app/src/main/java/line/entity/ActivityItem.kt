package line.entity

import androidx.annotation.DrawableRes

data class ActivityItem(var name: String, @field:DrawableRes var icon: Int, var targetActivity: Class<*>)
data class WebViewItem(var name: String)