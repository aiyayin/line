package line.html

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yingfu.line.databinding.LayoutWebviewItemBinding

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = LayoutWebviewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webView.loadUrl("https://mapp.to8to.com/wap/t8tapp/newMcnArticle?id=229475&t8t_screen_full=true&t8t_statusbar_show_when_fullscreen=true")

    }


}