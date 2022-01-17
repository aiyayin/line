package com.yin.lin.demo.html

import android.os.Bundle
import com.yin.lin.demo.databinding.LayoutWebviewItemBinding
import com.yin.line.base.BaseActivity

class WebActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = LayoutWebviewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webView.loadUrl("https://mapp.to8to.com/wap/t8tapp/newMcnArticle?id=229475&t8t_screen_full=true&t8t_statusbar_show_when_fullscreen=true")

    }


}