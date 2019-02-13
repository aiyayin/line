package line.flutter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.yingfu.line.R
import io.flutter.facade.Flutter
//import io.flutter.plugin.common.MethodChannel
//import io.flutter.plugins.GeneratedPluginRegistrant

class MyFlutterActivity : AppCompatActivity() {
    //channel的名称，由于app中可能会有多个channel，这个名称需要在app内是唯一的。
    private val CHANNEL = "samples.flutter"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flutter)

        val flutterView = Flutter.createView(
                this@MyFlutterActivity, lifecycle,
                "route1"
        )
        val layout = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        addContentView(flutterView, layout)
    }

}
