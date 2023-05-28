package com.yin.lin.demo.html;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RawRes;
import androidx.core.text.HtmlCompat;

import com.yin.lin.demo.R;
import com.yin.line.base.BaseActivity;

import java.io.InputStream;
import java.util.Scanner;

public class ArticleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
//222
        TextView text = findViewById(R.id.text);
        text.setText(HtmlCompat.fromHtml(convertStreamToString(R.raw.article3), HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS));
//        RichText.fromHtml(R.raw.article).into(text);
        MarqueeView marqueeView = findViewById(R.id.mMarqueeView);
        marqueeView.setText("===看着怎么和v老姑父；pi'upu【噗噗片===");
        marqueeView.startAnim();
//过滤图片标签
//        Pattern compile = Pattern.compile("<\\s*(img|IMG)\\s+([^>]*)\\s*>");
//        String[] strings = compile.split(content);


    }

    private String convertStreamToString(@RawRes int resId) {
        InputStream inputStreamText = getResources().openRawResource(resId);
        Scanner s = new Scanner(inputStreamText).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}