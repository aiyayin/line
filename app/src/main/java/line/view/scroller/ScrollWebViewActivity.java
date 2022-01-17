package line.view.scroller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.example.yingfu.line.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.yin.line.base.BaseActivity;

public class ScrollWebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        WebView webView = (WebView) findViewById(R.id.content);
        webView.loadUrl("https://www.baidu.com");
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("ScrollingActivity", "webView height: " + webView.getHeight());

                webView.getHeight();
            }
        }, 2000);
    }

}
