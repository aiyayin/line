//package line.flutter
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import android.util.Log
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import com.example.backstack.BackstackPlugin
//import com.example.yingfu.line.R
//import io.flutter.facade.Flutter
//import line.BaseActivity
//
////import io.flutter.plugin.common.MethodChannel
////import io.flutter.plugins.GeneratedPluginRegistrant
//
//class MyFlutterActivity : BaseActivity() {
//    //channel的名称，由于app中可能会有多个channel，这个名称需要在app内是唯一的。
//    private val CHANNEL = "samples.flutter"
//    val flutterView=null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_flutter)
//        Log.e("ying>>>","MyFlutterActivity onCreate after setContentView")
//
//        val flutterView = Flutter.createView(
//                this@MyFlutterActivity, lifecycle,
//                "route1"
//        )
//        val layout = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        addContentView(flutterView, layout)
//        Log.e("ying>>>","MyFlutterActivity onCreate after addContentView")
//    }
//
//    override fun onBackPressed() {
//        Log.e("ying>>>>flutter", "onBackPressed:   routeCounter ::  " + BackstackPlugin.routeCounter)
//        if (BackstackPlugin.routeCounter > 0) {
//            BackstackPlugin.routeCounter--;
//            BackstackPlugin.channel.invokeMethod("back",null)
//        } else super.onBackPressed()
//    }
//}
