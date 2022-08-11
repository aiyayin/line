package com.line.demo.html;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;




/**
 * fix bug:
 * #301023 android.content.res.Resources$NotFoundException
 * String resource ID #0x2040003
 */
public class FixResourceNotFountWebView extends WebView {

    public FixResourceNotFountWebView(Context context) {
        super(getContext(context));
        init();
    }

    public FixResourceNotFountWebView(Context context, AttributeSet attributeSet) {
        super(getContext(context), attributeSet);
        init();
    }

    public FixResourceNotFountWebView(Context context, AttributeSet attributeSet, int i) {
        super(getContext(context), attributeSet, i);
        init();
    }

    private static Context getContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return context.createConfigurationContext(new Configuration());
        }
        return context;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //noinspection deprecation
        webSettings.setJavaScriptEnabled(true);// 启用javascript支持
        //WebView注入漏洞处理
        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibility");
        removeJavascriptInterface("accessibilityTraversal");
        //自适应屏幕大小
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSettings.setMediaPlaybackRequiresUserGesture(false);
//        if (TBaseModuleApplication.IS_DEBUG) {
//            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        }
        //ua拼接土巴兔参数 http://wiki.we.com:8090/pages/viewpage.action?pageId=61672497
//        webSettings.setUserAgentString(webSettings.getUserAgentString() + " " + ToolUtil.getUserAgent());
        //noinspection deprecation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        //地理定位
        webSettings.setGeolocationEnabled(true);
    }

}
