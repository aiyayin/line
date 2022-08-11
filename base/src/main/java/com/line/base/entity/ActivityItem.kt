package com.line.base.entity

import androidx.annotation.DrawableRes

data class ActivityItem(var name: String, @field:DrawableRes var icon: Int, var targetActivity: Class<*>)
data class WebViewItem(var content: String)
data class TextItem(var content: String)




data class YapiEntityName(
	/**
	* 序号数
	*/
	var serialNumber : String? = null,
	/**
	* 阶段id
	*/
	var stageId : Int? = null,
	/**
	* 阶段名称
	*/
	var stageName : String? = null
)
